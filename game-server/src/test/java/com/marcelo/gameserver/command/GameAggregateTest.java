package com.marcelo.gameserver.command;

import com.marcelo.gameserver.events.GameCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class GameAggregateTest {
	private AggregateTestFixture<GameAggregate> testFixture;

	@BeforeEach
	void setUp() {
		testFixture = new AggregateTestFixture<>(GameAggregate.class);
	}

	@Test
	void createGameTest() {
		UUID gameId = UUID.randomUUID();
		MovePlayer gameCreator = MovePlayer.PLAYER1;
		CreateGameCommand createGameCommand = new CreateGameCommand(gameId, gameCreator);
		GameCreatedEvent gameCreatedEvent = new GameCreatedEvent(gameId, gameCreator);

		testFixture.givenNoPriorActivity()
		           .when(createGameCommand)
		           .expectEvents(gameCreatedEvent)
		           .expectSuccessfulHandlerExecution();
	}

}