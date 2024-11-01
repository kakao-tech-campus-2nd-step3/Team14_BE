package com.ordertogether.team14_be.payment.service;

import com.ordertogether.team14_be.payment.service.command.PaymentStatusUpdateCommand;
import com.ordertogether.team14_be.payment.web.request.PaymentConfirmRequest;
import com.ordertogether.team14_be.payment.web.response.PaymentConfirmationResponse;
import com.ordertogether.team14_be.payment.web.toss.client.TossPaymentsClient;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
/** 결제 승인 서비스 */
public class PaymentConfirmService {

	private final TossPaymentsClient tossPaymentsClient;

	private final PaymentValidationService paymentValidationService;
	private final PaymentStatusUpdateService paymentStatusUpdateService;
	private final PointManagementService pointManagementService;

	public PaymentConfirmationResponse confirm(PaymentConfirmRequest request) {
		// 1. 결제 상태 변경 (준비 -> 실행 중)
		paymentStatusUpdateService.updatePaymentStatusToExecuting(
				request.orderId(), request.paymentKey());
		// 2. 결제 유효성 검사
		paymentValidationService.validate(request.orderId(), BigDecimal.valueOf(request.amount()));
		// 3. 결제 승인 요청
		PaymentConfirmationResponse response = tossPaymentsClient.confirmPayment(request);
		// 4. 승인 결과에 따른 결제 상태 업데이트
		paymentStatusUpdateService.updatePaymentStatus(
				new PaymentStatusUpdateCommand(
						request.paymentKey(), request.orderId(), response.paymentStatus()));
		// 5. 포인트 충전
		pointManagementService.increasePoint(request.orderId());
		return response;
	}
}
