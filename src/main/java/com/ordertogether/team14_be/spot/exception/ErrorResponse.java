package com.ordertogether.team14_be.spot.exception;

import com.ordertogether.team14_be.spot.enums.ErrorCode;
import lombok.Getter;

@Getter
public record ErrorResponse(String message, ErrorCode code) {}
