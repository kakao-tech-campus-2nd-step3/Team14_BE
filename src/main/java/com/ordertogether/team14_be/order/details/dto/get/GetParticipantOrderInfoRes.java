package com.ordertogether.team14_be.order.details.dto.get;

public record GetParticipantOrderInfoRes(
		String category,
		String storeName,
		int minimumOrderAmount,
		String pickUpLocation,
		String deliveryStatus,
		int price) {}
