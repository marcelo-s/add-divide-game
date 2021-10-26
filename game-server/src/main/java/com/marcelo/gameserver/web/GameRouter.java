package com.marcelo.gameserver.web;

import com.marcelo.gameserver.web.model.in.CreateGameRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class GameRouter {

	@Bean
	public RouterFunction<ServerResponse> routing(GameRouteHandler gameRouteHandler) {
		return route()
				.POST("/game/auto", accept(APPLICATION_JSON),
				      gameRouteHandler::createGameAuto,
				      ops -> ops.tag("Game")
				                .operationId("game-auto")
				                .requestBody(requestBodyBuilder().implementation(CreateGameRequest.class))
				                .response(responseBuilder().responseCode("200")
				                                           .implementation(String.class)))
				.POST("/game/manual", accept(APPLICATION_JSON),
				      gameRouteHandler::createGameManual,
				      ops -> ops.tag("Game")
				                .operationId("game-manual")
				                .response(responseBuilder().responseCode("200")
				                                           .implementation(String.class)))
				.build();
	}
}