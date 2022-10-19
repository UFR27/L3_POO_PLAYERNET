/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.pantheonsorbonne.miage.camel;

import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.PlayerFacadeImpl;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;

public class CamelRouteBuilder extends RouteBuilder {

    public static final String TOPIC_MESSAGE = "jms:topic:miage.lobby.message?exchangePattern=InOnly";
    public static final String TOPIC_GAME_ANOUNCEMENTS = "jms:topic:miage.lobby.game-anouncement?exchangePattern=InOnly";
    public static final String TOPIC_GAME_JOINING = "jms:topic:miage.lobby.game-joining?exchangePattern=InOnly";
    public static final String TOPIC_STATUS = "jms:topic:miage.lobby.status?exchangePattern=InOnly";
    public static final String TOPIC_GAME_COMMAND = "jms:topic:miage.lobby.game-commands?exchangePattern=InOnly";

    @Override
    public void configure() {

        //void joinLobby(String playerName);
        from(TOPIC_MESSAGE)
                .bean(PlayerFacadeImpl.getSingleton(), "onLobbyReceiveMessage")
                .end();

        from(TOPIC_GAME_ANOUNCEMENTS)

                .log("received game ${body} of type ${header.type}")
                .unmarshal().json(Game.class)
                .bean(PlayerFacadeImpl.getSingleton(), "onLobbyReceiveGame")
                .end();
        from(TOPIC_GAME_JOINING).
                log("User ${body} is joining game ${header.gameId}")
                .filter(new Predicate() {
                    @Override
                    public boolean matches(Exchange exchange) {
                        return exchange.getMessage().getHeader("gameId").equals(PlayerFacadeImpl.getSingleton().getCurrentGame().gameId());
                    }
                })
                .bean(PlayerFacadeImpl.getSingleton(), "onGameJoining")
        ;

        from(TOPIC_STATUS)
                .bean(PlayerFacadeImpl.getSingleton(), "onLobbyReceiveStatus")
                .end();

        from(TOPIC_GAME_COMMAND)
                .filter(new Predicate() {
                    @Override
                    public boolean matches(Exchange exchange) {
                        return PlayerFacadeImpl.getSingleton().getCurrentGame() != null
                                && exchange.getMessage().getHeader("gameId").equals(PlayerFacadeImpl.getSingleton().getCurrentGame().gameId())
                                && !exchange.getMessage().getHeader("sender").equals(PlayerFacadeImpl.getSingleton().getPlayerName());
                    }
                })
                .bean(PlayerFacadeImpl.getSingleton(), "onGameCommandReceived")
                .end();


        from("direct:lobbyText")
                .log("sent ${body} to lobby")
                .to(ExchangePattern.InOnly, TOPIC_MESSAGE);

        from("direct:game-joining")
                .to(ExchangePattern.InOnly, TOPIC_GAME_JOINING);

        from("direct:lobbyGame?block=false")
                .log(LoggingLevel.INFO, "received game ${body}")
                .marshal().json(Game.class)
                .setHeader("type", constant("lobbyGameAnouncement"))
                .to(ExchangePattern.InOnly, TOPIC_GAME_ANOUNCEMENTS);

        from("direct:gameCommands")
                .to(ExchangePattern.InOnly, TOPIC_GAME_COMMAND);
    }
}
