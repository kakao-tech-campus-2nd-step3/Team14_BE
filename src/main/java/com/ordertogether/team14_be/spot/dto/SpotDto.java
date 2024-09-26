package com.ordertogether.team14_be.spot.dto;

import com.ordertogether.team14_be.spot.entity.Spot;
import com.ordertogether.team14_be.spot.enums.Category;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SpotDto {
	private Long id;

	@Column(precision = 10, scale = 8)
	private BigDecimal lat;

	@Column(precision = 11, scale = 8)
	private BigDecimal lng;

	private Category category;
	private String storeName;
	private int minimumOrderAmount;
	private String togetherOrderLink;
	private String pickUpLocation;
	private String deliveryStatus;
	private boolean isDeleted;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private Long createdBy;
	private Long modifiedBy;

	public Spot toEntity() {
		return Spot.builder()
				.id(id)
				.lat(lat)
				.lng(lng)
				.category(category)
				.storeName(storeName)
				.minimumOrderAmount(minimumOrderAmount)
				.togetherOrderLink(togetherOrderLink)
				.pickUpLocation(pickUpLocation)
				.deliveryStatus(deliveryStatus)
				.isDeleted(isDeleted)
				.createdAt(createdAt)
				.modifiedAt(modifiedAt)
				.createdBy(createdBy)
				.modifiedBy(modifiedBy)
				.build();
	}
}
