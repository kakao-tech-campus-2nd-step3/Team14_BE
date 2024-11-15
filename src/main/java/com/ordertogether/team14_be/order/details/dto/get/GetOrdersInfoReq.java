package com.ordertogether.team14_be.order.details.dto.get;

import java.util.List;

public record GetOrdersInfoReq(
		Integer page, Integer size, List<String> sort // 첫 번째 - 정렬할 필드명(DB 기준), 두 번째 - 정렬 순서(desc, asc)
		) {}
