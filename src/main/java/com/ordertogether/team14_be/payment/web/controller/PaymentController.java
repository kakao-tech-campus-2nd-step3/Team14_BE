package com.ordertogether.team14_be.payment.web.controller;

import com.ordertogether.team14_be.common.web.response.ApiResponse;
import com.ordertogether.team14_be.payment.service.PaymentPreparationService;
import com.ordertogether.team14_be.payment.web.request.PaymentPrepareRequest;
import com.ordertogether.team14_be.payment.web.response.PaymentPrepareResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentPreparationService paymentPreparationService;

	@PostMapping
	public ResponseEntity<ApiResponse<PaymentPrepareResponse>> preparePayment(
			@RequestBody PaymentPrepareRequest request) {
		// todo: 1L -> UserDetail.getUserId()
		request.addBuyerId(1L);
		PaymentPrepareResponse data = paymentPreparationService.prepare(request);

		return ResponseEntity.ok(ApiResponse.with(HttpStatus.OK, "결제 정보를 저장하였습니다.", data));
	}
}
