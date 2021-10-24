package com.marcelo.gameserver.events;

import lombok.Value;

import java.util.UUID;

@Value
public class GameCreatedEvent {

	UUID gameId;
	int seed;

}
