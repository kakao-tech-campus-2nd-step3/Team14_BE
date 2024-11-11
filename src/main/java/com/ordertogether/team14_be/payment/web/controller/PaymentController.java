package com.ordertogether.team14_be.payment.web.controller;

import com.ordertogether.team14_be.common.web.response.ApiResponse;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import com.ordertogether.team14_be.member.presentation.LoginMember;
import com.ordertogether.team14_be.payment.domain.PaymentStatus;
import com.ordertogether.team14_be.payment.service.PaymentConfirmService;
import com.ordertogether.team14_be.payment.service.PaymentHistoryService;
import com.ordertogether.team14_be.payment.service.PaymentPreparationService;
import com.ordertogether.team14_be.payment.web.request.PaymentConfirmRequest;
import com.ordertogether.team14_be.payment.web.request.PaymentHistoryRequest;
import com.ordertogether.team14_be.payment.web.request.PaymentPrepareRequest;
import com.ordertogether.team14_be.payment.web.response.PaymentConfirmationResponse;
import com.ordertogether.team14_be.payment.web.response.PaymentHistoryResponse;
import com.ordertogether.team14_be.payment.web.response.PaymentPrepareResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentPreparationService paymentPreparationService;
	private final PaymentConfirmService paymentConfirmService;
	private final PaymentHistoryService paymentHistoryService;

	@PostMapping
	public ResponseEntity<ApiResponse<PaymentPrepareResponse>> preparePayment(
			@RequestBody PaymentPrepareRequest request, @LoginMember Member member) {
		request.addBuyerId(member.getId());
		PaymentPrepareResponse data = paymentPreparationService.prepare(request);

		return ResponseEntity.ok(ApiResponse.with(HttpStatus.OK, "결제 정보를 저장하였습니다.", data));
	}

	@PostMapping("/confirm")
	public ResponseEntity<ApiResponse<PaymentConfirmationResponse>> confirmPayment(
			@RequestBody PaymentConfirmRequest request) {
		PaymentConfirmationResponse data = paymentConfirmService.confirm(request);
		if (data.paymentStatus().isFail()) {
			return ResponseEntity.badRequest()
					.body(ApiResponse.with(HttpStatus.BAD_REQUEST, "결제에 실패하였습니다.", data));
		}
		return ResponseEntity.ok(ApiResponse.with(HttpStatus.OK, "결제가 완료되었습니다.", data));
	}

	@GetMapping("/history")
	public ResponseEntity<ApiResponse<PaymentHistoryResponse>> getHistory(
			@RequestBody PaymentHistoryRequest request, @LoginMember Member member) {
		return ResponseEntity.ok(
				ApiResponse.with(
						HttpStatus.OK,
						"포인트 사용 내역을 조회하였습니다.",
						paymentHistoryService.getChargeHistory(
								member.getId(), PaymentStatus.fromString(request.paymentStatus()))));
	}
}
