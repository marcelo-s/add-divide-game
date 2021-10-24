package com.marcelo.gameserver.events;

import com.marcelo.gameserver.command.MovePlayer;
import lombok.Value;

import java.util.UUID;

@Value
public class GameFinishedEvent {

	UUID gameId;
	MovePlayer winner;

}
