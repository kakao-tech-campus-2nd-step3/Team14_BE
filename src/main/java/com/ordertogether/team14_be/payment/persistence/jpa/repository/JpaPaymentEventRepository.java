package com.ordertogether.team14_be.payment.persistence.jpa.repository;

import com.ordertogether.team14_be.payment.domain.PaymentEvent;
import com.ordertogether.team14_be.payment.persistence.jpa.entity.PaymentEventEntity;
import com.ordertogether.team14_be.payment.persistence.jpa.mapper.PaymentEventMapper;
import com.ordertogether.team14_be.payment.persistence.repository.PaymentEventRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaPaymentEventRepository implements PaymentEventRepository {

	private final SimpleJpaPaymentEventRepository simpleJpaPaymentEventRepository;

	@Override
	public PaymentEvent save(PaymentEvent paymentEvent) {
		PaymentEventEntity savedEntity =
				simpleJpaPaymentEventRepository.save(PaymentEventMapper.mapToEntity(paymentEvent));
		return PaymentEventMapper.mapToDomain(savedEntity);
	}

	@Override
	public Optional<PaymentEvent> findById(Long id) {
		return simpleJpaPaymentEventRepository.findById(id).map(PaymentEventMapper::mapToDomain);
	}

	@Override
	public Optional<PaymentEvent> findByOrderId(String orderId) {
		return simpleJpaPaymentEventRepository
				.findByOrderId(orderId)
				.map(PaymentEventMapper::mapToDomain);
	}
}
