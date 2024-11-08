package com.ordertogether.team14_be.payment.persistence.jpa.repository;

import com.ordertogether.team14_be.payment.domain.PaymentEvent;
import com.ordertogether.team14_be.payment.domain.PaymentOrder;
import com.ordertogether.team14_be.payment.domain.PaymentStatus;
import com.ordertogether.team14_be.payment.persistence.jpa.entity.PaymentEventEntity;
import com.ordertogether.team14_be.payment.persistence.jpa.mapper.PaymentEventMapper;
import com.ordertogether.team14_be.payment.persistence.repository.PaymentEventRepository;
import com.ordertogether.team14_be.payment.persistence.repository.PaymentOrderRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaPaymentEventRepository implements PaymentEventRepository {

	private final SimpleJpaPaymentEventRepository simpleJpaPaymentEventRepository;
	private final PaymentOrderRepository paymentOrderRepository;

	@Override
	@Transactional
	public PaymentEvent save(PaymentEvent paymentEvent) {
		PaymentEventEntity savedEntity =
				simpleJpaPaymentEventRepository.save(PaymentEventMapper.mapToEntity(paymentEvent));
		return PaymentEventMapper.mapToDomain(savedEntity, paymentEvent.getPaymentOrders());
	}

	@Override
	public Optional<PaymentEvent> findByOrderId(String orderId) {
		List<PaymentOrder> paymentOrders = paymentOrderRepository.findByOrderId(orderId);
		return simpleJpaPaymentEventRepository
				.findByOrderId(orderId)
				.map(paymentEvent -> PaymentEventMapper.mapToDomain(paymentEvent, paymentOrders));
	}

	@Override
	@Transactional
	public Integer updatePaymentStatus(
			String paymentKey, String orderId, PaymentStatus paymentStatus) {
		return simpleJpaPaymentEventRepository.updatePaymentStatus(paymentKey, orderId, paymentStatus);
	}

	@Override
	@Transactional
	public Integer updatePaymentStatusToExecuting(String orderId, String paymentKey) {
		checkPreviousPaymentStatus(orderId);
		simpleJpaPaymentEventRepository.updatePaymentKey(paymentKey, orderId);

		return simpleJpaPaymentEventRepository.updatePaymentStatus(
				paymentKey, orderId, PaymentStatus.EXECUTING);
	}

	private void checkPreviousPaymentStatus(String orderId) {
		PaymentStatus previousStatus =
				findByOrderId(orderId)
						.orElseThrow(
								() ->
										new IllegalArgumentException(
												"orderId: %s 에 해당하는 결제 정보가 없습니다.".formatted(orderId)))
						.getPaymentStatus();

		if (previousStatus.isSuccess() || previousStatus.isFail()) {
			throw new IllegalArgumentException(
					"이미 %s 상태인 결제 입니다.".formatted(previousStatus.getDescription()));
		}
	}
}
