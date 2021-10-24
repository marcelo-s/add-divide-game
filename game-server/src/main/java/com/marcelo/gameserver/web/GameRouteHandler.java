package com.marcelo.gameserver.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class GameRouteHandler {

	public Mono<ServerResponse> createGame(ServerRequest request) {
		return Mono.empty();
	}
}
