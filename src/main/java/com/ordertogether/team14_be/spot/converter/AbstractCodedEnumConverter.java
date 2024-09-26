package com.ordertogether.team14_be.spot.converter;

import jakarta.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Objects;

public class AbstractCodedEnumConverter<T extends Enum<T> & CodedEnum<E>, E>
    implements AttributeConverter<T, E> {

  private final Class<T> clazz;

  public AbstractCodedEnumConverter(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public E convertToDatabaseColumn(
      T attribute) { // Converts the value stored in the entity attribute into the data
    // representation to be stored in the database.
    return attribute.getCode();
  }

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
