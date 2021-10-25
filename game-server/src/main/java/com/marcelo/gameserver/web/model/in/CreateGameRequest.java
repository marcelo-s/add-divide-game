package com.marcelo.gameserver.web.model.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGameRequest {
	@Min(3)
	@Max(Integer.MAX_VALUE - 3)
	int initialValue;
}
