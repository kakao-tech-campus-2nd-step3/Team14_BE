package com.ordertogether.team14_be.spot.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.Objects;

@Converter
public class AbstractCodedEnumConverter<T extends Enum<T> & CodedEnum<E>, E>
		implements AttributeConverter<T, E> {

	private final Class<T> clazz;

	public AbstractCodedEnumConverter(Class<T> clazz) {
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked") // 경고 억제
	@Override
	public E convertToDatabaseColumn(
			T attribute) { // Converts the value stored in the entity attribute into the data
		// representation to be stored in the database.
		// return attribute.getCode(); -> 코드 저장 ex) KOREAN_STEW -> "찜, 탕, 찌개"
		return (E) attribute.name(); // Enum의 이름을 그대로 반환 -> DB에 ENUM을 그대로 저장
	}

	@Override
	public T convertToEntityAttribute(
			E dbData) { // Converts the data stored in the database column into the value to be stored
		// in the entity attribute.
		if (Objects.isNull(dbData)) {
			return null;
		}
		T asdfsdf =
				Arrays.stream(clazz.getEnumConstants())
						.filter(e -> e.getCode().equals(dbData))
						.findFirst()
						.orElseThrow(() -> new IllegalArgumentException("Unknown code: " + dbData));
		System.out.println(asdfsdf.name());
		return Arrays.stream(clazz.getEnumConstants())
				.filter(e -> e.getCode().equals(dbData))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Unknown code: " + dbData));
	}
}
