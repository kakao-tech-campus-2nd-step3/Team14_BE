package com.ordertogether.team14_be.payment.web.response;

import com.ordertogether.team14_be.payment.domain.PaymentEvent;
import com.ordertogether.team14_be.payment.domain.PaymentOrder;
import java.util.List;

public record PaymentPrepareResponse(
		Long paymentEventId,
		Long buyerId,
		List<PaymentOrderResponse> paymentOrders,
		String orderId,
		String orderName,
		String paymentKey) {
	public static PaymentPrepareResponse of(
			PaymentEvent paymentEvent, List<PaymentOrder> paymentOrders) {
		return new PaymentPrepareResponse(
				paymentEvent.getId(),
				paymentEvent.getBuyerId(),
				paymentOrders.stream().map(PaymentOrderResponse::from).toList(),
				paymentEvent.getOrderId(),
				paymentEvent.getOrderName(),
				paymentEvent.getPaymentKey());
	}
}
