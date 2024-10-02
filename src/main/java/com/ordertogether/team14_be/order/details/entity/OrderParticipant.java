//package com.ordertogether.team14_be.order.details.entity;
//
//import com.ordertogether.team14_be.member.persistence.entity.Member;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.experimental.SuperBuilder;
//
//// 주문 정보에는 참여자 정보(즉 여러 명의 Member)가 필요합니다.
//// 따라서 Member 와 다대다 연결을 위한 중간 테이블(연결 엔티티)를 만들었습니다.
//@Entity
//@Getter
//@SuperBuilder
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(name = "order_participant")
//public class OrderParticipant {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "order_detail_id", nullable = false)
//	private OrderDetail orderDetail;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "member_id", nullable = false)
//	private Member member;
//}
