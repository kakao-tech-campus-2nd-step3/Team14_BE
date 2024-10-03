package com.ordertogether.team14_be.spot.dto.controllerdto;

import com.ordertogether.team14_be.spot.enums.Category;

public record SpotViewedResponse(
		Category category, String storeName, Integer minimumOrderAmount, String pickUpLocation) {}
