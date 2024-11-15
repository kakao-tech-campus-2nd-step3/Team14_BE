package com.ordertogether.team14_be.spot.repository;

import com.ordertogether.team14_be.spot.dto.servicedto.SpotDto;
import com.ordertogether.team14_be.spot.entity.Spot;
import com.ordertogether.team14_be.spot.exception.SpotNotFoundException;
import com.ordertogether.team14_be.spot.mapper.SpotMapper;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SpotRepository {

	private final SimpleSpotRepository simpleSpotRepository;

	public SpotDto save(Spot spot) {
		log.info("Spot 생성 요청: {}", spot.toString());
		return SpotMapper.INSTANCE.toDto(simpleSpotRepository.saveAndFlush(spot));
	}

	public List<SpotDto> findByMemberIdAndIsDeletedFalse(Long memberId) {
		return simpleSpotRepository.findByMemberIdAndIsDeletedFalse(memberId).stream()
				.map(SpotMapper.INSTANCE::toDto)
				.toList();
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

	public SpotDto update(SpotDto updateSpotDto) {
		log.info("Spot 수정 요청: {}", updateSpotDto.toString());

		// 기존 Spot 객체 조회
		Spot spot =
				simpleSpotRepository
						.findById(updateSpotDto.getId())
						.orElseThrow(
								() -> new SpotNotFoundException(updateSpotDto.getId() + "에 해당하는 Spot을 찾을 수 없습니다."));

		SpotDto updatedSpot = SpotMapper.INSTANCE.toDto(spot);
		updatedSpot.setStoreName(updateSpotDto.getStoreName());
		updatedSpot.setCategory(updateSpotDto.getCategory());
		updatedSpot.setMinimumOrderAmount(updateSpotDto.getMinimumOrderAmount());
		updatedSpot.setTogetherOrderLink(updateSpotDto.getTogetherOrderLink());
		updatedSpot.setPickUpLocation(updateSpotDto.getPickUpLocation());
		log.info("수정된 Spot: {}", updatedSpot.toString());
		// 업데이트된 Spot 객체를 저장
		return SpotMapper.INSTANCE.toDto(
				simpleSpotRepository.saveAndFlush(SpotMapper.INSTANCE.toEntity(updatedSpot)));
	}

	public void delete(Long id) {
		Spot spot =
				simpleSpotRepository
						.findByIdAndIsDeletedFalse(id)
						.orElseThrow(() -> new SpotNotFoundException(id + "에 해당하는 Spot을 찾을 수 없습니다."));
		spot.delete();
	}

	public List<SpotDto> findBygeoHash(String geoHash) {
		return simpleSpotRepository.findByGeoHash(geoHash).stream()
				.map(SpotMapper.INSTANCE::toDto)
				.toList();
	}
}
