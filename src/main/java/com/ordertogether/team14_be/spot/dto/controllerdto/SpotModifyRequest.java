package com.ordertogether.team14_be.spot.dto.controllerdto;

import jakarta.validation.constraints.NotNull;

public record SpotModifyRequest(
		Long id,
		@NotNull(message = "가게 이름은 비어있을 수 없습니다.") String storeName,
		@NotNull(message = "최소 주문 금액은 비어있을 수 없습니다.") Integer minimumOrderAmount,
		@NotNull(message = "픽업 장소는은 비어있을 수 없습니다.") String pickUpLocation) {}
