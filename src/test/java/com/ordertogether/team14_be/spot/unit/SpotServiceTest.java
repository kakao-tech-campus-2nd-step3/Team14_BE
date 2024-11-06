package com.ordertogether.team14_be.spot.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ch.hsr.geohash.GeoHash;
import com.ordertogether.team14_be.spot.dto.controllerdto.SpotCreationResponse;
import com.ordertogether.team14_be.spot.dto.controllerdto.SpotViewedResponse;
import com.ordertogether.team14_be.spot.dto.servicedto.SpotDto;
import com.ordertogether.team14_be.spot.entity.Spot;
import com.ordertogether.team14_be.spot.enums.Category;
import com.ordertogether.team14_be.spot.exception.NotSpotMasterException;
import com.ordertogether.team14_be.spot.mapper.SpotMapper;
import com.ordertogether.team14_be.spot.repository.SpotRepository;
import com.ordertogether.team14_be.spot.service.SpotService;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SpotServiceTest {

	@Mock private SpotRepository spotRepository;

	@Spy private SpotMapper spotMapper;

	@InjectMocks private SpotService spotService;

	private BigDecimal lat;
	private BigDecimal lng;
	private SpotDto spotDto;

	@BeforeEach
	void setUp() {
		lat = new BigDecimal("37.7749");
		lng = new BigDecimal("-122.4194");

		// SpotDto 초기화
		spotDto =
				SpotDto.builder()
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
	void getSpot_success() {
		when(spotRepository.findByLatAndLngAndIsDeletedFalse(lat, lng)).thenReturn(List.of(spotDto));
		List<SpotViewedResponse> result = spotService.getSpot(lat, lng);

		assertNotNull(result);
		assertEquals("패스트푸드", result.getFirst().category());
		assertEquals("맥도날드", result.getFirst().storeName());
		assertEquals(12000, result.getFirst().minimumOrderAmount());
		assertEquals("픽업위치", result.getFirst().pickUpLocation());
		verify(spotRepository).findByLatAndLngAndIsDeletedFalse(lat, lng);
	}

	@Test
	void createSpot_success() {
		SpotCreationResponse expectedResponse =
				new SpotCreationResponse(1L, "패스트푸드", "맥도날드", 12000, "픽업위치", LocalTime.of(12, 0, 0));

		// Mocking the repository and mapper responses
		when(spotRepository.save(any(Spot.class))).thenReturn(spotDto);

		SpotCreationResponse response = spotService.createSpot(spotDto);

		assertNotNull(response);
		assertEquals(expectedResponse, response);
		verify(spotRepository).save(any(Spot.class));
	}

	@Test
	void getSpotByGeoHash_success() {
		GeoHash geoHash = GeoHash.withCharacterPrecision(lat.doubleValue(), lng.doubleValue(), 12);
		String geoHashString = geoHash.toBase32();

		when(spotRepository.findBygeoHash(geoHashString)).thenReturn(List.of(spotDto));

		List<SpotViewedResponse> result = spotService.getSpotByGeoHash(lat, lng);

		assertFalse(result.isEmpty());
		verify(spotRepository).findBygeoHash(geoHashString);
	}

	@Test
	void updateSpot_throwsNotSpotMasterException() {
		SpotDto spotDtoWithDifferentCreator = SpotDto.builder().id(1L).createdBy(2L).build();
		assertThrows(
				NotSpotMasterException.class, () -> spotService.updateSpot(spotDtoWithDifferentCreator));
	}

	@Test
	void deleteSpot_success() {
		// given
		Long id = 1L;
		when(spotRepository.findByIdAndIsDeletedFalse(id)).thenReturn(spotDto);

		// when
		spotService.deleteSpot(id);

		// then
		verify(spotRepository, times(1)).delete(id);
	}

	@Test
	void deleteSpot_throwsIllegalArgumentException() {
		Long id = 1L;
		SpotDto exceptionSpotDto = SpotDto.builder().id(1L).createdBy(2L).build();
		when(spotRepository.findByIdAndIsDeletedFalse(id)).thenReturn(exceptionSpotDto);

		assertThrows(IllegalArgumentException.class, () -> spotService.deleteSpot(id));
	}
}
