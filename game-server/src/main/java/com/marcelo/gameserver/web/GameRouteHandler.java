package com.marcelo.gameserver.web;

import com.marcelo.gameserver.command.CreateGameCommand;
import com.marcelo.gameserver.command.PlayCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class GameRouteHandler {

	private final ReactorCommandGateway reactorCommandGateway;

	public Mono<ServerResponse> createGame(ServerRequest request) {
		UUID gameId = UUID.randomUUID();
		return reactorCommandGateway.send(new CreateGameCommand(gameId, 0))
				.then(reactorCommandGateway.send(new PlayCommand(gameId, 56)))
				.flatMap(a ->
						         ServerResponse
								         .ok()
								         .contentType(MediaType.APPLICATION_JSON)
								         .bodyValue(gameId)
				        );
	}
}
