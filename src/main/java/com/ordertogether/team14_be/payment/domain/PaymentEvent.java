package com.ordertogether.team14_be.payment.domain;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class PaymentEvent {

	private Long id;

	private Long buyerId;

	private List<PaymentOrder> paymentOrders;

	private String orderId;

	private String orderName;

	private String paymentKey;

	@Builder.Default private PaymentStatus paymentStatus = PaymentStatus.READY;

	public Long totalAmount() {
		return paymentOrders.stream()
				.map(PaymentOrder::getAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add)
				.longValue();
	}
}
