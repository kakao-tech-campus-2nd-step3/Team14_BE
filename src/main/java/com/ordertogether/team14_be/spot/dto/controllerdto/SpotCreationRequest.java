package com.ordertogether.team14_be.spot.dto.controllerdto;

import java.math.BigDecimal;

public record SpotCreationRequest(
		Long id,
		BigDecimal lat,
		BigDecimal lng,
		String storeName,
		Integer minimumOrderAmount,
		String togetherOrderLink,
		String pickUpLocation) {}
