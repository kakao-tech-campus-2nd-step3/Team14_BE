package com.ordertogether.team14_be.payment.persistence.jpa.mapper;

import com.ordertogether.team14_be.payment.domain.PaymentEvent;
import com.ordertogether.team14_be.payment.domain.PaymentOrder;
import com.ordertogether.team14_be.payment.persistence.jpa.entity.PaymentEventEntity;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class PaymentEventMapper {

	public static PaymentEventEntity mapToEntity(PaymentEvent domain) {
		return PaymentEventEntity.builder()
				.id(domain.getId())
				.buyerId(domain.getBuyerId())
				.orderId(domain.getOrderId())
				.orderName(domain.getOrderName())
				.paymentKey(domain.getPaymentKey())
				.paymentStatus(domain.getPaymentStatus())
				.build();
	}

	public static PaymentEvent mapToDomain(
			PaymentEventEntity paymentEventEntity, List<PaymentOrder> paymentOrders) {
		return PaymentEvent.builder()
				.id(paymentEventEntity.getId())
				.buyerId(paymentEventEntity.getBuyerId())
				.paymentOrders(paymentOrders)
				.orderId(paymentEventEntity.getOrderId())
				.orderName(paymentEventEntity.getOrderName())
				.paymentKey(paymentEventEntity.getPaymentKey())
				.paymentStatus(paymentEventEntity.getPaymentStatus())
				.build();
	}
}
