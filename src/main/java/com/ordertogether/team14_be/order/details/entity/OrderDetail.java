package com.ordertogether.team14_be.order.details.entity;

import com.ordertogether.team14_be.common.persistence.entity.BaseTimeEntity;
import com.ordertogether.team14_be.memebr.persistence.entity.Member;
import com.ordertogether.team14_be.spot.entity.Spot;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

	// 다대다 관계를 풀기 위한 OrderParticipant 사용
	@OneToMany(mappedBy = "orderDetail", cascade = CascadeType.ALL)
	private List<OrderParticipant> participants = new ArrayList<>();

	// 방장 정보를 별도로 저장하는 필드
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "leader_id", nullable = false)
	private Member leader;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spot_id", nullable = false)
	private Spot spot;

	private int price;

	private boolean isPayed;
}
