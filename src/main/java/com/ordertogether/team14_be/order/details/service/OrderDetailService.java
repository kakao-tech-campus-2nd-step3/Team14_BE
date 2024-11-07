package com.ordertogether.team14_be.order.details.service;

import com.ordertogether.team14_be.member.persistence.MemberRepository;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import com.ordertogether.team14_be.order.details.dto.create.CreateOrderDetailReq;
import com.ordertogether.team14_be.order.details.dto.create.CreateOrderDetailRes;
import com.ordertogether.team14_be.order.details.dto.get.GetCreatorOrderInfoRes;
import com.ordertogether.team14_be.order.details.dto.get.GetOrdersInfoReq;
import com.ordertogether.team14_be.order.details.dto.get.GetOrdersInfoRes;
import com.ordertogether.team14_be.order.details.dto.get.GetParticipantOrderInfoRes;
import com.ordertogether.team14_be.order.details.dto.get.MemberBriefInfo;
import com.ordertogether.team14_be.order.details.dto.get.OrderInfo;
import com.ordertogether.team14_be.order.details.entity.OrderDetail;
import com.ordertogether.team14_be.order.details.repository.OrderDetailRepository;
import com.ordertogether.team14_be.spot.entity.Spot;
import com.ordertogether.team14_be.spot.repository.SimpleSpotRepository;
import com.ordertogether.team14_be.spot.repository.SpotRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
	private final SimpleSpotRepository simpleSpotRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final MemberRepository memberRepository;
	private final SpotRepository spotRepository;

	// 주문 상세 정보 생성 메서드
	@Transactional
	public CreateOrderDetailRes createOrderDetail(CreateOrderDetailReq createOrderDetailReq) {

		// 참여자 본인 정보 설정
		Member member =
				memberRepository
						.findById(createOrderDetailReq.getParticipantId())
						.orElseThrow(() -> new IllegalArgumentException("참여자 정보가 없습니다."));

		// 스팟 정보 설정 - spotMapper가 아직 구현되지 않아서 Simple사용
		// SpotDto spotDto =
		// spotRepository.findByIdAndIsDeletedFalse(createOrderDetailRequestDto.getSpotId());
		Spot spot =
				simpleSpotRepository
						.findById(createOrderDetailReq.getSpotId())
						.orElseThrow(() -> new IllegalArgumentException("스팟 정보가 없습니다."));

		OrderDetail orderDetail =
				OrderDetail.builder()
						.member(member)
						.spot(spot)
						.price(createOrderDetailReq.getPrice())
						.isPayed(createOrderDetailReq.isPayed())
						.build();

		OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);

		return CreateOrderDetailRes.builder()
				.id(savedOrderDetail.getId())
				.price(savedOrderDetail.getPrice())
				.isPayed(savedOrderDetail.isPayed())
				.participantName(savedOrderDetail.getMember().getDeliveryName())
				.spotName(spot.getStoreName())
				.build();
	}

	@Transactional(readOnly = true)
	public GetOrdersInfoRes getOrdersInfo(Member member, GetOrdersInfoReq dto) {
		Page<OrderDetail> orderDetails =
				orderDetailRepository.findAllByMember(
						member,
						PageRequest.of(
								dto.page(),
								dto.size(),
								dto.sort() == null ? Sort.unsorted() : Sort.by(dto.sort().get(1))));

		return new GetOrdersInfoRes(
				orderDetails.getTotalPages(),
				orderDetails.getTotalElements(),
				orderDetails.getContent().stream()
						.map(order -> new OrderInfo(member.getId(), order, order.getSpot()))
						.toList());
	}

	@Transactional(readOnly = true)
	public GetParticipantOrderInfoRes getParticipantOrderInfo(Member member, Long spotId) {
		Spot spot =
				simpleSpotRepository
						.findById(spotId)
						.orElseThrow(() -> new IllegalArgumentException("스팟 정보가 없습니다."));
		Member creator = spot.getMember();

		if (member.getId().equals(creator.getId()))
			throw new IllegalArgumentException("방장입니다.(참여자만 사용 가능)");

		OrderDetail orderDetail =
				orderDetailRepository
						.findFirstBySpotAndMember(spot, member)
						.orElseThrow(() -> new IllegalArgumentException("주문 정보가 없습니다."));

		return new GetParticipantOrderInfoRes(
				spot.getCategory().toString(),
				spot.getStoreName(),
				spot.getMinimumOrderAmount(),
				spot.getPickUpLocation(),
				spot.getDeliveryStatus(),
				orderDetail.getPrice());
	}

	@Transactional(readOnly = true)
	public GetCreatorOrderInfoRes getCreatorOrderInfo(Member member, Long spotId) {
		Spot spot =
				simpleSpotRepository
						.findById(spotId)
						.orElseThrow(() -> new IllegalArgumentException("스팟 정보가 없습니다."));
		Member creator = spot.getMember();

		if (!member.getId().equals(creator.getId()))
			throw new IllegalArgumentException("참여자입니다.(방장만 사용 가능)");

		List<OrderDetail> orders = orderDetailRepository.findAllBySpot(spot);

		return new GetCreatorOrderInfoRes(
				spot.getCategory().toString(),
				spot.getStoreName(),
				spot.getMinimumOrderAmount(),
				spot.getPickUpLocation(),
				spot.getDeliveryStatus(),
				orders.stream()
						.map(
								order -> {
									Member participant = order.getMember();
									return new MemberBriefInfo(
											participant.getId(),
											participant.getDeliveryName(),
											order.getPrice(),
											order.isPayed());
								})
						.toList()); // memberInfo
	}
}
