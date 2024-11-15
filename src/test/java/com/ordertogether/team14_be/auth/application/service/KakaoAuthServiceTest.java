package com.ordertogether.team14_be.auth.application.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class KakaoAuthServiceTest {
	//	@Autowired private KakaoAuthService kakaoAuthService;
	//
	//	@Autowired private KakaoClient kakaoClient;
	//
	//	private static final String authorizationCode =
	//			"iMBWo8P8X48A6i4xSdxZyGh_FbmNY2KrdXxa3-6Pkk3lfJ15z4wOqgAAAAQKKwymAAABkyZ7AD7UNEQ5evY1pg";
	//
	//	@Test
	//	public void 인가코드로_사용자이메일_얻어오기() throws Exception {
	//
	//		String userKakaoEmail = kakaoAuthService.getKakaoUserEmail(authorizationCode);
	//
	//		assertThat(userKakaoEmail).isEqualTo("a1007panda@gmail.com");
	//	}
}
