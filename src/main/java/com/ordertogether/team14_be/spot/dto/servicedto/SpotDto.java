package com.ordertogether.team14_be.spot.dto.servicedto;

import com.ordertogether.team14_be.spot.enums.Category;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.*;

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

	@Setter private Category category;
	private String storeName;
	private int minimumOrderAmount;
	private String togetherOrderLink;
	private String pickUpLocation;
	private String deliveryStatus;
	private LocalTime deadlineTime;
	@Setter private String geoHash;
	private boolean isDeleted;
	@Setter private LocalDateTime createdAt;
	@Setter private LocalDateTime modifiedAt;
	@Setter private Long createdBy;
	@Setter private Long modifiedBy;
}
