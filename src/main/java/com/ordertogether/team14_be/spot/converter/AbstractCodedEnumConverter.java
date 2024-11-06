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
	// Entity의 enum값을 DB에 변환하는 방식을 정의
	public E convertToDatabaseColumn(
			T attribute) { // Converts the value stored in the entity attribute into the data
		// representation to be stored in the database.
		return attribute.getCode(); // 코드 저장 ex) KOREAN_STEW -> "찜, 탕, 찌개"
	}

	// DB값을 Entity의 enum값으로 변환하는 방식을 정의
	@Override
	public T convertToEntityAttribute(
			E dbData) { // Converts the data stored in the database column into the value to be stored
		// in the entity attribute.
		if (Objects.isNull(dbData)) {
			return null;
		}
		return Arrays.stream(clazz.getEnumConstants())
				.filter(e -> e.getCode().equals(dbData))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Unknown code: " + dbData));
	}
}
