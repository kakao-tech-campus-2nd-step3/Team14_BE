package com.ordertogether.team14_be.spot.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ordertogether.team14_be.spot.controller.SpotController;
import com.ordertogether.team14_be.spot.dto.controllerdto.SpotCreationRequest;
import com.ordertogether.team14_be.spot.dto.controllerdto.SpotCreationResponse;
import com.ordertogether.team14_be.spot.dto.controllerdto.SpotViewedResponse;
import com.ordertogether.team14_be.spot.service.SpotService;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// 알맞는 서비스의 메소드를 호출했는지 & 의도한 형식이 반환되는지
@ExtendWith(MockitoExtension.class)
class SpotControllerTest {

	private MockMvc mockMvc;

	@Mock private SpotService spotService;

	@InjectMocks private SpotController spotController;

	private ObjectMapper objectMapper;

	private SpotCreationRequest spotCreationRequest;
	private SpotCreationResponse spotCreationResponse;
	private SpotViewedResponse spotViewedResponse;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(spotController).build();
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule()); // LocalTime 지원 모듈 등록

		spotCreationRequest =
				new SpotCreationRequest(
						1L,
						new BigDecimal("37.7749"),
						new BigDecimal("-122.4194"),
						"맥도날드",
						"패스트푸드",
						12000,
						"함께 주문 링크",
						"픽업위치",
						LocalTime.of(12, 0, 0));

		spotCreationResponse =
				new SpotCreationResponse(1L, "패스트푸드", "맥도날드", 12000, "픽업위치", LocalTime.of(12, 0, 0));

		spotViewedResponse = new SpotViewedResponse("패스트푸드", "맥도날드", 12000, "픽업위치");
	}

	@Test
	void createSpot_success() throws Exception {
		when(spotService.createSpot(any())).thenReturn(spotCreationResponse);

		mockMvc
				.perform(
						post("/api/v1/spot")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(spotCreationRequest)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.storeName").value("맥도날드"))
				.andExpect(jsonPath("$.category").value("패스트푸드"));
	}

	/*
	@Test
	void getSpotDetail_success() throws Exception {
			when(spotService.getSpot(1L)).thenReturn(new SpotDetailResponse());

			mockMvc.perform(get("/api/v1/spot/1"))
							.andExpect(status().isOk());
	}

	 */

	@Test
	void getSpotByGeoHash_success() throws Exception {
		when(spotService.getSpotByGeoHash(any(BigDecimal.class), any(BigDecimal.class)))
				.thenReturn(Collections.singletonList(spotViewedResponse));

		mockMvc
				.perform(get("/api/v1/spot/37.7749/-122.4194"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].storeName").value("맥도날드"))
				.andExpect(jsonPath("$[0].category").value("패스트푸드"));
	}

	/*
		@Test
		void updateSpot_success() throws Exception {
				when(spotService.updateSpot(any())).thenReturn(spotCreationResponse);

				mockMvc.perform(put("/api/v1/spot")
												.contentType(MediaType.APPLICATION_JSON)
												.content(objectMapper.writeValueAsString(spotCreationRequest)))
								.andExpect(status().isOk())
								.andExpect(jsonPath("$.storeName").value("맥도날드"))
								.andExpect(jsonPath("$.category").value("BURGER"));
		}

	*/

	@Test
	void deleteSpot_success() throws Exception {
		mockMvc.perform(delete("/api/v1/spot/1")).andExpect(status().isOk());
	}
}
