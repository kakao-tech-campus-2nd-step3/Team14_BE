package com.ordertogether.team14_be.spot.exception;

import com.ordertogether.team14_be.spot.enums.ErrorCode;
import lombok.Getter;

@Getter
public class NotSpotMasterException extends RuntimeException {

	private final ErrorCode errorCode;

	public NotSpotMasterException(String message) {
		super(message);
		this.errorCode = ErrorCode.NOT_SPOT_MASTER;
	}
}
