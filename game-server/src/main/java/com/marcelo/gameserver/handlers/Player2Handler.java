package com.marcelo.gameserver.handlers;

import com.marcelo.gameserver.command.PlayCommand;
import com.marcelo.gameserver.command.Turn;
import com.marcelo.gameserver.events.GameCreatedEvent;
import com.marcelo.gameserver.events.TurnEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("player2")
@Component
@ProcessingGroup("player2-group")
public class Player2Handler {
	private final CommandGateway commandGateway;

	public Player2Handler(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}

	@EventHandler
	public void on(GameCreatedEvent event) {
		System.out.println("player2 : event = " + event);
	}

	@EventHandler
	public void on(TurnEvent event) {
		if (event.getTurn() == Turn.PLAYER2) {
			int value = event.getValue();
			int newValue;
			int addedValue;
			if (value % 3 == 0) {
				addedValue = 0;
				newValue = value / 3;
			} else if (event.getValue() % 3 == 1) {
				addedValue = -1;
				newValue = value - 1;
			} else {
				addedValue = 1;
				newValue = value + 1;
			}
			System.out.println("player2 current value is = " + event.getValue() + " added : " + addedValue + "final " +
					                   "value: " + newValue);
			commandGateway.send(new PlayCommand(event.getGameId(), newValue));
		}
	}
}
