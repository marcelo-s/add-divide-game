package com.marcelo.gameserver.web.validation.message;

import lombok.Value;

@Value
public class ObjectErrorMessage {
	String objectName;
	String message;
}
