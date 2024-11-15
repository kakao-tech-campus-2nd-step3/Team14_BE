package com.ordertogether.team14_be.auth.persistence.exception;

public class InvalidToken extends IllegalAccessException {
	public InvalidToken() {
		super("토큰이 유효하지 않습니다.");
	}
}
