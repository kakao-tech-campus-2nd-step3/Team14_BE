package com.ordertogether.team14_be.payment.service;

import com.ordertogether.team14_be.common.util.IdempotentKeyGenerator;
import com.ordertogether.team14_be.payment.domain.PaymentEvent;
import com.ordertogether.team14_be.payment.domain.PaymentOrder;
import com.ordertogether.team14_be.payment.domain.Product;
import com.ordertogether.team14_be.payment.persistence.repository.PaymentEventRepository;
import com.ordertogether.team14_be.payment.persistence.repository.PaymentOrderRepository;
import com.ordertogether.team14_be.payment.persistence.repository.ProductRepository;
import com.ordertogether.team14_be.payment.web.request.PaymentPrepareRequest;
import com.ordertogether.team14_be.payment.web.response.PaymentPrepareResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
/** 결제 시작 전, 결제 정보를 저장하는 서비스 */
public class PaymentPreparationService {

	private final PaymentEventRepository paymentEventRepository;
	private final PaymentOrderRepository paymentOrderRepository;
	private final ProductRepository productRepository;

	/**
	 * 결제 정보를 저장한다.<br>
	 * {@link PaymentEvent} 와 {@link PaymentOrder} 를 저장한다.
	 *
	 * @param request 결제 정보
	 * @return 저장된 결제 정보
	 */
	@Transactional
	public PaymentPrepareResponse prepare(PaymentPrepareRequest request) {
		validateDuplicatePayment(request);

		List<Product> products = productRepository.findByIdIn(request.getProductIds());
		List<PaymentOrder> paymentOrders =
				paymentOrderRepository.saveAll(createPaymentOrders(products, request.getIdempotencySeed()));
		PaymentEvent paymentEvent = paymentEventRepository.save(createPaymentEvent(request, products));

		return PaymentPrepareResponse.of(paymentEvent, paymentOrders);
	}

	/**
	 * 중복 결제 요청인지 확인한다.
	 *
	 * @param request 결제 정보
	 * @throws IllegalArgumentException 중복 결제 요청일 경우
	 */
	private void validateDuplicatePayment(PaymentPrepareRequest request) {
		String idempotentKey = IdempotentKeyGenerator.generate(request.getIdempotencySeed());

		paymentEventRepository
				.findByOrderId(idempotentKey)
				.ifPresent(
						duplicatedPaymentEvent -> {
							throw new IllegalArgumentException(
									"Seed: %s 를 통해 생성된 결제는 이미 %s 상태인 주문입니다."
											.formatted(
													request.getIdempotencySeed(),
													duplicatedPaymentEvent.getPaymentStatus().getDescription()));
						});
	}

	private List<PaymentOrder> createPaymentOrders(List<Product> products, String idempotencySeed) {
		return products.stream().map(product -> createPaymentOrder(product, idempotencySeed)).toList();
	}

	private PaymentOrder createPaymentOrder(Product product, String idempotencySeed) {
		return PaymentOrder.builder()
				.productId(product.getId())
				.orderId(IdempotentKeyGenerator.generate(idempotencySeed))
				.orderName(product.getName())
				.amount(product.getPrice())
				.build();
	}

	private PaymentEvent createPaymentEvent(PaymentPrepareRequest request, List<Product> products) {
		String idempotencySeed = request.getIdempotencySeed();

		return PaymentEvent.builder()
				.buyerId(request.getBuyerId())
				.paymentOrders(
						products.stream().map(product -> createPaymentOrder(product, idempotencySeed)).toList())
				.orderId(IdempotentKeyGenerator.generate(idempotencySeed))
				.orderName(createOrderName(products))
				.build();
	}

	private String createOrderName(List<Product> products) {
		return String.join(",", products.stream().map(Product::getName).toList());
	}
}
