/*
package com.ordertogether.team14_be.payment.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ordertogether.team14_be.helper.PaymentDatabaseHelper;
import com.ordertogether.team14_be.payment.domain.PaymentOrder;
import com.ordertogether.team14_be.payment.persistence.repository.PaymentOrderRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("결제 유효성 검사 서비스는")
class PaymentValidationServiceTest {

	@Autowired PaymentValidationService paymentValidationService;
	@Autowired PaymentDatabaseHelper paymentDatabaseHelper;
	@Autowired PaymentOrderRepository paymentOrderRepository;

	@BeforeEach
	void setup() {
		paymentDatabaseHelper.clean();

		List<PaymentOrder> paymentOrders = new ArrayList<>();
		for (int i = 1; i < 4; i++) {
			paymentOrders.add(
					PaymentOrder.builder()
							.id((long) i)
							.productId((long) i)
							.orderId("order-id")
							.orderName("product 0" + i)
							.amount(BigDecimal.valueOf(i * 10000))
							.build());
		}

		paymentOrderRepository.saveAll(paymentOrders);
	}

	@Test
	@DisplayName("유효한 결제 요청에 대해 true 를 반환한다.")
	void shouldReturnTrueWhenLegalRequest() {
		// given
		String orderId = "order-id";
		BigDecimal requestedAmount = BigDecimal.valueOf(60000);

		// when
		// then
		assertTrue(paymentValidationService.validate(orderId, requestedAmount));
	}

	@Test
	@DisplayName("결제 금액이 실제 결제할 금액과 다른 결제 요청에 대해 예외를 발생시킨다.")
	void shouldThrowExceptionWhenDifferentAmountRequest() {
		// given
		String orderId = "order-id";
		BigDecimal requestedAmount = BigDecimal.valueOf(50000);

		// when
		// then
		assertThatThrownBy(() -> paymentValidationService.validate(orderId, requestedAmount))
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("존재하지 않는 주문 번호에 대해 예외를 발생시킨다.")
	void shouldThrowExceptionWhenRequestWithInvalidOrderId() {
		String orderId = "invalid-order-id";
		BigDecimal requestedAmount = BigDecimal.valueOf(60000);

		// when
		// then
		assertThatThrownBy(() -> paymentValidationService.validate(orderId, requestedAmount))
				.isInstanceOf(NoSuchElementException.class);
	}
}


 */
