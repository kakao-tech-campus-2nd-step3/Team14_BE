package com.ordertogether.team14_be.payment.persistence.jpa.mapper;

import com.ordertogether.team14_be.payment.domain.PaymentEvent;
import com.ordertogether.team14_be.payment.persistence.jpa.entity.PaymentEventEntity;
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

	public static PaymentEvent mapToDomain(PaymentEventEntity entity) {
		return PaymentEvent.builder()
				.id(entity.getId())
				.buyerId(entity.getBuyerId())
				.orderId(entity.getOrderId())
				.orderName(entity.getOrderName())
				.paymentKey(entity.getPaymentKey())
				.paymentStatus(entity.getPaymentStatus())
				.build();
	}
}
