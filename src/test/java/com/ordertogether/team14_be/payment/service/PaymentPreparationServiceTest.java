package com.ordertogether.team14_be.payment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.ordertogether.team14_be.helper.PaymentDatabaseHelper;
import com.ordertogether.team14_be.payment.web.request.PaymentPrepareRequest;
import com.ordertogether.team14_be.payment.web.response.PaymentPrepareResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class PaymentPreparationServiceTest {

	@Autowired private PaymentPreparationService paymentPreparationService;
	@Autowired private PaymentDatabaseHelper paymentDatabaseHelper;

	@BeforeEach
	void setup() {
		paymentDatabaseHelper.clean();
		paymentDatabaseHelper.saveTestData();
	}

	@Test
	@DisplayName("결제 정보를 성공적으로 저장할 수 있다.")
	void shouldSuccessWhenNormalRequest() {
		// given
		PaymentPrepareRequest request =
				new PaymentPrepareRequest("idempotency-seed", List.of(1L, 2L, 3L)).addBuyerId(1L);

		// then
		PaymentPrepareResponse response = paymentPreparationService.prepare(request);

		// when
		assertThat(response.paymentEventId()).isNotNull();
		assertThat(response.buyerId()).isEqualTo(1L);
		assertThat(response.paymentOrders()).hasSize(3);
		assertThat(response.orderId()).isNotNull();
		assertThat(response.orderName()).isEqualTo("Product 1,Product 2,Product 3");
		assertThat(response.paymentKey()).isNull();
		response.paymentOrders().stream()
				.forEach(
						paymentOrder -> {
							assertAll(
									() -> assertThat(paymentOrder.paymentOrderId()).isNotNull(),
									() -> assertThat(paymentOrder.productId()).isIn(1L, 2L, 3L),
									() -> assertThat(paymentOrder.orderId()).isEqualTo(response.orderId()));
						});
	}

	@Test
	@DisplayName("이미 저장된 결제 정보는 저장 요청 시, 예외가 발생한다.")
	void shouldThrowExceptionWhenAlreadyCompleteRequest() {
		// given
		PaymentPrepareRequest request =
				new PaymentPrepareRequest("idempotency-seed", List.of(1L, 2L, 3L)).addBuyerId(1L);
		paymentPreparationService.prepare(request);

		// then
		// when
		assertThatThrownBy(() -> paymentPreparationService.prepare(request))
				.isInstanceOf(IllegalArgumentException.class);
	}
}
