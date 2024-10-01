package com.ordertogether.team14_be.payment.domain;

import java.math.BigDecimal;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class PaymentOrder {

	private Long id;

	private Long productId;

	private String orderId;

	private String orderName;

	private BigDecimal amount;

	public PaymentOrder updateProductInfo(Product product) {
		if (isProductMismatch(product)) {
			throw new IllegalArgumentException("상품 정보가 일치하지 않습니다.");
		}

		this.orderName = product.getName();
		this.amount = product.getPrice();

		return this;
	}

	public boolean isMissingProductInfo() {
		return Objects.isNull(orderName) && Objects.isNull(amount);
	}

	private boolean isProductMismatch(Product product) {
		return !Objects.equals(productId, product.getId());
	}
}
