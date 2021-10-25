package com.marcelo.gameserver.web.validation.exception;

import com.marcelo.gameserver.web.validation.ValidationError;
import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

	private final ValidationError validationError;

	public ValidationException(ValidationError validationError) {
		super(validationError.getValidationErrorMessage());
		this.validationError = validationError;
	}
}
