package com.marcelo.gameserver.web.validation;

import com.marcelo.gameserver.web.model.in.CreateGameRequest;
import com.marcelo.gameserver.web.validation.exception.ValidationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class RouterValidator {

	private final Validator webFluxBeanValidator;

	public RouterValidator(@Qualifier("webFluxValidator") Validator webFluxBeanValidator) {
		this.webFluxBeanValidator = webFluxBeanValidator;
	}

	public Mono<CreateGameRequest> validate(CreateGameRequest createGameRequest) {
		Errors errors = new BeanPropertyBindingResult(createGameRequest, createGameRequest.getClass().getName());
		this.webFluxBeanValidator.validate(createGameRequest, errors);
		if (errors.hasErrors()) {
			return getValidationErrorsResponse(errors);
		}
		return Mono.just(createGameRequest);
	}

	private Mono<CreateGameRequest> getValidationErrorsResponse(Errors errors) {
		return Mono.error(() -> {
			throw getValidationException(errors);
		});
	}

	private ValidationException getValidationException(Errors errors) {
		ValidationError validationError = new ValidationError("Input is invalid");
		addFieldErrors(validationError, errors.getFieldErrors());
		addGlobalErrors(validationError, errors.getGlobalErrors());
		return new ValidationException(validationError);
	}

	private void addFieldErrors(ValidationError validationError, List<FieldError> errors) {
		for (FieldError fieldError : errors) {
			validationError.addFieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage());
		}
	}

	private void addGlobalErrors(ValidationError validationError, List<ObjectError> errors) {
		for (ObjectError error : errors) {
			validationError.addObjectErrorMessage(error.getObjectName(), error.getDefaultMessage());
		}
	}
}
