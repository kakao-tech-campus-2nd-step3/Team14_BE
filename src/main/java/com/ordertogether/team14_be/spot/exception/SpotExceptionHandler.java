package com.ordertogether.team14_be.spot.exception;

import com.ordertogether.team14_be.spot.controller.SpotController;
import com.ordertogether.team14_be.spot.enums.ErrorCode;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = SpotController.class)
public class SpotExceptionHandler {

	@ExceptionHandler(SpotNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleSpotNotFoundException(SpotNotFoundException ex) {
		return ResponseEntity.badRequest()
				.body(new ErrorResponse(ex.getMessage(), ErrorCode.SPOT_NOT_FOUND));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
			ConstraintViolationException ex) {
		return ResponseEntity.badRequest()
				.body(new ErrorResponse(ex.getMessage(), ErrorCode.NULL_VALUE_NOT_ALLOWED));
	}
}
