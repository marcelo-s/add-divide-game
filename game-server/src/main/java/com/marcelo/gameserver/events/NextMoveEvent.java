package com.marcelo.gameserver.events;

import com.marcelo.gameserver.command.MovePlayer;
import lombok.Value;

import java.util.UUID;

@Value
public class NextMoveEvent {

	UUID gameId;
	int value;
	MovePlayer turn;

}
