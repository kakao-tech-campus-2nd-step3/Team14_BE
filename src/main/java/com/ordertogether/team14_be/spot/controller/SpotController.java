package com.ordertogether.team14_be.spot.controller;

import com.ordertogether.team14_be.spot.dto.controllerdto.*;
import com.ordertogether.team14_be.spot.mapper.SpotMapper;
import com.ordertogether.team14_be.spot.service.SpotService;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SpotController {

	private final SpotService spotService;

	// Spot 전체 조회하기
	@GetMapping("/api/v1/spot/{lat}/{lng}")
	public ResponseEntity<List<SpotViewedResponse>> getSpot(
			@PathVariable BigDecimal lat, @PathVariable BigDecimal lng) {
		return ResponseEntity.ok(spotService.getSpot(lat, lng));
	}

	// Spot 생성하기
	@PostMapping("/api/v1/spot")
	public ResponseEntity<SpotCreationResponse> createSpot(
			@RequestBody SpotCreationRequest spotCreationRequest) {
		return new ResponseEntity<>(
				spotService.createSpot(SpotMapper.INSTANCE.toSpotDto(spotCreationRequest)),
				HttpStatus.CREATED);
	}

	// Spot 상세 조회하기
	@GetMapping("/api/v1/spot/{id}")
	public ResponseEntity<SpotDetailResponse> getSpotDetail(@PathVariable Long id) {
		return ResponseEntity.ok(spotService.getSpot(id));
	}

	// 반경 n미터 내 Spot 조회하기
	@GetMapping("/api/v1/spot/{lat}/{lng}/{radius}") // 현재 위치의 좌표와 반지름을 받아옴
	public ResponseEntity<List<SpotViewedResponse>> getSpotByRadius(
			@PathVariable BigDecimal lat, @PathVariable BigDecimal lng, @PathVariable int radius) {
		return ResponseEntity.ok(spotService.getSpotByRadius(lat, lng, radius));
	}

	// Spot 수정하기
	@PutMapping("/api/v1/spot")
	public ResponseEntity<SpotCreationResponse> updateSpot(
			@RequestBody SpotModifyRequest spotModifyRequest) {
		return ResponseEntity.ok(
				SpotMapper.INSTANCE.toSpotCreationResponse(
						spotService.updateSpot(SpotMapper.INSTANCE.toSpotDto(spotModifyRequest))));
	}

	// Spot 삭제하기
	@DeleteMapping("/api/v1/spot/{id}")
	public ResponseEntity<Void> deleteSpot(@PathVariable Long id) {
		spotService.deleteSpot(id);
		return ResponseEntity.ok().build();
	}
}
