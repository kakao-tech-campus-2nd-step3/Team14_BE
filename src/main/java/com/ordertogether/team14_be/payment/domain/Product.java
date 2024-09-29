package com.ordertogether.team14_be.payment.domain;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Product {

	private Long id;

	private String name;

	private BigDecimal price;
}
