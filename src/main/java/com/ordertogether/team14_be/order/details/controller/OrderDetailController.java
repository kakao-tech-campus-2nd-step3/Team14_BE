package com.ordertogether.team14_be.order.details.controller;

import com.ordertogether.team14_be.order.details.dto.create.CreateOrderDetailRequestDto;
import com.ordertogether.team14_be.order.details.dto.create.CreateOrderDetailResponseDto;
import com.ordertogether.team14_be.order.details.service.OrderDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderDetailController {

	private final OrderDetailService orderDetailService;

	public OrderDetailController(OrderDetailService orderDetailService) {
		this.orderDetailService = orderDetailService;
	}

	// 주문 생성
	@PostMapping
	public ResponseEntity<CreateOrderDetailResponseDto> createOrderDetail(
			@RequestBody CreateOrderDetailRequestDto createOrderDetailRequestDto) {
		CreateOrderDetailResponseDto createOrderDetailResponseDto =
				orderDetailService.createOrderDetail(createOrderDetailRequestDto);
		return ResponseEntity.ok(createOrderDetailResponseDto);
	}
}
