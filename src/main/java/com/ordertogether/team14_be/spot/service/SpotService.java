package com.ordertogether.team14_be.spot.service;

import com.ordertogether.team14_be.spot.dto.SpotDto;
import com.ordertogether.team14_be.spot.entity.Spot;
import com.ordertogether.team14_be.spot.repository.SpotRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpotService {

	private final SpotRepository spotRepository;

	@Autowired
	public SpotService(SpotRepository spotRepository) {
		this.spotRepository = spotRepository;
	}

	// Spot 전체 조회하기
	public List<SpotDto> getSpot(BigDecimal lat, BigDecimal lng) {
		return spotRepository.findByLatAndLngAndIsDeletedFalse(lat, lng).stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	@Transactional
	public SpotDto createSpot(SpotDto spotDto) {
		Spot spot = spotDto.toEntity();
		return toDto(spotRepository.save(spot));
	}

	// Spot 상세 조회하기
	public SpotDto getSpot(Long id) {
		Spot spot =
				spotRepository
						.findById(id)
						.orElseThrow(() -> new EntityNotFoundException("Spot을 찾을 수 없습니다."));
		return toDto(spot);
	}

	@Transactional
	public SpotDto updateSpot(SpotDto spotDto) {
		Spot spot = spotRepository.save(spotDto.toEntity());
		return toDto(spot);
	}

	@Transactional
	public void deleteSpot(Long id) {
		Optional<Spot> spotToDelete = spotRepository.findByIdAndIsDeletedFalse(id);
		spotToDelete.ifPresent(
				spot -> {
					spot.setDeleted(true);
					spotRepository.save(spot);
				});
	}

	// Service Layer에서 toDto만들어서 매핑시키기
	public SpotDto toDto(Spot spotInStream) {
		Spot spot =
				spotRepository
						.findById(spotInStream.getId())
						.orElseThrow(() -> new EntityNotFoundException("Spot을 찾을 수 없습니다."));

		return SpotDto.builder()
				.id(spot.getId())
				.lat(spot.getLat())
				.lng(spot.getLng())
				.category(spot.getCategory())
				.store_name(spot.getStore_name())
				.minimum_order_amount(spot.getMinimum_order_amount())
				.together_order_link(spot.getTogether_order_link())
				.pick_up_location(spot.getPick_up_location())
				.delivery_status(spot.getDelivery_status())
				.isDeleted(spot.getIsDeleted())
				.build();
	}
}
