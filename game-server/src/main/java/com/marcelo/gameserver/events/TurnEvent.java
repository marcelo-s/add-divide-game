package com.marcelo.gameserver.events;

import com.marcelo.gameserver.command.Turn;
import lombok.Value;

import java.util.UUID;

@Value
public class TurnEvent {

	UUID gameId;
	int value;
	Turn turn;

}
