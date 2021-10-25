package com.marcelo.gameserver.web.validation;

import com.marcelo.gameserver.web.validation.message.FieldErrorMessage;
import com.marcelo.gameserver.web.validation.message.ObjectErrorMessage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationError {

	private String validationErrorMessage;
	private List<FieldErrorMessage> fieldErrorMessages;
	private List<ObjectErrorMessage> objectErrorMessages;

	public ValidationError(String validationErrorMessage) {
		this.validationErrorMessage = validationErrorMessage;
		this.fieldErrorMessages = new ArrayList<>();
		this.objectErrorMessages = new ArrayList<>();
	}

	public void addFieldErrorMessage(String fieldName, String message) {
		fieldErrorMessages.add(new FieldErrorMessage(fieldName, message));
	}

	public void addObjectErrorMessage(String objectName, String message) {
		objectErrorMessages.add(new ObjectErrorMessage(objectName, message));
	}
}
