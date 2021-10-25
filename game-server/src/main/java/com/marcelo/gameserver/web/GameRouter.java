package com.marcelo.gameserver.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GameRouter {

	@Bean
	public RouterFunction<ServerResponse> route(GameRouteHandler gameRouteHandler) {
		return RouterFunctions
				.route()
				.POST("/game/auto", gameRouteHandler::createGameAuto)
				.POST("/game/manual", gameRouteHandler::createGameManual)
				.build();
	}
}
