package com.ordertogether.team14_be.order.details.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateOrderDetailReq {
	private int price;
	private boolean isPayed;
	private Long participantId; // 참여자 아이디
	private Long spotId; // Spot ID (스팟 정보)
}
