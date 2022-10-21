package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;
import org.apache.camel.Body;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * This is the internal implementation of the facade, students should not use it directly for their assigment
 */
public class FacadeImpl extends Main implements InternalFacadePlumbing, Facade, HostFacade, PlayerFacade {

    private final static Logger LOGGER = LoggerFactory.getLogger(FacadeImpl.class);

    private static final FacadeImpl PLAYER_FACADE;

    static {
        PLAYER_FACADE = new FacadeImpl();
        try {
            new Thread(() -> {
                try {
                    PLAYER_FACADE.run();


                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("camel intializing");
        }


    }

    BlockingDeque<String> lobbyText = new LinkedBlockingDeque<>();
    BlockingDeque<String> lobbyStatus = new LinkedBlockingDeque<>();
    BlockingDeque<String> gameCommands = new LinkedBlockingDeque<>();
    ConcurrentHashMap<String, Game> lobbyGame = new ConcurrentHashMap<>();
    private String playerName;
    private Game currentGame;

    private FacadeImpl() {
        super(Facade.class);
    }

    public static FacadeImpl getSingleton() {

        return PLAYER_FACADE;

    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    protected void afterStart() throws Exception {
        super.afterStart();


    }

    public void sendLobbyMessage(String text) {
        LOGGER.debug("send lobby message " + text);
        var exchange = ExchangeBuilder.anExchange(this.camelContext).withPattern(ExchangePattern.InOnly).withHeader("type", "lobbyMessage").withBody(text).build();
        this.camelContext.createProducerTemplate().send("direct:lobbyMessage?exchangePattern=InOnly&block=false", exchange);
        LOGGER.debug("send lobby message done");
    }

    @Override
    public Collection<String> receiveLobbyMessages() {
        return getAndPollQueue(this.lobbyText);

    }

    private Collection<String> getAndPollQueue(Deque<String> queue) {
        LOGGER.debug("receive lobby message");
        Collection<String> res = new LinkedList<>();
        String item = null;
        while ((item = queue.pollFirst()) != null) {
            res.add(item);
        }
        LOGGER.debug("receive lobby message done");
        return res;
    }

    @Override
    public Collection<String> receiveLobbyStatus() {
        return getAndPollQueue(this.lobbyStatus);
    }

    @Override
    public Collection<Game> getAvailableGamesInLobby() {
        return this.lobbyGame.values().stream().filter(g -> !g.playerName().equals(this.playerName)).collect(Collectors.toList());
    }

    @Override
    public void startGame(Game game) {
        if (this.getCurrentGame() != null && this.getCurrentGame().gameId().equals(game.gameId())) {
            this.getCurrentGame().setState(Game.GameState.STARTED);
            this.camelContext.createProducerTemplate().sendBody("direct:lobbyGame", this.getCurrentGame());

        }
    }

    @Override
    public void leaveGame(Game game) {

    }

    @Override
    public void joinGame(Game game) {
        this.setCurrentGame(game);
        this.camelContext.createProducerTemplate().sendBodyAndHeaders("direct:game-joining", this.playerName, Map.of("gameId", game.gameId()));

    }

    @Override
    public void rawSendGameCommand(Game game, String command) {
        this.camelContext.createProducerTemplate().sendBodyAndHeaders("direct:gameCommands", command, Map.of("type", "gameReceiveCommands", "gameId", game.gameId(), "sender", this.playerName));
    }

    @Override
    public String receiveRawGameCommand(Game game) {

        try {
            return this.gameCommands.pollFirst(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isReady() {
        return this.isStarted();
    }

    @Override
    public void waitGameStarted(Game game) {
        while (!getCurrentGame().state().equals(Game.GameState.STARTED)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    synchronized public int getPlayerCount() {
        return this.getCurrentGame().players().size() + 1;
    }

    @Override
    public void waitReady() {
        while (!this.isReady()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void createNewPlayer(String playerName) {
        LOGGER.debug("join lobby");
        this.playerName = playerName;
        this.sendLobbyStatus(playerName + " joined the lobby");
        LOGGER.debug("join lobby message sent");
    }

    @Override
    public void sendGameCommandToPlayer(Game game, String playerName, GameCommand command) {
        Map<String, String> customParams = new HashMap<>(command.params());
        customParams.put("playerId", playerName);
        this.sendGameCommandToAll(game, new GameCommand(command.name(), command.body(), customParams));
    }

    @Override
    public Game createNewGame(String gameName) {
        LOGGER.debug("createNewGameInLobby");

        if (this.getCurrentGame() != null) {
            lobbyGame.remove(this.getCurrentGame());
            this.leaveGame(this.getCurrentGame());
        }

        this.setCurrentGame(new Game(UUID.randomUUID().toString(), gameName, this.playerName, new HashSet<>(), Game.GameState.CREATED));
        new Thread(() -> {
            while (!this.getCurrentGame().state().equals(Game.GameState.STARTED)) {
                this.camelContext.createProducerTemplate().sendBody("direct:lobbyGame", this.getCurrentGame());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return this.getCurrentGame();

    }

    @Override
    public void waitForExtraPlayerCount(int i) {
        while (this.getPlayerCount() +1 < i) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        this.startGame(this.getCurrentGame());
        this.waitGameStarted(this.getCurrentGame());

    }

    @Override
    public GameCommand receiveGameCommand(Game game) {
        while (true) {
            String command = this.receiveRawGameCommand(game);

            int nameIndex = command.indexOf(":");
            String commandName = command.substring(0, nameIndex);
            int paramIndex = command.indexOf("?");
            Map<String, String> params = Collections.EMPTY_MAP;
            String commandBody = "";
            if (paramIndex != -1) {
                params = new HashMap<>();
                String paramsStr = command.substring(paramIndex + 1);
                for (String entry : paramsStr.split("&")) {
                    String[] kv = entry.split("=");
                    params.put(kv[0], kv[1]);
                }
                commandBody = command.substring(nameIndex + 1, paramIndex);
            } else {
                commandBody = command.substring(nameIndex + 1);
            }
            if (!params.containsKey("playerId") || params.get("playerId").equals(this.playerName)) {
                return new GameCommand(commandName, commandBody, params);
            } else {
                //this message is not for me and not for all
                continue;
            }


        }


    }

    @Override
    public void sendGameCommandToAll(Game game, GameCommand command) {
        String params = command.params().entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("&"));
        if (params.isEmpty()) {
            this.rawSendGameCommand(game, command.name() + ":" + command.body());
        } else {
            this.rawSendGameCommand(game, command.name() + ":" + command.body() + "?" + params);
        }

    }

    @Override
    public Game autoJoinGame(String gameName) {

        waitingForGame:
        while (true) {
            for (Game game : this.getAvailableGamesInLobby()) {
                if (game.gameName().equals(gameName)) {
                    this.setCurrentGame(game);
                    break waitingForGame;
                }
            }
        }
        this.joinGame(this.getCurrentGame());
        this.waitGameStarted(this.getCurrentGame());
        return this.getCurrentGame();

    }

    public synchronized Game getCurrentGame() {
        return currentGame;
    }

    public synchronized void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public void sendLobbyStatus(String text) {
        LOGGER.debug("send lobby status: " + text);
        var exchange = ExchangeBuilder.anExchange(this.camelContext).withPattern(ExchangePattern.InOnly).withHeader("type", "lobbyStatus").withBody(text).build();
        this.camelContext.createProducerTemplate().send("direct:lobbyStatus?exchangePattern=InOnly&block=false", exchange);
        LOGGER.debug("send lobby status done");
    }


    public void onLobbyReceiveMessage(@Body String text) throws InterruptedException {
        lobbyText.offerLast(text);
    }

    public void onLobbyReceiveStatus(@Body String text) throws InterruptedException {
        LOGGER.debug("lobbyReceiveStatus");
        lobbyStatus.offerLast(text);
        LOGGER.debug("lobbyReceiveStatus done");
    }

    public void onLobbyReceiveGame(@Body Game game) throws InterruptedException {
        LOGGER.debug("lobbyReceiveGame");
        if (this.getCurrentGame() != null && game.gameId().equals(this.getCurrentGame().gameId())) {
            this.getCurrentGame().getPlayers().addAll(game.getPlayers());
            this.getCurrentGame().setState(game.getState());

        } else {
            this.lobbyGame.put(game.gameId(), game);
        }

        LOGGER.debug("lobbyReceiveGame done");
    }

    public void onGameJoining(@Body String playerName) throws InterruptedException {
        this.getCurrentGame().players().add(playerName);
        LOGGER.warn("player " + playerName + " has joined our game");
    }

    public void onGameCommandReceived(@Body String command) throws InterruptedException {

        gameCommands.offerLast(command);
        LOGGER.info("received command " + command);
    }


}
