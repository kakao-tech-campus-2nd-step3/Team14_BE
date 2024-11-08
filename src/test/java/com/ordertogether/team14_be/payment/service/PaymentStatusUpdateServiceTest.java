package com.ordertogether.team14_be.payment.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ordertogether.team14_be.helper.PaymentDatabaseHelper;
import com.ordertogether.team14_be.payment.domain.PaymentEvent;
import com.ordertogether.team14_be.payment.domain.PaymentOrder;
import com.ordertogether.team14_be.payment.domain.PaymentStatus;
import com.ordertogether.team14_be.payment.persistence.repository.PaymentEventRepository;
import com.ordertogether.team14_be.payment.service.command.PaymentStatusUpdateCommand;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PaymentStatusUpdateServiceTest {

	@Autowired PaymentStatusUpdateService paymentStatusUpdateService;

	@Autowired PaymentDatabaseHelper paymentDatabaseHelper;

	@Autowired PaymentEventRepository paymentEventRepository;

	@BeforeEach
	void setup() {
		paymentDatabaseHelper.clean();

		paymentEventRepository.save(
				PaymentEvent.builder()
						.id(1L)
						.paymentOrders(
								List.of(
										PaymentOrder.builder()
												.id(1L)
												.orderId("test-order-id")
												.orderName("test-order-name01")
												.amount(BigDecimal.valueOf(1000))
												.productId(1L)
												.build(),
										PaymentOrder.builder()
												.id(2L)
												.orderId("test-order-id")
												.orderName("test-order-name02")
												.amount(BigDecimal.valueOf(2000))
												.productId(2L)
												.build()))
						.paymentStatus(PaymentStatus.READY)
						.buyerId(1L)
						.paymentKey("test-payment-key")
						.orderId("test-order-id")
						.orderName("test-order-name01, test-order-name02")
						.build());
	}

	@ParameterizedTest(name = "PaymentEvent의 상태를 READY 에서 {0} 으로 변경할 수 있다.")
	@EnumSource
	void shouldUpdateStatusWithNormalRequest(PaymentStatus paymentStatus) {
		PaymentStatusUpdateCommand command =
				new PaymentStatusUpdateCommand("test-payment-key", "test-order-id", paymentStatus);
		paymentStatusUpdateService.updatePaymentStatus(command);

		PaymentEvent result = paymentEventRepository.findByOrderId("test-order-id").get();

		assertThat(result.getPaymentStatus()).isEqualTo(paymentStatus);
	}
}
