package com.ordertogether.team14_be.payment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
@ActiveProfiles(profiles = "test")
class PointUpdateServiceTest {

	@Autowired PointUpdateService pointUpdateService;

	@Autowired MemberRepository memberRepository;
	@Autowired PaymentEventRepository paymentEventRepository;

	@Autowired PaymentDatabaseHelper paymentDatabaseHelper;

	@BeforeEach
	void setUp() {
		paymentDatabaseHelper.clean();

		paymentDatabaseHelper.setOrderId("test-order-id");
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
						.findByOrderId("test-order-id")
						.orElseThrow(() -> new NoSuchElementException("PaymentEvent not found"))
						.totalAmount();

		// when
		pointUpdateService.increasePoint("test-order-id");

		// then
		int afterPoint =
				memberRepository
						.findById(1L)
						.orElseThrow(() -> new NoSuchElementException("Member not found"))
						.getPoint();
		assertThat(afterPoint).isEqualTo(beforePoint + chargeAmount);
	}
}
