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
package fr.pantheonsorbonne.miage;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder {

    public static final String TOPIC_MESSAGE = "jms:topic:miage.lobby.message?exchangePattern=InOnly";
    public static final String TOPIC_GAME_ANOUNCEMENTS = "jms:topic:miage.lobby.game-anouncement?exchangePattern=InOnly";
    public static final String TOPIC_GAME_JOINING = "jms:topic:miage.lobby.game-joining?exchangePattern=InOnly";
    public static final String TOPIC_STATUS = "jms:topic:miage.lobby.status?exchangePattern=InOnly";
    public static final String TOPIC_GAME_COMMAND = "jms:topic:miage.lobby.game-commands?exchangePattern=InOnly";

    @Override
    public void configure() {

        //void joinLobby(String playerName);
        from(TOPIC_MESSAGE)
                .bean(PlayerFacadeImpl.getInstance(), "onLobbyReceiveMessage")
                .end();

        from(TOPIC_GAME_ANOUNCEMENTS)

                .log("received game ${body} of type ${header.type}")
                .unmarshal().json(Game.class)
                .bean(PlayerFacadeImpl.getInstance(), "onLobbyReceiveGame")
                .end();
        from(TOPIC_GAME_JOINING).
                log("User ${body} is joining game ${header.gameId}")
                .filter(new Predicate() {
                    @Override
                    public boolean matches(Exchange exchange) {
                        return exchange.getMessage().getHeader("gameId").equals(PlayerFacadeImpl.getInstance().getCurrentGame().gameId());
                    }
                })
                .bean(PlayerFacadeImpl.getInstance(), "onGameJoining")
        ;

        from(TOPIC_STATUS)
                .bean(PlayerFacadeImpl.getInstance(), "onLobbyReceiveStatus")
                .end();

        from(TOPIC_GAME_COMMAND)
                .log("processing user sending game commands")
                .filter(new Predicate() {
                    @Override
                    public boolean matches(Exchange exchange) {
                        return PlayerFacadeImpl.getInstance().getCurrentGame() != null && exchange.getMessage().getHeader("gameId").equals(PlayerFacadeImpl.getInstance().getCurrentGame().gameId());
                    }
                })
                .log("processing user sending game commands forwarded")
                .bean(PlayerFacadeImpl.getInstance(), "onGameCommandReceived")
                .log("processing user sending game commands forwarded and processed")
                .end();


        from("direct:lobbyText")
                .log("sent ${body} to lobby")
                .to(ExchangePattern.InOnly, TOPIC_MESSAGE);

        from("direct:game-joining")
                .to(ExchangePattern.InOnly, TOPIC_GAME_JOINING);

        from("direct:lobbyGame?block=false")
                .log(LoggingLevel.WARN, "received game ${body}")
                .marshal().json(Game.class)
                .setHeader("type", constant("lobbyGameAnouncement"))
                .to(ExchangePattern.InOnly, TOPIC_GAME_ANOUNCEMENTS);

        from("direct:gameCommands")
                .to(ExchangePattern.InOnly, TOPIC_GAME_COMMAND);
    }
}
