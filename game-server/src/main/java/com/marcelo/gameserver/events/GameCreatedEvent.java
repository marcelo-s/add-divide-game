package com.marcelo.gameserver.events;

import com.marcelo.gameserver.command.MovePlayer;
import lombok.Value;

import java.util.UUID;

@Value
public class GameCreatedEvent {

	UUID gameId;
	int seed;
	MovePlayer creator;

}
