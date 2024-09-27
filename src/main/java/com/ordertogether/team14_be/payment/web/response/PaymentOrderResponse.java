package com.ordertogether.team14_be.payment.web.response;

import com.ordertogether.team14_be.payment.domain.PaymentOrder;

public record PaymentOrderResponse(
		Long paymentOrderId, Long productId, String orderId, String orderName, Long amount) {
	public static PaymentOrderResponse from(PaymentOrder paymentOrder) {
		return new PaymentOrderResponse(
				paymentOrder.getId(),
				paymentOrder.getProductId(),
				paymentOrder.getOrderId(),
				paymentOrder.getOrderName(),
				paymentOrder.getAmount().longValue());
	}
}
