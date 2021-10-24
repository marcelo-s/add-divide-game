package com.marcelo.gameserver.web;

import com.marcelo.gameserver.command.CreateGameCommand;
import com.marcelo.gameserver.command.MovePlayer;
import com.marcelo.gameserver.command.PlayCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Component
@RequiredArgsConstructor
@Slf4j
public class GameRouteHandler {
	@Value("${name}")
	private String playerName;

	private final ReactorCommandGateway reactorCommandGateway;

	public Mono<ServerResponse> createGame(ServerRequest request) {
		UUID gameId = UUID.randomUUID();
		MovePlayer creatorPlayer = getCreatorPlayer();
		return reactorCommandGateway.send(new CreateGameCommand(gameId, 0, creatorPlayer))
		                            .then(reactorCommandGateway.send(new PlayCommand(gameId, 56, creatorPlayer)))
		                            .flatMap(a ->
				                                     ServerResponse
						                                     .ok()
						                                     .contentType(MediaType.APPLICATION_JSON)
						                                     .bodyValue(gameId)
		                                    );
	}

	private MovePlayer getCreatorPlayer() {
		if (playerName.equals(MovePlayer.PLAYER1.name())) {
			return MovePlayer.PLAYER1;
		}
		return MovePlayer.PLAYER2;
	}
}
