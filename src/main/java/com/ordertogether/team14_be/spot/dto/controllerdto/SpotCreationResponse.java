package com.ordertogether.team14_be.spot.dto.controllerdto;

import java.time.LocalTime;
import lombok.Builder;

@Builder
public record SpotCreationResponse(
		Long id,
		String category,
		String storeName,
		Integer minimumOrderAmount,
		String pickUpLocation,
		LocalTime deadlineTime) {}
