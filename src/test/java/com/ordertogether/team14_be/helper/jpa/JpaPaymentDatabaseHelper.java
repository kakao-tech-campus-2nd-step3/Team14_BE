package com.ordertogether.team14_be.helper.jpa;

import com.ordertogether.team14_be.helper.PaymentDatabaseHelper;
import com.ordertogether.team14_be.member.persistence.MemberRepository;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import com.ordertogether.team14_be.payment.domain.PaymentEvent;
import com.ordertogether.team14_be.payment.domain.PaymentOrder;
import com.ordertogether.team14_be.payment.domain.PaymentStatus;
import com.ordertogether.team14_be.payment.domain.Product;
import com.ordertogether.team14_be.payment.persistence.repository.PaymentEventRepository;
import com.ordertogether.team14_be.payment.persistence.repository.PaymentOrderRepository;
import com.ordertogether.team14_be.payment.persistence.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class JpaPaymentDatabaseHelper implements PaymentDatabaseHelper {

	private final JpaDatabaseCleanup jpaDatabaseCleanup;
	private final PaymentEventRepository paymentEventRepository;
	private final PaymentOrderRepository paymentOrderRepository;
	private final ProductRepository productRepository;
	private final MemberRepository memberRepository;

	private String orderId;

	@Override
	public void clean() {
		jpaDatabaseCleanup.execute();
	}

	@Override
	@Transactional
	public void saveTestData() {
		if (Objects.isNull(orderId)) {
			throw new IllegalStateException("orderId is not set");
		}
		memberRepository.save(
				new Member(1L, "member1@example.com", 100000, "010-1234-5678", "member1", "Kakao"));

		productRepository.saveAll(
				List.of(
						Product.builder().id(1L).name("Product 1").price(BigDecimal.valueOf(10000)).build(),
						Product.builder().id(2L).name("Product 2").price(BigDecimal.valueOf(20000)).build(),
						Product.builder().id(3L).name("Product 3").price(BigDecimal.valueOf(30000)).build()));

		List<PaymentOrder> paymentOrders =
				paymentOrderRepository.saveAll(
						List.of(
								PaymentOrder.builder()
										.id(1L)
										.productId(1L)
										.orderId(orderId)
										.orderName("Product 1")
										.amount(BigDecimal.valueOf(10000L))
										.build(),
								PaymentOrder.builder()
										.id(2L)
										.productId(2L)
										.orderId(orderId)
										.orderName("Product 2")
										.amount(BigDecimal.valueOf(20000L))
										.build(),
								PaymentOrder.builder()
										.id(3L)
										.productId(3L)
										.orderId(orderId)
										.orderName("Product 3")
										.amount(BigDecimal.valueOf(30000L))
										.build()));

		paymentEventRepository.save(
				PaymentEvent.builder()
						.id(1L)
						.buyerId(1L)
						.orderId(orderId)
						.paymentOrders(paymentOrders)
						.orderName("Product 1, Product 2, Product 3")
						.paymentStatus(PaymentStatus.READY)
						.build());
	}

	@Override
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
