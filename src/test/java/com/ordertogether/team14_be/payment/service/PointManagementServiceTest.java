package com.ordertogether.team14_be.payment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ordertogether.team14_be.helper.PaymentDatabaseHelper;
import com.ordertogether.team14_be.member.persistence.MemberRepository;
import com.ordertogether.team14_be.payment.persistence.repository.PaymentEventRepository;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@DisplayName("포인트 관리 서비스는")
@ActiveProfiles(profiles = "test")
class PointManagementServiceTest {

	@Autowired PointManagementService pointManagementService;

	@Autowired MemberRepository memberRepository;
	@Autowired PaymentEventRepository paymentEventRepository;

	@Autowired PaymentDatabaseHelper paymentDatabaseHelper;

	@BeforeEach
	void setUp() {
		paymentDatabaseHelper.clean();
		paymentDatabaseHelper.saveTestData();
	}

	@Test
	@DisplayName("구매 금액만큼 포인트가 증가한다")
	void shouldIncreaseSuccessWhenNormallyRequest() {
		// given
		int beforePoint =
				memberRepository
						.findById(1L)
						.orElseThrow(() -> new NoSuchElementException("Member not found"))
						.getPoint();
		Long chargeAmount =
				paymentEventRepository
						.findByOrderId("test-order-id-1")
						.orElseThrow(() -> new NoSuchElementException("PaymentEvent not found"))
						.totalAmount();

		// when
		pointManagementService.increasePoint("test-order-id-1");

		// then
		int afterPoint =
				memberRepository
						.findById(1L)
						.orElseThrow(() -> new NoSuchElementException("Member not found"))
						.getPoint();
		assertThat(afterPoint).isEqualTo(beforePoint + chargeAmount);
	}

	@Test
	@DisplayName("요청 포인트가 보유 포인트 보다 작은 경우 차감에 성공한다.")
	void shouldDecreasePointWhenNormallyRequest() {
		// given
		int beforePoint =
				memberRepository
						.findById(1L)
						.orElseThrow(() -> new NoSuchElementException("Member not found"))
						.getPoint();
		int decreasePoint = 1000;

		// when
		pointManagementService.decreasePoint(1L, decreasePoint);

		// then
		int afterPoint =
				memberRepository
						.findById(1L)
						.orElseThrow(() -> new NoSuchElementException("Member not found"))
						.getPoint();
		assertThat(afterPoint).isEqualTo(beforePoint - decreasePoint);
	}

	@Test
	@DisplayName("요청 포인트가 보유 포인트 보다 큰 경우 예외를 발생시킨다.")
	void shouldThrowExceptionWhenNotEnoughRemainingPoint() {
		// given
		int remainingPoint =
				memberRepository
						.findById(1L)
						.orElseThrow(() -> new NoSuchElementException("Member not found"))
						.getPoint();
		int decreasePoint = remainingPoint + 1000;

		// when
		// then
		assertThatThrownBy(() -> pointManagementService.decreasePoint(1L, decreasePoint))
				.isInstanceOf(IllegalArgumentException.class);
	}
}
