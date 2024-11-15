package com.ordertogether.team14_be.spot.dto.controllerdto;

import java.math.BigDecimal;
import java.time.LocalTime;

public record SpotModifyResponse(
		BigDecimal lat,
		BigDecimal lng,
		String storeName,
		String category,
		Integer minimumOrderAmount,
		String togetherOrderLink,
		String pickUpLocation,
		LocalTime deadlineTime) {}
