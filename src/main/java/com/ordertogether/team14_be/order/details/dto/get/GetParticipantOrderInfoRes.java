package com.ordertogether.team14_be.order.details.dto.get;

import java.time.LocalDateTime;

public record GetParticipantOrderInfoRes(
		Long orderId,
		String category,
		String storeName,
		int minimumOrderAmount,
		String pickUpLocation,
		LocalDateTime orderDate,
		int price) {}
