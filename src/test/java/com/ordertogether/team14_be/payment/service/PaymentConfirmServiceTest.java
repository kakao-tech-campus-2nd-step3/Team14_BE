package com.ordertogether.team14_be.payment.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ordertogether.team14_be.helper.PaymentDatabaseHelper;
import com.ordertogether.team14_be.member.persistence.MemberRepository;
import com.ordertogether.team14_be.payment.domain.PaymentStatus;
import com.ordertogether.team14_be.payment.persistence.repository.PaymentEventRepository;
import com.ordertogether.team14_be.payment.persistence.repository.PaymentOrderRepository;
import com.ordertogether.team14_be.payment.persistence.repository.ProductRepository;
import com.ordertogether.team14_be.payment.web.request.PaymentConfirmRequest;
import com.ordertogether.team14_be.payment.web.response.PaymentConfirmationResponse;
import com.ordertogether.team14_be.payment.web.toss.client.TossPaymentsClient;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles(profiles = "test")
class PaymentConfirmServiceTest {

	@MockBean PaymentConfirmService paymentConfirmService;

	@Autowired PaymentDatabaseHelper paymentDatabaseHelper;

	@Autowired PaymentValidationService paymentValidationService;
	@Autowired PaymentStatusUpdateService paymentStatusUpdateService;
	@Autowired PointManagementService pointManagementService;

	@Autowired PaymentOrderRepository paymentOrderRepository;
	@Autowired PaymentEventRepository paymentEventRepository;
	@Autowired ProductRepository productRepository;
	@Autowired MemberRepository memberRepository;

	@Mock TossPaymentsClient tossPaymentsClient;

	@BeforeEach
	void setUp() {
		paymentConfirmService =
				new PaymentConfirmService(
						tossPaymentsClient,
						paymentValidationService,
						paymentStatusUpdateService,
						pointManagementService);

		paymentDatabaseHelper.clean();

		paymentDatabaseHelper.setOrderId("test-order-id");
		paymentDatabaseHelper.saveTestData();
	}

	@Test
	@DisplayName("결제 승인 성공 시, 결제 상태를 성공으로 저장하고 포인트를 증가시킨다")
	void shouldSaveSuccessStatusWhenNormallyRequest() {
		// given
		int beforePoint =
				memberRepository
						.findById(1L)
						.orElseThrow(() -> new NoSuchElementException("Member not found"))
						.getPoint();

		long chargeAmount = 60000L;
		PaymentConfirmRequest request =
				PaymentConfirmRequest.builder()
						.orderId("test-order-id")
						.paymentKey(UUID.randomUUID().toString())
						.amount(chargeAmount)
						.build();

		// when
		when(tossPaymentsClient.confirmPayment(any(PaymentConfirmRequest.class)))
				.thenReturn(new PaymentConfirmationResponse(PaymentStatus.SUCCESS, null));

		PaymentConfirmationResponse response = paymentConfirmService.confirm(request);

		// then
		int afterPoint =
				memberRepository
						.findById(1L)
						.orElseThrow(() -> new NoSuchElementException("Member not found"))
						.getPoint();

		assertAll(
				() -> assertThat(afterPoint).isEqualTo(beforePoint + chargeAmount),
				() -> assertThat(response.paymentStatus()).isEqualTo(PaymentStatus.SUCCESS),
				() -> assertThat(response.failure()).isNull());
	}
}
