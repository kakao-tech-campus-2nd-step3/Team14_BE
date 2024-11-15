package com.ordertogether.team14_be.spot.service;

import ch.hsr.geohash.GeoHash;
import com.ordertogether.team14_be.member.application.service.MemberService;
import com.ordertogether.team14_be.order.details.dto.create.CreateOrderDetailReq;
import com.ordertogether.team14_be.order.details.service.OrderDetailService;
import com.ordertogether.team14_be.spot.dto.controllerdto.SpotCreationResponse;
import com.ordertogether.team14_be.spot.dto.controllerdto.SpotDetailResponse;
import com.ordertogether.team14_be.spot.dto.controllerdto.SpotModifyResponse;
import com.ordertogether.team14_be.spot.dto.controllerdto.SpotViewedResponse;
import com.ordertogether.team14_be.spot.dto.servicedto.SpotDto;
import com.ordertogether.team14_be.spot.mapper.SpotMapper;
import com.ordertogether.team14_be.spot.repository.SpotRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpotService {

	private final SpotRepository spotRepository;
	private final MemberService memberService;
	private final OrderDetailService orderDetailService;

	// Spot 상세 조회하기
	@Transactional(readOnly = true)
	public SpotDetailResponse getSpotDetail(Long id) {
		return SpotMapper.INSTANCE.toSpotDetailResponse(spotRepository.findByIdAndIsDeletedFalse(id));
	}

	@Transactional
	public SpotCreationResponse createSpot(SpotDto spotDto, Long memberId) {
		spotDto.setMemberId(memberId);
		GeoHash geoHash =
				GeoHash.withCharacterPrecision(
						spotDto.getLat().doubleValue(), spotDto.getLng().doubleValue(), 5);
		spotDto.setGeoHash(geoHash.toBase32());
		spotDto.setCreatedAt(LocalDateTime.now());
		spotDto.setCreatedBy(spotDto.getMemberId());
		spotDto.setModifiedAt(LocalDateTime.now());
		spotDto.setModifiedBy(spotDto.getMemberId());
		log.info("SpotDto 생성 요청: {}", spotDto.toString());
		SpotDto savedSpotDto =
				spotRepository.save(SpotMapper.INSTANCE.toEntity(spotDto, memberService));
		CreateOrderDetailReq createOrderDetailReq =
				CreateOrderDetailReq.builder()
						.price(-1) // 자신이 주문 금액을 입력하기 전이므로
						.isPayed(false)
						.participantId(memberId)
						.spotId(savedSpotDto.getId())
						.build();
		orderDetailService.createOrderDetail(createOrderDetailReq);
		return SpotMapper.INSTANCE.toSpotCreationResponse(savedSpotDto);
	}

	@Transactional(readOnly = true)
	public List<SpotViewedResponse> getSpotByGeoHash(BigDecimal lat, BigDecimal lng) {
		int precision = 5; // 2.4km 정도의 정확도
		GeoHash geoHash =
				GeoHash.withCharacterPrecision(lat.doubleValue(), lng.doubleValue(), precision);

		String hashString = geoHash.toBase32();
		String geoHashPrefix = hashString.substring(0, precision);
		List<SpotDto> resultAroundSpot = spotRepository.findBygeoHash(geoHashPrefix);
		log.info("주변 Spot 조회 요청: {}", resultAroundSpot);
		return resultAroundSpot.stream().map(SpotMapper.INSTANCE::toSpotViewedResponse).toList();
	}

	@Transactional
	public SpotModifyResponse updateSpot(SpotDto spotDto, Long memberId) {
		spotDto.setModifiedAt(LocalDateTime.now());
		spotDto.setMemberId(memberId);
		log.info("SpotDto 수정 요청: {}", spotDto);
		SpotDto modifiedSpotDto = spotRepository.update(spotDto);
		log.info("Spot 수정 요청: {}", modifiedSpotDto);
		return SpotMapper.INSTANCE.toSpotModifyResponse(modifiedSpotDto);
	}

	@Transactional
	public void deleteSpot(Long id) {
		spotRepository.delete(id);
	}

	@Transactional
	public void closeSpot(Long id) {
		SpotDto spotDto = spotRepository.findByIdAndIsDeletedFalse(id);
		spotRepository.update(spotDto);
	}
}
