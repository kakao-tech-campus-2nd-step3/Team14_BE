package com.ordertogether.team14_be.spot.repository;

import com.ordertogether.team14_be.spot.dto.servicedto.SpotDto;
import com.ordertogether.team14_be.spot.entity.Spot;
import com.ordertogether.team14_be.spot.exception.SpotNotFoundException;
import com.ordertogether.team14_be.spot.mapper.SpotMapper;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SpotRepository {

	private final SimpleSpotRepository simpleSpotRepository;

	public SpotDto save(Spot spot) {
		return SpotMapper.INSTANCE.toDto(simpleSpotRepository.save(spot));
	}

	public SpotDto findByIdAndIsDeletedFalse(Long id) {
		return SpotMapper.INSTANCE.toDto(
				simpleSpotRepository
						.findByIdAndIsDeletedFalse(id)
						.orElseThrow(() -> new SpotNotFoundException(id + "에 해당하는 Spot을 찾을 수 없습니다.")));
	}

	public List<SpotDto> findByLatAndLngAndIsDeletedFalse(BigDecimal lat, BigDecimal lng) {
		return simpleSpotRepository.findByLatAndLngAndIsDeletedFalse(lat, lng).stream()
				.map(SpotMapper.INSTANCE::toDto)
				.toList();
	}

	public void delete(Long id) {
		Spot spot =
				simpleSpotRepository
						.findByIdAndIsDeletedFalse(id)
						.orElseThrow(() -> new EntityNotFoundException(id + "에 해당하는 Spot을 찾을 수 없습니다."));
		spot.delete();
	}

	public List<SpotDto> findBygeoHash(String geoHash) {
		return simpleSpotRepository.findByGeoHash(geoHash).stream()
				.map(SpotMapper.INSTANCE::toDto)
				.toList();
	}
}
