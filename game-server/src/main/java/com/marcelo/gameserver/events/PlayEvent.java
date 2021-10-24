package com.marcelo.gameserver.events;

import com.marcelo.gameserver.command.Turn;
import lombok.Value;

import java.util.UUID;

@Value
public class PlayEvent {

	UUID gameId;
	int value;
	Turn turn;

}
