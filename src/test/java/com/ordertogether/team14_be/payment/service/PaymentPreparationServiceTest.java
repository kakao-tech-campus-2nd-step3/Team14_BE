package com.ordertogether.team14_be.payment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.ordertogether.team14_be.helper.PaymentDatabaseHelper;
import com.ordertogether.team14_be.payment.domain.Product;
import com.ordertogether.team14_be.payment.persistence.repository.ProductRepository;
import com.ordertogether.team14_be.payment.web.request.PaymentPrepareRequest;
import com.ordertogether.team14_be.payment.web.response.PaymentPrepareResponse;
import java.math.BigDecimal;
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
	@Autowired private ProductRepository productRepository;

	@Autowired private PaymentDatabaseHelper paymentDatabaseHelper;

	@BeforeEach
	void setup() {
		paymentDatabaseHelper.clean();

		productRepository.saveAll(
				List.of(
						Product.builder().id(1L).name("Product 1").price(BigDecimal.valueOf(10000)).build(),
						Product.builder().id(2L).name("Product 2").price(BigDecimal.valueOf(20000)).build(),
						Product.builder().id(3L).name("Product 3").price(BigDecimal.valueOf(30000)).build()));
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
		assertThat(response.getPaymentEventId()).isNotNull();
		assertThat(response.getBuyerId()).isEqualTo(1L);
		assertThat(response.getPaymentOrders()).hasSize(3);
		assertThat(response.getOrderId()).isNotNull();
		assertThat(response.getOrderName()).isEqualTo("Product 1,Product 2,Product 3");
		assertThat(response.getPaymentKey()).isNotNull();
		response.getPaymentOrders().stream()
				.forEach(
						paymentOrder -> {
							assertAll(
									() -> assertThat(paymentOrder.getPaymentOrderId()).isNotNull(),
									() -> assertThat(paymentOrder.getProductId()).isIn(1L, 2L, 3L),
									() -> assertThat(paymentOrder.getOrderId()).isEqualTo(response.getOrderId()));
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
