package com.marcelo.gameserver.web;

import com.marcelo.gameserver.command.CreateGameCommand;
import com.marcelo.gameserver.command.MovePlayer;
import com.marcelo.gameserver.command.PlayCommand;
import com.marcelo.gameserver.web.model.in.CreateGameRequest;
import com.marcelo.gameserver.web.validation.RouterValidator;
import com.marcelo.gameserver.web.validation.exception.ValidationException;
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
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;


@Component
@RequiredArgsConstructor
@Slf4j
public class GameRouteHandler {
	@Value("${name}")
	private String playerName;

	private final ReactorCommandGateway reactorCommandGateway;
	private final RouterValidator routerValidator;

	public Mono<ServerResponse> createGameAuto(ServerRequest request) {
		int initialNumber = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE - 3);
		return sendCreateCommand(initialNumber);
	}

	public Mono<ServerResponse> createGameManual(ServerRequest request) {
		return request.bodyToMono(CreateGameRequest.class)
		              .flatMap(routerValidator::validate)
		              .flatMap(createGameRequest -> sendCreateCommand(createGameRequest.getInitialValue()))
		              .onErrorResume(ValidationException.class, this::handleValidationException);
	}

	private Mono<ServerResponse> sendCreateCommand(int initialNumber) {
		UUID gameId = UUID.randomUUID();
		MovePlayer creatorPlayer = getCreatorPlayer();
		return reactorCommandGateway.send(new CreateGameCommand(gameId, creatorPlayer))
		                            .then(reactorCommandGateway.send(new PlayCommand(gameId, initialNumber,
		                                                                             creatorPlayer)))
		                            .flatMap(getSuccessResponse(gameId));
	}

	private Function<Object, Mono<? extends ServerResponse>> getSuccessResponse(UUID gameId) {
		return response ->
				ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.bodyValue(gameId);
	}

	private Mono<? extends ServerResponse> handleValidationException(ValidationException validationException) {
		validationException.printStackTrace();
		return badRequest().bodyValue(validationException.getValidationError());
	}

	private MovePlayer getCreatorPlayer() {
		if (playerName.equals(MovePlayer.PLAYER1.name())) {
			return MovePlayer.PLAYER1;
		}
		return MovePlayer.PLAYER2;
	}
}
