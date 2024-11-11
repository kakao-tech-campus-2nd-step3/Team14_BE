package com.ordertogether.team14_be.spot.exception;

import com.ordertogether.team14_be.spot.controller.SpotController;
import com.ordertogether.team14_be.spot.enums.ErrorCode;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = SpotController.class)
public class SpotExceptionHandler {

	@ExceptionHandler({SpotNotFoundException.class, NotSpotMasterException.class})
	public ResponseEntity<String> handleSpotNotFoundException(SpotNotFoundException ex) {
		return ResponseEntity.status(
						HttpStatus.valueOf(Integer.parseInt(ErrorCode.fromEnumToString(ex.getErrorCode()))))
				.body(ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleMethodArgumentNotValidException(
			ConstraintViolationException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {
		return ResponseEntity.internalServerError().body(ex.getMessage());
	}
}
