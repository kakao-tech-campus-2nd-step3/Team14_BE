package com.ordertogether.team14_be.order.details.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateOrderDetailRes {
	private Long id;
	private int price;
	private boolean isPayed;
	private String participantName; // 참여자의 이름
	private String spotName; // 스팟(가게)의 이름
}
