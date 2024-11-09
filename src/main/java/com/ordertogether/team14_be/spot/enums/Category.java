package com.ordertogether.team14_be.spot.enums;

import com.ordertogether.team14_be.spot.converter.AbstractCodedEnumConverter;
import com.ordertogether.team14_be.spot.converter.CodedEnum;
import java.util.Arrays;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category implements CodedEnum<String> {
	JOKBAL_BOSSAM("001", "족발, 보쌈"),
	KOREAN_STEW("002", "찜, 탕, 찌개"),
	JAPANESE_FOOD("003", "돈까스, 회, 일식"),
	PIZZA("004", "피자"),
	MEAT("005", "고기, 구이"),
	NIGHT_FOOD("006", "야식"),
	WESTERN_STYLE("007", "양식"),
	CHICKEN("008", "치킨"),
	CHINESE_FOOD("009", "중식"),
	ASIAN("010", "아시안"),
	CARBOHYDRATE("011", "백반, 죽, 국수"),
	DOSIRAK("012", "도시락"),
	K_SNACK_FOOD("013", "분식"),
	CAFE("014", "카페, 디저트"),
	BURGER("015", "패스트푸드");

	private final String code;
	private final String stringCategory;

	// 한글 설명(String)을 ENUM으로 변환
	public static Optional<Category> fromStringToEnum(String category) {
		return Arrays.stream(Category.values())
				.filter(c -> c.getStringCategory().equals(category))
				.findFirst();
	}

	// ENUM을 코드(String)으로 변환
	public static Optional<String> fromEnumToString(Category category) {
		return Optional.of(category.getCode())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."))
				.describeConstable(); // 상수 풀에 저장되는 값을 안전하게 참조
	}

	@jakarta.persistence.Converter(autoApply = true)
	static class Converter extends AbstractCodedEnumConverter<Category, String> {
		public Converter() {
			super(Category.class);
		}
	}

	/*
		spotDto.getCategory().getCode() → "015"
		spotDto.getCategory().getCategory() → "패스트푸드"
		spotDto.getCategory() 자체는 Category.BURGER ENUM 객체를 반환
	*/
}
