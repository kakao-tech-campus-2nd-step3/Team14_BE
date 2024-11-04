package com.ordertogether.team14_be.payment.web.controller;

import com.ordertogether.team14_be.common.web.response.ApiResponse;
import com.ordertogether.team14_be.payment.web.request.UsePointRequest;
import com.ordertogether.team14_be.payment.web.response.PointResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/points")
public class PointController {

	@PutMapping
	public ResponseEntity<ApiResponse<PointResponse>> usePoint(@RequestBody UsePointRequest request) {
		return ResponseEntity.ok(
				ApiResponse.with(HttpStatus.OK, "포인트 사용이 완료되었습니다.", createUsePointResponse()));
	}

	private PointResponse createUsePointResponse() {
		return PointResponse.builder().remainingPoint(100000).build();
	}
}
