package com.ordertogether.team14_be.order.details.dto.get;

import java.util.List;

public record GetCreatorOrderInfoRes(
		String category,
		String storeName,
		int minimumOrderAmount,
		String pickUpLocation,
		String deliveryStatus,
		List<MemberBriefInfo> memberInfo) {}
