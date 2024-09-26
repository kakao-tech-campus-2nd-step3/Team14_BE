package com.ordertogether.team14_be.payment.web.response;

import com.ordertogether.team14_be.payment.domain.PaymentEvent;
import com.ordertogether.team14_be.payment.domain.PaymentOrder;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PaymentPrepareResponse {

	private Long paymentEventId;
	private Long buyerId;
	private List<PaymentOrderResponse> paymentOrders;
	private String orderId;
	private String orderName;
	private String paymentKey;

	public static PaymentPrepareResponse of(
			PaymentEvent paymentEvent, List<PaymentOrder> paymentOrders) {
		return PaymentPrepareResponse.builder()
				.paymentEventId(paymentEvent.getId())
				.buyerId(paymentEvent.getBuyerId())
				.paymentOrders(paymentOrders.stream().map(PaymentOrderResponse::from).toList())
				.orderId(paymentEvent.getOrderId())
				.orderName(paymentEvent.getOrderName())
				.paymentKey(paymentEvent.getPaymentKey())
				.build();
	}
}
