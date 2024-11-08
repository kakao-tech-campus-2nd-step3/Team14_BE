package com.ordertogether.team14_be.spot.converter;

import com.ordertogether.team14_be.spot.enums.Category;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryConverter extends AbstractCodedEnumConverter<Category, String> {

	public CategoryConverter() {
		super(Category.class); // Category.class를 부모 클래스에 전달
	}
}
