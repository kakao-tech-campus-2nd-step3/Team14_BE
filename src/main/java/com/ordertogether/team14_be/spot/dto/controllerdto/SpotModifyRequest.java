package com.ordertogether.team14_be.spot.dto.controllerdto;

public record SpotModifyRequest(
		Long id, String storeName, Integer minimumOrderAmount, String pickUpLocation) {}
