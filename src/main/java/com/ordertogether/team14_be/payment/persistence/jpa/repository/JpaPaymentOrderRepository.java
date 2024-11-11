package com.ordertogether.team14_be.payment.persistence.jpa.repository;

import com.ordertogether.team14_be.payment.domain.PaymentOrder;
import com.ordertogether.team14_be.payment.domain.PaymentStatus;
import com.ordertogether.team14_be.payment.persistence.jpa.entity.PaymentOrderEntity;
import com.ordertogether.team14_be.payment.persistence.jpa.entity.ProductEntity;
import com.ordertogether.team14_be.payment.persistence.jpa.mapper.PaymentOrderMapper;
import com.ordertogether.team14_be.payment.persistence.jpa.mapper.ProductMapper;
import com.ordertogether.team14_be.payment.persistence.repository.PaymentOrderRepository;
import com.ordertogether.team14_be.payment.web.dto.PaymentHistory;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaPaymentOrderRepository implements PaymentOrderRepository {

	private final SimpleJpaPaymentOrderRepository simpleJpaPaymentOrderRepository;
	private final SimpleJpaProductRepository simpleJpaProductRepository;

	/**
	 * 결제 주문 정보를 저장한다.<br>
	 * - 결제 주문 정보에 상품 정보가 없는 경우 상품 정보를 조회하여 결제 주문 정보에 추가한다.
	 *
	 * @param paymentOrder 결제 주문 정보
	 * @return 저장된 결제 주문 정보
	 */
	@Override
	@Transactional
	public PaymentOrder save(PaymentOrder paymentOrder) {
		addMissingProductInfo(paymentOrder);
		PaymentOrderEntity savedEntity =
				simpleJpaPaymentOrderRepository.save(PaymentOrderMapper.mapToEntity(paymentOrder));

		return PaymentOrderMapper.mapToDomain(savedEntity);
	}

	private void addMissingProductInfo(PaymentOrder paymentOrder) {
		if (paymentOrder.isMissingProductInfo()) {
			ProductEntity productEntity = getProductEntity(paymentOrder);
			paymentOrder.updateProductInfo(ProductMapper.mapToDomain(productEntity));
		}
	}

	@Override
	@Transactional
	public List<PaymentOrder> saveAll(List<PaymentOrder> paymentOrders) {
		List<PaymentOrderEntity> savedEntities =
				simpleJpaPaymentOrderRepository.saveAll(
						paymentOrders.stream().map(PaymentOrderMapper::mapToEntity).toList());

		return PaymentOrderMapper.mapToDomain(savedEntities);
	}

	@Override
	public Optional<PaymentOrder> findById(Long id) {
		return simpleJpaPaymentOrderRepository.findById(id).map(PaymentOrderMapper::mapToDomain);
	}

	@Override
	public List<PaymentOrder> findByOrderId(String orderId) {
		return simpleJpaPaymentOrderRepository.findByOrderId(orderId).stream()
				.map(PaymentOrderMapper::mapToDomain)
				.toList();
	}

	@Override
	public BigDecimal getPaymentTotalAmount(String orderId) {
		return simpleJpaPaymentOrderRepository
				.getPaymentTotalAmount(orderId)
				.orElseThrow(
						() -> new NoSuchElementException("주문 번호: %s 에 해당하는 주문이 존재하지 않습니다.".formatted(orderId)));
	}

	@Override
	public List<PaymentHistory> getChargeHistory(Long memberId, PaymentStatus paymentStatus) {
		return simpleJpaPaymentOrderRepository.getChargeHistory(memberId, paymentStatus);
	}

	private ProductEntity getProductEntity(PaymentOrder paymentOrder) {
		return simpleJpaProductRepository
				.findById(paymentOrder.getProductId())
				.orElseThrow(
						() ->
								new NoSuchElementException(
										String.format("상품 아이디 %s에 해당하는 상품이 없습니다.", paymentOrder.getProductId())));
	}
}
