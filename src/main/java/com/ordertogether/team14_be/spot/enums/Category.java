package com.ordertogether.team14_be.spot.enums;

import com.ordertogether.team14_be.spot.converter.AbstractCodedEnumConverter;
import com.ordertogether.team14_be.spot.converter.CodedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category implements CodedEnum<String> {
  JOKBAL_BOSSAM("족발, 보쌈"),
  JAPANESE_FOOD("돈까스, 회, 일식"),
  MEAT("고기"),
  KOREAN_STEW("찜, 탕, 찌개"),
  WESTERN_STYLE("양식"),
  CHINESE_FOOD("중식"),
  ASIAN("아시안"),
  CHICKEN("치킨"),
  CARBOHYDRATE("백반, 죽, 국수"),
  BURGER("버거"),
  K_SNACK_FOOD("분식"),
  CAFE("카페, 디저트");

  private final String category;

  public String getCode() {
    return category;
  }

  @jakarta.persistence.Converter(autoApply = true)
  static class Converter extends AbstractCodedEnumConverter<Category, String> {
    public Converter() {
      super(Category.class);
    }
  }
}
