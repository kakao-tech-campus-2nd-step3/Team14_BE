package com.ordertogether.team14_be.spot.dto.controllerdto;

public record SpotDetailResponse(
		String category,
		String storeName,
		Integer minimumOrderAmount,
		String pickUpLocation,
		String deliveryStatus) {}
