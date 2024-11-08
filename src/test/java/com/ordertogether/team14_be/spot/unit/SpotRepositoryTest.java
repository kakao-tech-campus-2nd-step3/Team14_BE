package com.ordertogether.team14_be.spot.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.ordertogether.team14_be.spot.dto.servicedto.SpotDto;
import com.ordertogether.team14_be.spot.entity.Spot;
import com.ordertogether.team14_be.spot.enums.Category;
import com.ordertogether.team14_be.spot.exception.SpotNotFoundException;
import com.ordertogether.team14_be.spot.mapper.SpotMapper;
import com.ordertogether.team14_be.spot.repository.SimpleSpotRepository;
import com.ordertogether.team14_be.spot.repository.SpotRepository;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SpotRepositoryTest {

	@InjectMocks private SpotRepository spotRepository;

	@Mock private SimpleSpotRepository simpleSpotRepository;

	@Spy private SpotMapper spotMapper;

	private BigDecimal lat;
	private BigDecimal lng;
	private Spot spot;

	@BeforeEach
	void setUp() {
		lat = new BigDecimal("37.7749");
		lng = new BigDecimal("-122.4194");

		// SpotDto 초기화
		spot =
				Spot.builder()
						.id(1L)
						.lat(lat)
						.lng(lng)
						.id(1L)
						.category(Category.BURGER)
						.storeName("맥도날드")
						.minimumOrderAmount(12000)
						.deadlineTime(LocalTime.of(12, 0, 0))
						.pickUpLocation("픽업위치")
						.createdBy(1L)
						.build();
	}

	@Test
	void save_success() {
		when(simpleSpotRepository.save(spot)).thenReturn(spot);

		SpotDto savedSpot = spotRepository.save(spot);
		assertThat(savedSpot.getId()).isEqualTo(spot.getId());
		assertThat(savedSpot.getLat()).isEqualTo(spot.getLat());
		assertThat(savedSpot.getLng()).isEqualTo(spot.getLng());
		assertThat(savedSpot.getCategory()).isEqualTo(spot.getCategory());
		assertThat(savedSpot.getStoreName()).isEqualTo(spot.getStoreName());
		assertThat(savedSpot.getMinimumOrderAmount()).isEqualTo(spot.getMinimumOrderAmount());
		assertThat(savedSpot.getDeadlineTime()).isEqualTo(spot.getDeadlineTime());
		assertThat(savedSpot.getPickUpLocation()).isEqualTo(spot.getPickUpLocation());
		assertThat(savedSpot.getCreatedBy()).isEqualTo(spot.getCreatedBy());
	}

	@Test
	void findByIdAndIsDeletedFalse_success() {
		when(simpleSpotRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.of(spot));

		SpotDto spotDto = spotRepository.findByIdAndIsDeletedFalse(1L);
		assertThat(spotDto.getId()).isEqualTo(1L);
		assertThat(spotDto.getLat()).isEqualTo(lat);
		assertThat(spotDto.getLng()).isEqualTo(lng);
		assertThat(spotDto.getCategory()).isEqualTo(Category.BURGER);
		assertThat(spotDto.getStoreName()).isEqualTo("맥도날드");
		assertThat(spotDto.getMinimumOrderAmount()).isEqualTo(12000);
		assertThat(spotDto.getDeadlineTime()).isEqualTo(LocalTime.of(12, 0, 0));
		assertThat(spotDto.getPickUpLocation()).isEqualTo("픽업위치");
		assertThat(spotDto.getCreatedBy()).isEqualTo(1L);
	}

	@Test
	void findByIdAndIsDeletedFalse_exception() {
		when(simpleSpotRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.empty());

		assertThrows(SpotNotFoundException.class, () -> spotRepository.findByIdAndIsDeletedFalse(1L));
	}

	@Test
	void findByLatAndLngAndIsDeletedFalse_success() {
		when(simpleSpotRepository.findByLatAndLngAndIsDeletedFalse(lat, lng)).thenReturn(List.of(spot));

		List<SpotDto> spotDto = spotRepository.findByLatAndLngAndIsDeletedFalse(lat, lng);
		assertThat(spotDto.getFirst().getId()).isEqualTo(1L);
		assertThat(spotDto.getFirst().getLat()).isEqualTo(lat);
		assertThat(spotDto.getFirst().getLng()).isEqualTo(lng);
		assertThat(spotDto.getFirst().getCategory()).isEqualTo(Category.BURGER);
		assertThat(spotDto.getFirst().getStoreName()).isEqualTo("맥도날드");
		assertThat(spotDto.getFirst().getMinimumOrderAmount()).isEqualTo(12000);
		assertThat(spotDto.getFirst().getDeadlineTime()).isEqualTo(LocalTime.of(12, 0, 0));
		assertThat(spotDto.getFirst().getPickUpLocation()).isEqualTo("픽업위치");
		assertThat(spotDto.getFirst().getCreatedBy()).isEqualTo(1L);
	}

	@Test
	void findBygeoHash_success() {
		when(simpleSpotRepository.findByGeoHash("9q8yyz")).thenReturn(List.of(spot));

		List<SpotDto> spotDto = spotRepository.findBygeoHash("9q8yyz");
		assertThat(spotDto.getFirst().getId()).isEqualTo(1L);
		assertThat(spotDto.getFirst().getLat()).isEqualTo(lat);
		assertThat(spotDto.getFirst().getLng()).isEqualTo(lng);
		assertThat(spotDto.getFirst().getCategory()).isEqualTo(Category.BURGER);
		assertThat(spotDto.getFirst().getStoreName()).isEqualTo("맥도날드");
		assertThat(spotDto.getFirst().getMinimumOrderAmount()).isEqualTo(12000);
		assertThat(spotDto.getFirst().getDeadlineTime()).isEqualTo(LocalTime.of(12, 0, 0));
		assertThat(spotDto.getFirst().getPickUpLocation()).isEqualTo("픽업위치");
		assertThat(spotDto.getFirst().getCreatedBy()).isEqualTo(1L);
	}
}
