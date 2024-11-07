package com.ordertogether.team14_be.order.details.dto.get;

import com.ordertogether.team14_be.order.details.entity.OrderDetail;
import com.ordertogether.team14_be.spot.entity.Spot;

public record OrderInfo(
		Long id,
		String category,
		String storeName,
		int minimumOrderAmount,
		String pickUpLocation,
		String deliveryStatus,
		int price,
		boolean isCreator) {

	public OrderInfo(Long memberId, OrderDetail order, Spot spot) {
		this(
				order.getId(),
				spot.getCategory().toString(),
				spot.getStoreName(),
				spot.getMinimumOrderAmount(),
				spot.getPickUpLocation(),
				spot.getDeliveryStatus(),
				order.getPrice(),
				spot.getMember().getId().equals(memberId));
	}
}
