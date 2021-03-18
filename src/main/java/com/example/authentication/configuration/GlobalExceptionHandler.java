package com.example.authentication.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.authentication.dto.MessageDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

	private MessageSource messageSource;

	public GlobalExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<MessageDTO>> handleValidationExceptions(MethodArgumentNotValidException ex,
			Locale locale) {
		List<MessageDTO> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			MessageDTO message = new MessageDTO();
			String errorCode = error.getDefaultMessage();
			message.setCode(errorCode);
			message.setText(messageSource.getMessage(errorCode, null, locale));
			errors.add(message);
		});
		ResponseEntity<List<MessageDTO>> response = new ResponseEntity<List<MessageDTO>>(errors,
				HttpStatus.BAD_REQUEST);
		return response;
	}
}
