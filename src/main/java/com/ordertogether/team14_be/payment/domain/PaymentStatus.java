package com.ordertogether.team14_be.payment.domain;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** 결제 상태 */
@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
	READY("결제 준비"),
	EXECUTING("결제 진행 중"),
	SUCCESS("결제 성공"),
	FAIL("결제 실패");

	private final String description;

	public boolean isSuccess() {
		return this == SUCCESS;
	}

	public boolean isFail() {
		return this == FAIL;
	}

	public static PaymentStatus fromString(String statusName) {
		return Arrays.stream(PaymentStatus.values())
				.filter(paymentStatus -> paymentStatus.name().equalsIgnoreCase(statusName))
				.findFirst()
				.orElseThrow(
						() -> new IllegalArgumentException("%s 는 올바른 결제 상태가 아닙니다.".formatted(statusName)));
	}
}
