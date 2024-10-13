package com.ordertogether.team14_be.spot.exception;

import com.ordertogether.team14_be.spot.enums.ErrorCode;
import lombok.Getter;

@Getter
public class SpotNotFoundException extends RuntimeException {

	private final ErrorCode errorCode;

	public SpotNotFoundException(String message) {
		super(message);
		this.errorCode = ErrorCode.SPOT_NOT_FOUND;
	}
}
