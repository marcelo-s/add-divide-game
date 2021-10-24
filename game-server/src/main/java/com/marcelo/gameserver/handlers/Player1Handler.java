package com.marcelo.gameserver.handlers;

import com.marcelo.gameserver.command.MovePlayer;
import com.marcelo.gameserver.command.PlayCommand;
import com.marcelo.gameserver.events.GameFinishedEvent;
import com.marcelo.gameserver.events.NextMoveEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("player1")
@Component
@Slf4j
@ProcessingGroup("player1-group")
public class Player1Handler {
	private final CommandGateway commandGateway;

	public Player1Handler(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}

	@EventHandler
	public void on(GameFinishedEvent event) {
		log.info("Game has finished, the winner is: {} ", event.getWinner().name());
	}

	@EventHandler
	public void on(NextMoveEvent event) {
		if (event.getTurn() == MovePlayer.PLAYER1) {
			int valueToAdd = PlayUtils.getValueToAdd(event.getValue());
			int newValue = (event.getValue() + valueToAdd) / 3;
			log.info("{} --> Received number: {} Chosen Added number: {} Resulting number after adding and division: " +
					         "{}",
			         MovePlayer.PLAYER1.name(),
			         event.getValue(),
			         valueToAdd,
			         newValue);
			commandGateway.send(new PlayCommand(event.getGameId(), newValue, MovePlayer.PLAYER1));
		}
	}
}
