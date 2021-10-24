package com.marcelo.gameserver.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class PlayCommand {

	@TargetAggregateIdentifier
	UUID gameId;
	int value;
	MovePlayer player;

	// constructor, getters, equals/hashCode and toString
}
