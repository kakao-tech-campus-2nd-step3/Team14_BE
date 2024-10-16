package com.ordertogether.team14_be.common.web.handler;

import com.ordertogether.team14_be.member.application.exception.NotFoundMember;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NotFoundMember.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<String> handleAlreadyExistMemberException(NotFoundMember e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
}
