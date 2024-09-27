package com.ordertogether.team14_be.payment.persistence.jpa.mapper;

import com.ordertogether.team14_be.payment.domain.Product;
import com.ordertogether.team14_be.payment.persistence.jpa.entity.ProductEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ProductMapper {

	public static ProductEntity mapToEntity(Product domain) {
		return ProductEntity.builder()
				.id(domain.getId())
				.name(domain.getName())
				.price(domain.getPrice())
				.build();
	}

	public static Product mapToDomain(ProductEntity entity) {
		return Product.builder()
				.id(entity.getId())
				.name(entity.getName())
				.price(entity.getPrice())
				.build();
	}
}
