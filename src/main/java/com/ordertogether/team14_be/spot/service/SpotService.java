package com.ordertogether.team14_be.spot.service;

import ch.hsr.geohash.GeoHash;
import com.ordertogether.team14_be.spot.dto.controllerdto.SpotCreationResponse;
import com.ordertogether.team14_be.spot.dto.controllerdto.SpotDetailResponse;
import com.ordertogether.team14_be.spot.dto.controllerdto.SpotViewedResponse;
import com.ordertogether.team14_be.spot.dto.servicedto.SpotDto;
import com.ordertogether.team14_be.spot.entity.Spot;
import com.ordertogether.team14_be.spot.exception.NotSpotMasterException;
import com.ordertogether.team14_be.spot.mapper.SpotMapper;
import com.ordertogether.team14_be.spot.repository.SpotRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
		GeoHash geoHash =
				GeoHash.withCharacterPrecision(
						spotDto.getLat().doubleValue(), spotDto.getLng().doubleValue(), 12);
		spotDto.setGeoHash(geoHash.toBase32());
		spotDto.setCreatedAt(LocalDateTime.now());
		spotDto.setCreatedBy(spotDto.getId());
		spotDto.setModifiedAt(LocalDateTime.now());
		spotDto.setModifiedBy(spotDto.getModifiedBy());
		Spot spot = SpotMapper.INSTANCE.toEntity(spotDto, new Spot());
		return SpotMapper.INSTANCE.toSpotCreationResponse(spotRepository.save(spot));
	}

	// Spot 상세 조회하기
	@Transactional(readOnly = true)
	public SpotDetailResponse getSpot(Long id) {
		SpotDto spotDto = spotRepository.findByIdAndIsDeletedFalse(id);
		return SpotMapper.INSTANCE.toSpotDetailResponse(spotDto);
	}

	@Transactional(readOnly = true)
	public List<SpotViewedResponse> getSpotByGeoHash(BigDecimal lat, BigDecimal lng) {
		int precision = 12;
		GeoHash geoHash =
				GeoHash.withCharacterPrecision(lat.doubleValue(), lng.doubleValue(), precision);

		String hashString = geoHash.toBase32();

		List<SpotDto> resultAroundSpot = spotRepository.findBygeoHash(hashString);
		return resultAroundSpot.stream().map(SpotMapper.INSTANCE::toSpotViewedResponse).toList();
	}

	@Transactional
	public SpotDto updateSpot(SpotDto spotDto) {
		if (!Objects.equals(spotDto.getId(), spotDto.getCreatedBy())) {
			throw new NotSpotMasterException("작성자만 수정할 수 있습니다.");
		}
		spotDto.setModifiedAt(LocalDateTime.now());
		Spot spot =
				SpotMapper.INSTANCE.toEntity(
						spotDto,
						SpotMapper.INSTANCE.toEntity(
								spotRepository.findByIdAndIsDeletedFalse(spotDto.getId())));
		return SpotMapper.INSTANCE.toDto(spot);
	}

	@Transactional
	public void deleteSpot(Long id) {
		// id가 createdBy와 일치하는지 검증 후 delete
		SpotDto spotDto = spotRepository.findByIdAndIsDeletedFalse(id);
		if (!Objects.equals(spotDto.getCreatedBy(), id)) {
			throw new IllegalArgumentException("방장이 아닌 사람은 삭제할 수 없습니다.");
		}
		spotRepository.delete(id);
	}
}
