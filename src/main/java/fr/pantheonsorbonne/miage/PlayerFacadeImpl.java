package fr.pantheonsorbonne.miage;

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
import java.util.stream.Collectors;


public class PlayerFacadeImpl extends Main implements PlayerFacade {

    private final static Logger LOGGER = LoggerFactory.getLogger(PlayerFacadeImpl.class);

    private static final PlayerFacadeImpl PLAYER_FACADE;

    static {
        PLAYER_FACADE = new PlayerFacadeImpl();
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

    private PlayerFacadeImpl() {
        super(PlayerFacadeImpl.class);
    }

    public static PlayerFacadeImpl getInstance() {

        return PLAYER_FACADE;

    }

    @Override
    protected void afterStart() throws Exception {
        super.afterStart();


    }

    @Override
    public void joinLobby(String playerName) {
        LOGGER.debug("join lobby");
        this.playerName = playerName;
        this.sendLobbyStatus(playerName + " joined the lobby");
        LOGGER.debug("join lobby message sent");
    }

    @Override
    public void leaveLobby() {

    }

    @Override
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
    public Game createNewGameInLobby(String gameName) {
        LOGGER.debug("createNewGameInLobby");

        if (this.getCurrentGame() != null) {
            lobbyGame.remove(this.getCurrentGame());
            this.leaveGame(this.getCurrentGame());
        }

        this.setCurrentGame(new Game(UUID.randomUUID().toString(), gameName, this.playerName, new HashSet<>(), Game.GameState.CREATED));
        new Thread(() -> {
            while (true) {
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

    public synchronized Game getCurrentGame() {
        return currentGame;
    }

    public synchronized void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    @Override
    public void startGame(Game game) {
        if (this.getCurrentGame() != null && this.getCurrentGame().gameId().equals(game.gameId())) {
            this.getCurrentGame().setState(Game.GameState.STARTED);

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
    public void sendGameCommand(Game game, String command) {
        this.camelContext.createProducerTemplate().sendBodyAndHeaders("direct:gameCommands", command, Map.of("type", "gameReceiveCommands", "gameId", game.gameId()));
    }

    @Override
    public Collection<String> receiveGameCommands(Game game) {
        return getAndPollQueue(this.gameCommands);
    }

    @Override
    public boolean isReady() {
        return this.isStarted();
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
    public void waitGameStart(Game game) {
        while (!getCurrentGame().state().equals(Game.GameState.STARTED)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getPlayerCount() {
        return this.getCurrentGame().players().size() + 1;
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
            this.setCurrentGame(game);
            this.lobbyGame.put(this.getCurrentGame().gameId(), game);
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
        LOGGER.error("received command " + command + " we have " + gameCommands.size() + " so far in " + gameCommands);
    }


}
