package com.ordertogether.team14_be.payment.web.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PaymentPrepareRequest {

	@JsonIgnore(false)
	private Long buyerId;

	private final String idempotencySeed;

	private final List<Long> productIds;

	public PaymentPrepareRequest addBuyerId(Long buyerId) {
		this.buyerId = buyerId;
		return this;
	}
}
