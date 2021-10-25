package com.marcelo.gameserver.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGameCommand {

	@TargetAggregateIdentifier
	UUID gameId;
	MovePlayer creator;
}
