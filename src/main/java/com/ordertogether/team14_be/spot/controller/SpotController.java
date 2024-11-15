package com.ordertogether.team14_be.spot.controller;

import com.ordertogether.team14_be.member.persistence.entity.Member;
import com.ordertogether.team14_be.member.presentation.LoginMember;
import com.ordertogether.team14_be.spot.dto.controllerdto.*;
import com.ordertogether.team14_be.spot.mapper.SpotMapper;
import com.ordertogether.team14_be.spot.service.SpotService;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SpotController {

	private final SpotService spotService;

	// Spot 생성하기
	@PostMapping("/api/v1/spot")
	public ResponseEntity<SpotCreationResponse> createSpot(
			@LoginMember Member member, @RequestBody SpotCreationRequest spotCreationRequest) {
		log.info("Spot 생성 요청: {}", spotCreationRequest);
		return new ResponseEntity<>(
				spotService.createSpot(SpotMapper.INSTANCE.toSpotDto(spotCreationRequest), member.getId()),
				HttpStatus.CREATED);
	}

	// Spot 닫기
	@PutMapping("/api/v1/spot/close/{id}")
	public ResponseEntity<Void> closeSpot(@LoginMember Member member, @PathVariable Long id) {
		spotService.closeSpot(id);
		return ResponseEntity.noContent().build();
	}

	// Spot 상세 조회하기
	@GetMapping("/api/v1/spot/{id}")
	public ResponseEntity<SpotDetailResponse> getSpotDetail(
			@LoginMember Member member, @PathVariable Long id) {
		return ResponseEntity.ok(spotService.getSpotDetail(id));
	}

	// 반경 n미터 내 Spot 조회하기 - 전체 조회
	@GetMapping("/api/v1/spot/{lat}/{lng}") // 현재 위치의 좌표로 hash값이 같은 튜플을 조회
	public ResponseEntity<List<SpotViewedResponse>> getSpotByGeoHash(
			@PathVariable BigDecimal lat, @PathVariable BigDecimal lng) {
		return ResponseEntity.ok(spotService.getSpotByGeoHash(lat, lng));
	}

	// Spot 수정하기 - DetailResponse
	@PutMapping("/api/v1/spot")
	public ResponseEntity<SpotModifyResponse> updateSpot(
			@LoginMember Member member, @RequestBody SpotModifyRequest spotModifyRequest) {
		return ResponseEntity.ok(
				spotService.updateSpot(SpotMapper.INSTANCE.toSpotDto(spotModifyRequest), member.getId()));
	}

	// Spot 삭제하기
	@DeleteMapping("/api/v1/spot/{id}")
	public ResponseEntity<Void> deleteSpot(@LoginMember Member member, @PathVariable Long id) {
		spotService.deleteSpot(id); // id는 SpotId임
		return ResponseEntity.ok().build();
	}
}
