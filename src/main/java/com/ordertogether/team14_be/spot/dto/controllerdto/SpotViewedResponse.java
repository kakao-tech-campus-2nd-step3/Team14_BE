package com.ordertogether.team14_be.spot.dto.controllerdto;

public record SpotViewedResponse(
		String category, String storeName, Integer minimumOrderAmount, String pickUpLocation) {}
