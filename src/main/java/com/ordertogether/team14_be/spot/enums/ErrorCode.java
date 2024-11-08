package com.ordertogether.team14_be.spot.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
	INVALID_REQUEST("400", "Invalid request"),
	NOT_FOUND("404", "Not found"),
	INTERNAL_ERROR("500", "Internal server error"),
	SPOT_NOT_FOUND("404", "Spot not found"),
	NULL_VALUE_NOT_ALLOWED("400", "Null value not allowed"),
	NOT_SPOT_MASTER("401", "Not spot master");

	private final String code;
	private final String message;

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	// ENUM을 코드(String)으로 변환
	public static String fromEnumToString(ErrorCode errorCode) {
		if (errorCode == null) {
			throw new IllegalArgumentException("존재하지 않는 코드입니다.");
		}
		return errorCode.getCode();
	}
}
