package com.ordertogether.team14_be.order.details.dto.get;

import com.ordertogether.team14_be.order.details.entity.OrderDetail;
import com.ordertogether.team14_be.spot.entity.Spot;
import java.time.LocalDate;

public record OrderInfo(
		Long id,
		Long spotId,
		String category,
		String storeName,
		int minimumOrderAmount,
		String pickUpLocation,
		LocalDate orderDate,
		int price,
		boolean isCreator) {

	public OrderInfo(Long memberId, OrderDetail order, Spot spot) {
		this(
				order.getId(),
				spot.getId(),
				spot.getCategory().getStringCategory(),
				spot.getStoreName(),
				spot.getMinimumOrderAmount(),
				spot.getPickUpLocation(),
				order.getCreatedAt().toLocalDate(),
				order.getPrice(),
				spot.getMember().getId().equals(memberId));
	}
}
