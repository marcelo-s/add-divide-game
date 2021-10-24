package com.marcelo.gameserver.command;

import com.marcelo.gameserver.events.GameCreatedEvent;
import com.marcelo.gameserver.events.GameFinishedEvent;
import com.marcelo.gameserver.events.NextMoveEvent;
import com.marcelo.gameserver.events.PlayEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;


@Aggregate
@NoArgsConstructor
@Slf4j
public class GameAggregate {

	@AggregateIdentifier
	private UUID gameId;
	private MovePlayer currentPlayerTurn;
	private MovePlayer winner;
	private GameStatus gameStatus;
	private int currentValue;

	@CommandHandler
	public GameAggregate(CreateGameCommand command) {
		apply(new GameCreatedEvent(command.getGameId(), command.getSeed(), command.getCreator()));
	}

	@EventSourcingHandler
	public void on(GameCreatedEvent event) {
		this.gameId = event.getGameId();
		this.currentValue = event.getSeed();
		this.gameStatus = GameStatus.STARTED;
		this.currentPlayerTurn = MovePlayer.PLAYER2;
	}

	@CommandHandler
	public void handle(PlayCommand command) {
		apply(new PlayEvent(command.getGameId(), command.getValue(), command.getPlayer()));
	}

	@EventSourcingHandler
	public void on(PlayEvent event) {
		this.currentValue = event.getValue();
		if (currentPlayerHasWon()) {
			this.winner = currentPlayerTurn;
			apply(new GameFinishedEvent(event.getGameId(), this.winner));
		} else {
			MovePlayer nextMovePlayer = getNextMovePlayer(event.getPlayer());
			this.currentPlayerTurn = nextMovePlayer;
			apply(new NextMoveEvent(event.getGameId(), event.getValue(), nextMovePlayer));
		}
	}

	private MovePlayer getNextMovePlayer(MovePlayer currentPlayerTurn) {
		MovePlayer newMovePlayer;
		if (currentPlayerTurn.equals(MovePlayer.PLAYER1)) {
			newMovePlayer = MovePlayer.PLAYER2;
		} else {
			newMovePlayer = MovePlayer.PLAYER1;
		}
		return newMovePlayer;
	}

	private boolean currentPlayerHasWon() {
		return currentValue == 1;
	}

}