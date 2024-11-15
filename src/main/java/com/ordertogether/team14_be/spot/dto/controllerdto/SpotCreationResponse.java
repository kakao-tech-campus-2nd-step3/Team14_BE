package com.ordertogether.team14_be.spot.dto.controllerdto;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record SpotCreationResponse(
		Long id,
		BigDecimal lat,
		BigDecimal lng,
		String category,
		String storeName,
		Integer minimumOrderAmount,
		String pickUpLocation) {}
