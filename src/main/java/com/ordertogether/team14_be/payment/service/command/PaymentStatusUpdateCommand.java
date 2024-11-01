package com.ordertogether.team14_be.payment.service.command;

import com.ordertogether.team14_be.payment.domain.PaymentStatus;
import java.util.Objects;

public record PaymentStatusUpdateCommand(
		String paymentKey, String orderId, PaymentStatus paymentStatus) {

	public PaymentStatusUpdateCommand(
			String paymentKey, String orderId, PaymentStatus paymentStatus) {
		validateObjectsNonnull(paymentKey, orderId, paymentStatus);
		this.paymentKey = paymentKey;
		this.orderId = orderId;
		this.paymentStatus = paymentStatus;
	}

	private void validateObjectsNonnull(
			String paymentKey, String orderId, PaymentStatus paymentStatus) {
		Objects.requireNonNull(paymentKey, "paymentKey 가 null 인 결제의 상태 변경은 불가능합니다.");
		Objects.requireNonNull(orderId, "orderId 가 null 인 결제의 상태 변경은 불가능합니다.");
		Objects.requireNonNull(paymentStatus, "paymentStatus 가 null 인 결제 상태 변경은 불가능합니다.");
	}
}
