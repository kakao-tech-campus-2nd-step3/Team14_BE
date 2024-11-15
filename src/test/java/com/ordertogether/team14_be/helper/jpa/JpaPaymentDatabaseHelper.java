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
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

// @Component
@RequiredArgsConstructor
public class JpaPaymentDatabaseHelper implements PaymentDatabaseHelper {

	private final JpaDatabaseCleanup jpaDatabaseCleanup;
	private final PaymentEventRepository paymentEventRepository;
	private final PaymentOrderRepository paymentOrderRepository;
	private final ProductRepository productRepository;
	private final MemberRepository memberRepository;

	@Override
	public void clean() {
		jpaDatabaseCleanup.execute();
	}

	@Override
	@Transactional
	public void saveTestData() {
		// Save members
		memberRepository.saveAll(
				List.of(
						new Member(1L, "member1@example.com", 1000000, "010-0000-0001", "Member01", "Kakao"),
						new Member(2L, "member2@example.com", 1000000, "010-0000-0002", "Member02", "Kakao")));

		// Save products
		productRepository.saveAll(
				List.of(
						Product.builder().id(1L).name("Product 1").price(BigDecimal.valueOf(10000)).build(),
						Product.builder().id(2L).name("Product 2").price(BigDecimal.valueOf(20000)).build(),
						Product.builder().id(3L).name("Product 3").price(BigDecimal.valueOf(30000)).build()));

		// Save payment orders and events
		List<PaymentOrder> paymentOrders1 =
				paymentOrderRepository.saveAll(
						List.of(
								PaymentOrder.builder()
										.id(1L)
										.productId(1L)
										.orderId("test-order-id-1")
										.orderName("Product 1")
										.amount(BigDecimal.valueOf(10000))
										.build(),
								PaymentOrder.builder()
										.id(2L)
										.productId(2L)
										.orderId("test-order-id-1")
										.orderName("Product 2")
										.amount(BigDecimal.valueOf(20000))
										.build(),
								PaymentOrder.builder()
										.id(3L)
										.productId(3L)
										.orderId("test-order-id-1")
										.orderName("Product 3")
										.amount(BigDecimal.valueOf(30000))
										.build()));

		paymentEventRepository.save(
				PaymentEvent.builder()
						.id(1L)
						.buyerId(1L)
						.orderId("test-order-id-1")
						.paymentOrders(paymentOrders1)
						.orderName("Product 1, Product 2, Product 3")
						.paymentStatus(PaymentStatus.READY)
						.build());

		List<PaymentOrder> paymentOrders2 =
				paymentOrderRepository.saveAll(
						List.of(
								PaymentOrder.builder()
										.id(4L)
										.productId(1L)
										.orderId("test-order-id-2")
										.orderName("Product 1")
										.amount(BigDecimal.valueOf(10000))
										.build(),
								PaymentOrder.builder()
										.id(5L)
										.productId(2L)
										.orderId("test-order-id-2")
										.orderName("Product 2")
										.amount(BigDecimal.valueOf(20000))
										.build()));

		paymentEventRepository.save(
				PaymentEvent.builder()
						.id(2L)
						.buyerId(1L)
						.orderId("test-order-id-2")
						.paymentOrders(paymentOrders2)
						.orderName("Product 1, Product 2")
						.paymentStatus(PaymentStatus.SUCCESS)
						.build());

		List<PaymentOrder> paymentOrders3 =
				paymentOrderRepository.saveAll(
						List.of(
								PaymentOrder.builder()
										.id(6L)
										.productId(1L)
										.orderId("test-order-id-3")
										.orderName("Product 1")
										.amount(BigDecimal.valueOf(10000))
										.build(),
								PaymentOrder.builder()
										.id(7L)
										.productId(3L)
										.orderId("test-order-id-3")
										.orderName("Product 3")
										.amount(BigDecimal.valueOf(30000))
										.build()));

		paymentEventRepository.save(
				PaymentEvent.builder()
						.id(3L)
						.buyerId(1L)
						.orderId("test-order-id-3")
						.paymentOrders(paymentOrders3)
						.orderName("Product 1, Product 3")
						.paymentStatus(PaymentStatus.FAIL)
						.build());

		List<PaymentOrder> paymentOrders4 =
				paymentOrderRepository.saveAll(
						List.of(
								PaymentOrder.builder()
										.id(8L)
										.productId(1L)
										.orderId("test-order-id-4")
										.orderName("Product 1")
										.amount(BigDecimal.valueOf(10000))
										.build()));

		paymentEventRepository.save(
				PaymentEvent.builder()
						.id(4L)
						.buyerId(2L)
						.orderId("test-order-id-4")
						.paymentOrders(paymentOrders4)
						.orderName("Product 1")
						.paymentStatus(PaymentStatus.SUCCESS)
						.build());

		List<PaymentOrder> paymentOrders5 =
				paymentOrderRepository.saveAll(
						List.of(
								PaymentOrder.builder()
										.id(9L)
										.productId(1L)
										.orderId("test-order-id-5")
										.orderName("Product 1")
										.amount(BigDecimal.valueOf(10000))
										.build(),
								PaymentOrder.builder()
										.id(10L)
										.productId(2L)
										.orderId("test-order-id-5")
										.orderName("Product 2")
										.amount(BigDecimal.valueOf(20000))
										.build()));

		paymentEventRepository.save(
				PaymentEvent.builder()
						.id(5L)
						.buyerId(2L)
						.orderId("test-order-id-5")
						.paymentOrders(paymentOrders5)
						.orderName("Product 1, Product 2")
						.paymentStatus(PaymentStatus.SUCCESS)
						.build());
	}
}
