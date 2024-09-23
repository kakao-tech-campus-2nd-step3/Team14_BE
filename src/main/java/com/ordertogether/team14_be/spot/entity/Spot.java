package com.ordertogether.team14_be.spot.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Spot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(precision = 10, scale = 8)
	private BigDecimal lat;

	@Column(precision = 11, scale = 8)
	private BigDecimal lng;

	private String category;
	private String store_name;
	private Integer minimum_order_amount;

	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
	private String together_order_link;

	private String pick_up_location;
	private String delivery_status;
}
