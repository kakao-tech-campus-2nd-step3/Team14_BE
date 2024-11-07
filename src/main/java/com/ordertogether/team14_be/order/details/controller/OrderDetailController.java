package com.ordertogether.team14_be.order.details.controller;

import com.ordertogether.team14_be.member.persistence.entity.Member;
import com.ordertogether.team14_be.member.presentation.LoginMember;
import com.ordertogether.team14_be.order.details.dto.create.CreateOrderDetailReq;
import com.ordertogether.team14_be.order.details.dto.create.CreateOrderDetailRes;
import com.ordertogether.team14_be.order.details.dto.get.GetCreatorOrderInfoRes;
import com.ordertogether.team14_be.order.details.dto.get.GetOrdersInfoReq;
import com.ordertogether.team14_be.order.details.dto.get.GetOrdersInfoRes;
import com.ordertogether.team14_be.order.details.dto.get.GetParticipantOrderInfoRes;
import com.ordertogether.team14_be.order.details.service.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderDetailController {

	private final OrderDetailService orderDetailService;

	// 주문 생성
	@PostMapping
	public ResponseEntity<CreateOrderDetailRes> createOrderDetail(
			@RequestBody CreateOrderDetailReq createOrderDetailReq) {
		CreateOrderDetailRes createOrderDetailRes =
				orderDetailService.createOrderDetail(createOrderDetailReq);
		return ResponseEntity.ok(createOrderDetailRes);
	}

	@GetMapping
	public ResponseEntity<GetOrdersInfoRes> getOrdersInfo(
			@LoginMember Member member, @ModelAttribute @Valid GetOrdersInfoReq dto) {
		return ResponseEntity.ok(orderDetailService.getOrdersInfo(member, dto));
	}

	@GetMapping("/participant")
	public ResponseEntity<GetParticipantOrderInfoRes> getParticipantOrderInfo(
			@LoginMember Member member, @RequestParam(name = "spot-id") Long spotId) {
		return ResponseEntity.ok(orderDetailService.getParticipantOrderInfo(member, spotId));
	}

	@GetMapping("/creator")
	public ResponseEntity<GetCreatorOrderInfoRes> getCreatorOrderInfo(
			@LoginMember Member member, @RequestParam(name = "spot-id") Long spotId) {
		return ResponseEntity.ok(orderDetailService.getCreatorOrderInfo(member, spotId));
	}
}
