package com.ordertogether.team14_be.order.details.entity;

import com.ordertogether.team14_be.common.persistence.entity.BaseTimeEntity;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import com.ordertogether.team14_be.spot.entity.Spot;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_detail")
public class OrderDetail extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "spot_id", nullable = false)
	private Spot spot;

	// 방장의 정보는 Spot 에 있으니까...
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "participant_id", nullable = false)
	private Member member;

	// 기본적으로 가격 입력 전엔 -1로 해두기
	@Builder.Default private Integer price = -1;

	private Boolean isPayed;

	public void updatePrice(int price) {
		this.price = price;
	}

	public void updateIsPayed(boolean isPayed) {
		this.isPayed = isPayed;
	}
}
