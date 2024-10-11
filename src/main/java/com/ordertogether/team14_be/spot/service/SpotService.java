package com.ordertogether.team14_be.spot.service;

import static java.lang.Math.abs;

import com.ordertogether.team14_be.spot.dto.controllerdto.SpotCreationResponse;
import com.ordertogether.team14_be.spot.dto.controllerdto.SpotDetailResponse;
import com.ordertogether.team14_be.spot.dto.controllerdto.SpotViewedResponse;
import com.ordertogether.team14_be.spot.dto.servicedto.SpotDto;
import com.ordertogether.team14_be.spot.entity.Spot;
import com.ordertogether.team14_be.spot.mapper.SpotMapper;
import com.ordertogether.team14_be.spot.repository.SpotRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpotService {
	public static final int EARTH_RADIUS = 6371000; // 6371km
	private final SpotRepository spotRepository;

	// Spot 전체 조회하기
	@Transactional(readOnly = true)
	public List<SpotViewedResponse> getSpot(BigDecimal lat, BigDecimal lng) {
		return spotRepository.findByLatAndLngAndIsDeletedFalse(lat, lng).stream()
				.map(SpotMapper.INSTANCE::toSpotViewedResponse)
				.toList();
	}

	@Transactional
	public SpotCreationResponse createSpot(SpotDto spotDto) {
		Spot spot = SpotMapper.INSTANCE.toEntity(spotDto, new Spot());
		return SpotMapper.INSTANCE.toSpotCreationResponse(spotRepository.save(spot));
	}

	// Spot 상세 조회하기
	@Transactional(readOnly = true)
	public SpotDetailResponse getSpot(Long id) {
		SpotDto spotDto = spotRepository.findByIdAndIsDeletedFalse(id);
		return SpotMapper.INSTANCE.toSpotDetailResponse(spotDto);
	}

	// 반경 n미터 내 Spot 조회하기
	@Transactional(readOnly = true)
	public List<SpotViewedResponse> getSpotByRadius(BigDecimal lat, BigDecimal lng, int radius) {
		// m당 y 좌표 이동 값
		double mForLatitude = (1 / (EARTH_RADIUS * 1 * (Math.PI / 180))) / 1000;
		// m당 x 좌표 이동 값
		double mForLongitude =
				(1 / (EARTH_RADIUS * 1 * (Math.PI / 180) * Math.cos(Math.toRadians(lat.doubleValue()))))
						/ 1000;

		// 현재 위치 기준 검색 거리 좌표
		double maxY = lat.doubleValue() + (radius * mForLatitude);
		double minY = lat.doubleValue() - (radius * mForLatitude);
		double maxX = lng.doubleValue() + (radius * mForLongitude);
		double minX = lng.doubleValue() - (radius * mForLongitude);

		// 원의 지름에 해당하는 정사각형 내에 있는 Spot들을 모두 가져옴
		List<SpotDto> resultAroundSpot =
				spotRepository.findAroundSpotAndIsDeletedFalse(
						BigDecimal.valueOf(maxX),
						BigDecimal.valueOf(maxY),
						BigDecimal.valueOf(minX),
						BigDecimal.valueOf(minY));

		// 자기 위치에서부터 반경 내에 있는 Spot만 반환
		return resultAroundSpot.stream()
				.filter(
						spotDto -> {
							double distance =
									Math.sqrt(
											Math.pow(abs(spotDto.getLat().doubleValue() - lat.doubleValue()), 2)
													+ Math.pow(abs(spotDto.getLng().doubleValue() - lng.doubleValue()), 2));
							return distance <= radius;
						})
				.map(SpotMapper.INSTANCE::toSpotViewedResponse)
				.toList();
	}

	@Transactional
	public SpotDto updateSpot(SpotDto spotDto) {
		Spot spot =
				SpotMapper.INSTANCE.toEntity(
						spotDto,
						SpotMapper.INSTANCE.toEntity(
								spotRepository.findByIdAndIsDeletedFalse(spotDto.getId())));
		return SpotMapper.INSTANCE.toDto(spot);
	}

	@Transactional
	public void deleteSpot(Long id) {
		spotRepository.delete(id);
	}
}
