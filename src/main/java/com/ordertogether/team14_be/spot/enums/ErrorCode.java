package com.ordertogether.team14_be.spot.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
	INVALID_REQUEST("400", "Invalid request"),
	NOT_FOUND("404", "Not found"),
	INTERNAL_ERROR("500", "Internal server error"),
	SPOT_NOT_FOUND("404", "Spot not found"),
	NULL_VALUE_NOT_ALLOWED("400", "Null value not allowed");

	private final String code;
	private final String message;

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
