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

@Profile("player2")
@Component
@Slf4j
@ProcessingGroup("player2-group")
public class Player2Handler {
	private final CommandGateway commandGateway;

	public Player2Handler(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}

	@EventHandler
	public void on(GameFinishedEvent event) {
		log.info("Game has finished, the winner is: {} ", event.getWinner().name());
	}

	@EventHandler
	public void on(NextMoveEvent event) {
		if (event.getTurn() == MovePlayer.PLAYER2) {
			int valueToAdd = PlayUtils.getValueToAdd(event.getValue());
			int newValue = (event.getValue() + valueToAdd) / 3;
			log.info("{} --> Received number: {} Chosen Added number: {} Resulting number after adding and division: " +
					         "{}",
			         MovePlayer.PLAYER2.name(),
			         event.getValue(),
			         valueToAdd,
			         newValue);
			commandGateway.send(new PlayCommand(event.getGameId(), newValue, MovePlayer.PLAYER2));
		}
	}

}
