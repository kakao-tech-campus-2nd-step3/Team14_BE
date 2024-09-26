package com.ordertogether.team14_be.payment.persistence.jpa.mapper;

import com.ordertogether.team14_be.payment.domain.PaymentOrder;
import com.ordertogether.team14_be.payment.persistence.jpa.entity.PaymentOrderEntity;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class PaymentOrderMapper {

	public static PaymentOrderEntity mapToEntity(PaymentOrder domain) {
		return PaymentOrderEntity.builder()
				.id(domain.getId())
				.productId(domain.getProductId())
				.orderId(domain.getOrderId())
				.orderName(domain.getOrderName())
				.amount(domain.getAmount())
				.build();
	}

	public static PaymentOrder mapToDomain(PaymentOrderEntity entity) {
		return PaymentOrder.builder()
				.id(entity.getId())
				.productId(entity.getProductId())
				.orderId(entity.getOrderId())
				.orderName(entity.getOrderName())
				.amount(entity.getAmount())
				.build();
	}

	public static List<PaymentOrder> mapToDomain(List<PaymentOrderEntity> entities) {
		return entities.stream().map(PaymentOrderMapper::mapToDomain).toList();
	}
}
