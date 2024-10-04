package com.ordertogether.team14_be.spot.service;

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
