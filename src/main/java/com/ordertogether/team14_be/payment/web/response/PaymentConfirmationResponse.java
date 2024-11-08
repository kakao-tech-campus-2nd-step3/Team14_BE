package com.ordertogether.team14_be.payment.web.response;

import com.ordertogether.team14_be.payment.domain.PaymentStatus;
import jakarta.annotation.Nullable;
import java.util.Objects;

public record PaymentConfirmationResponse(
		PaymentStatus paymentStatus, @Nullable PaymentConfirmationFailure failure) {

	public PaymentConfirmationResponse(
			PaymentStatus paymentStatus, PaymentConfirmationFailure failure) {
		if (paymentStatus.isFail()) {
			Objects.requireNonNull(
					failure, "결제 상태가 FAIL 인 경우, PaymentConfirmationFailure 가 null 일 수 없습니다.");
		}

		this.paymentStatus = paymentStatus;
		this.failure = failure;
	}
}
