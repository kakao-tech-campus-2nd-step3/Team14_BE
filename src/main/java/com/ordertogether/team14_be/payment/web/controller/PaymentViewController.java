package com.ordertogether.team14_be.payment.web.controller;

import com.ordertogether.team14_be.payment.service.PaymentPreparationService;
import com.ordertogether.team14_be.payment.web.request.PaymentPrepareRequest;
import com.ordertogether.team14_be.payment.web.response.PaymentPrepareResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PaymentViewController {

	private final PaymentPreparationService paymentPreparationService;

	@GetMapping("/success")
	public String successPage() {
		return "success";
	}

	@GetMapping("/fail")
	public String failPage() {
		return "fail";
	}

	@GetMapping("/")
	public String preparePayment(Model model) {
		PaymentPrepareResponse response =
				paymentPreparationService.prepare(
						new PaymentPrepareRequest(UUID.randomUUID().toString(), List.of(1L, 2L))
								.addBuyerId(1L));

		model.addAttribute("orderId", response.orderId());
		model.addAttribute("orderName", response.orderName());
		return "checkout";
	}
}
