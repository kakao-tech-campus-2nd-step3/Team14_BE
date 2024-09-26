package com.ordertogether.team14_be.payment.web.response;

import com.ordertogether.team14_be.payment.domain.PaymentOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PaymentOrderResponse {

	private final Long paymentOrderId;

	private final Long productId;

	private final String orderId;

	private final String orderName;

	private final Long amount;

	public static PaymentOrderResponse from(PaymentOrder paymentOrder) {
		return PaymentOrderResponse.builder()
				.paymentOrderId(paymentOrder.getId())
				.productId(paymentOrder.getProductId())
				.orderId(paymentOrder.getOrderId())
				.orderName(paymentOrder.getOrderName())
				.amount(paymentOrder.getAmount().longValue())
				.build();
	}
}
