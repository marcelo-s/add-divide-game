package com.marcelo.gameserver.events;

import com.marcelo.gameserver.command.MovePlayer;
import lombok.Value;

import java.util.UUID;

@Value
public class PlayEvent {

	UUID gameId;
	int value;
	MovePlayer player;

}
