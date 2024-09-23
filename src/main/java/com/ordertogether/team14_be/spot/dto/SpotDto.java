package com.ordertogether.team14_be.spot.dto;

import com.ordertogether.team14_be.spot.entity.Spot;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SpotDto {
	private Long id;

	@Column(precision = 10, scale = 8)
	private BigDecimal lat;

	@Column(precision = 11, scale = 8)
	private BigDecimal lng;

	private String category;
	private String store_name;
	private int minimum_order_amount;
	private String together_order_link;
	private String pick_up_location;
	private String delivery_status;

	public Spot toEntity() {
		return Spot.builder()
				.id(id)
				.lat(lat)
				.lng(lng)
				.category(category)
				.store_name(store_name)
				.minimum_order_amount(minimum_order_amount)
				.together_order_link(together_order_link)
				.pick_up_location(pick_up_location)
				.delivery_status(delivery_status)
				.build();
	}
}
