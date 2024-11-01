package com.ordertogether.team14_be.spot.dto.controllerdto;

import com.ordertogether.team14_be.spot.enums.Category;
import java.time.LocalTime;

public record SpotCreationResponse(
		Long id,
		Category category,
		String storeName,
		Integer minimumOrderAmount,
		String pickUpLocation,
		LocalTime deadlineTime) {}
