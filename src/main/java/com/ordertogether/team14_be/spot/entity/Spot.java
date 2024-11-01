package com.ordertogether.team14_be.spot.entity;

import com.ordertogether.team14_be.common.persistence.entity.BaseEntity;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import com.ordertogether.team14_be.spot.enums.Category;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@SuperBuilder // 상속받은 필드도 빌더에서 사용
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(indexes = {@Index(name = "idx_lat_lng", columnList = "lat, lng")})
@DynamicUpdate // 변경한 필드만 대응
public class Spot extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "master_id") // PK 참조해서 master_id 속성 추가
	private Member member;

	@Column(precision = 10, scale = 8)
	private BigDecimal lat;

	@Column(precision = 11, scale = 8)
	private BigDecimal lng;

	private Category category;
	private String storeName;
	private Integer minimumOrderAmount;

	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
	private String togetherOrderLink;

	private String pickUpLocation;
	private String deliveryStatus;
	private LocalTime deadlineTime;
	private String geoHash;
	@Builder.Default private Boolean isDeleted = false;

	public void delete() {
		this.isDeleted = true;
	}

	public void restore() {
		this.isDeleted = false;
	}
}
