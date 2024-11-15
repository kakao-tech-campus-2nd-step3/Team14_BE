package com.ordertogether.team14_be.spot.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ch.hsr.geohash.GeoHash;
import com.ordertogether.team14_be.member.application.service.MemberService;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import com.ordertogether.team14_be.spot.dto.controllerdto.SpotCreationResponse;
import com.ordertogether.team14_be.spot.dto.controllerdto.SpotModifyResponse;
import com.ordertogether.team14_be.spot.dto.controllerdto.SpotViewedResponse;
import com.ordertogether.team14_be.spot.dto.servicedto.SpotDto;
import com.ordertogether.team14_be.spot.entity.Spot;
import com.ordertogether.team14_be.spot.enums.Category;
import com.ordertogether.team14_be.spot.exception.NotSpotMasterException;
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
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SpotServiceTest {

	@Mock private MemberService memberService;

	@Mock private SpotRepository spotRepository;

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
						.geoHash("9q8yyk8ytpxr")
						.build();
	}

	@Test
	void createSpot_success() {
		when(spotRepository.save(any(Spot.class))).thenReturn(spotDto);
		when(memberService.findMember(1L))
				.thenReturn(new Member(1L, "example@naver.com", 12000, "010-1234-1234", "닉네임", "배민"));
		SpotCreationResponse response = spotService.createSpot(spotDto, 1L);

		assertNotNull(response);
		System.out.println(response);
		assertEquals(spotDto.getId(), response.id());
		assertEquals(1L, spotDto.getMemberId());
		assertEquals(spotDto.getLat(), response.lat());
		assertEquals(spotDto.getLng(), response.lng());
		assertEquals(spotDto.getCategory().getStringCategory(), response.category());
		assertEquals(spotDto.getStoreName(), response.storeName());
		assertEquals(spotDto.getMinimumOrderAmount(), response.minimumOrderAmount());
		assertEquals(spotDto.getPickUpLocation(), response.pickUpLocation());

		verify(spotRepository).save(any(Spot.class));
	}

	@Test
	void getSpotByGeoHash_success() {
		GeoHash geoHash = GeoHash.withCharacterPrecision(lat.doubleValue(), lng.doubleValue(), 12);
		String geoHashString = geoHash.toBase32();

		when(spotRepository.findBygeoHash(geoHashString)).thenReturn(List.of(spotDto));

		List<SpotViewedResponse> result = spotService.getSpotByGeoHash(lat, lng);

		assertFalse(result.isEmpty());
		assertThat(result.getFirst().id()).isEqualTo(1L);
		assertThat(result.getFirst().lat()).isEqualTo(lat);
		assertThat(result.getFirst().lng()).isEqualTo(lng);
		assertThat(result.getFirst().category()).isEqualTo("패스트푸드");
		assertThat(result.getFirst().storeName()).isEqualTo(spotDto.getStoreName());
		assertThat(result.getFirst().minimumOrderAmount()).isEqualTo(spotDto.getMinimumOrderAmount());
		assertThat(result.getFirst().pickUpLocation()).isEqualTo(spotDto.getPickUpLocation());
		assertThat(result.getFirst().deadlineTime()).isEqualTo(spotDto.getDeadlineTime());

		verify(spotRepository).findBygeoHash(geoHashString);
	}

	@Test
	void updateSpot_success() {
		SpotDto spotDtoWithSameCreator = SpotDto.builder().id(1L).createdBy(1L).build();
		when(spotRepository.update(spotDtoWithSameCreator)).thenReturn(spotDto);

		SpotModifyResponse expectedSpotModifyResponse =
				spotService.updateSpot(spotDtoWithSameCreator, 1L);

		assertEquals(spotDto.getLat(), expectedSpotModifyResponse.lat());
		assertEquals(spotDto.getLng(), expectedSpotModifyResponse.lng());
		assertEquals(spotDto.getCategory().getStringCategory(), expectedSpotModifyResponse.category());
		assertEquals(spotDto.getStoreName(), expectedSpotModifyResponse.storeName());
		assertEquals(spotDto.getMinimumOrderAmount(), expectedSpotModifyResponse.minimumOrderAmount());
		assertEquals(spotDto.getPickUpLocation(), expectedSpotModifyResponse.pickUpLocation());
	}

	@Test
	void updateSpot_success_gpt() {
		// 업데이트된 SpotDto 예상 값 설정
		SpotDto updatedSpotDto =
				SpotDto.builder()
						.lat(new BigDecimal("37.7749"))
						.lng(new BigDecimal("-122.4194"))
						.category(Category.BURGER)
						.storeName("맥도날드")
						.memberId(1L)
						.minimumOrderAmount(12000)
						.deadlineTime(LocalTime.of(12, 0, 0))
						.pickUpLocation("픽업위치")
						.geoHash("9q8yyk8ytpxr")
						.createdBy(1L)
						.build();

		// Mock 설정
		when(spotRepository.update(updatedSpotDto)).thenReturn(updatedSpotDto);

		// 테스트 실행
		SpotModifyResponse response = spotService.updateSpot(updatedSpotDto, 1L);

		// 검증
		assertEquals(updatedSpotDto.getLat(), response.lat());
		assertEquals(updatedSpotDto.getLng(), response.lng());
		assertEquals(updatedSpotDto.getCategory().getStringCategory(), response.category());
		assertEquals(updatedSpotDto.getStoreName(), response.storeName());
		assertEquals(updatedSpotDto.getMinimumOrderAmount(), response.minimumOrderAmount());
		assertEquals(updatedSpotDto.getPickUpLocation(), response.pickUpLocation());
	}

	@Test
	void updateSpot_throwsNotSpotMasterException() {
		SpotDto spotDtoWithDifferentCreator = SpotDto.builder().id(1L).createdBy(2L).build();
		assertThrows(
				NotSpotMasterException.class,
				() -> spotService.updateSpot(spotDtoWithDifferentCreator, 1L));
	}

	@Test
	void deleteSpot_success() {
		// given
		Long id = 1L;
		when(spotRepository.findByIdAndIsDeletedFalse(id)).thenReturn(spotDto);

		// when
		spotService.deleteSpot(1L);

		// then
		verify(spotRepository, times(1)).delete(id);
	}

	@Test
	void deleteSpot_throwsIllegalArgumentException() {
		Long id = 1L;
		SpotDto exceptionSpotDto = SpotDto.builder().id(1L).createdBy(2L).build();
		when(spotRepository.findByIdAndIsDeletedFalse(id)).thenReturn(exceptionSpotDto);

		assertThrows(
				IllegalArgumentException.class, () -> spotService.deleteSpot(1L)); // 2L이 생성, 1L이 삭제하려 할 때
	}
}
