package com.ordertogether.team14_be.spot.dto.controllerdto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalTime;

public record SpotCreationRequest(
		Long id,
		BigDecimal lat,
		BigDecimal lng,
		@NotNull(message = "가게 이름을 입력해주세요") String storeName,
		@NotNull(message = "카테고리를 선택해주세요") String category,
		@NotNull(message = "최소 주문 금액을 입력해주세요") Integer minimumOrderAmount,
		@NotNull(message = "배달의 민족 함께 주문링크를 입력해주세요") String togetherOrderLink,
		@NotNull(message = "픽업 장소를 입력해주세요") String pickUpLocation,
		@NotNull(message = "주문 마감 시간을 입력해주세요") LocalTime deadlineTime) {}
