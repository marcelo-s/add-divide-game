package com.marcelo.gameserver.command;

import com.marcelo.gameserver.events.GameCreatedEvent;
import com.marcelo.gameserver.events.PlayEvent;
import com.marcelo.gameserver.events.TurnEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;


@Aggregate
@NoArgsConstructor
public class GameAggregate {

	@AggregateIdentifier
	private UUID gameId;
	private Turn turn;
	private int currentValue;

	@CommandHandler
	public GameAggregate(CreateGameCommand command) {
		apply(new GameCreatedEvent(command.getGameId(), command.getSeed()));
	}

	@EventSourcingHandler
	public void on(GameCreatedEvent event) {
		this.gameId = event.getGameId();
		this.currentValue = event.getSeed();
		this.turn = Turn.PLAYER1;
	}

	@CommandHandler
	public void handle(PlayCommand command) {
		Turn newTurn;
		if (turn.equals(Turn.PLAYER1)) {
			newTurn = Turn.PLAYER2;
		} else {
			newTurn = Turn.PLAYER1;
		}
		apply(new PlayEvent(command.getGameId(), command.getValue(), newTurn));
	}

	@EventSourcingHandler
	public void on(PlayEvent event) {
		this.currentValue = event.getValue();
		if (currentValue == 1) {
			System.out.println("WINNER IS " + turn);
		} else {
			this.turn = event.getTurn();
			apply(new TurnEvent(event.getGameId(), event.getValue(), event.getTurn()));
		}
	}

}