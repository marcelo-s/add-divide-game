package com.marcelo.gameserver.web.validation.message;

import lombok.Value;

@Value
public class FieldErrorMessage {
	String fieldName;
	String message;
}
