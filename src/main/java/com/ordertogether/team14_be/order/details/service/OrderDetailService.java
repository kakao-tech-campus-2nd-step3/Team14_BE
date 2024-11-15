package com.ordertogether.team14_be.order.details.service;

import com.ordertogether.team14_be.member.persistence.MemberRepository;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import com.ordertogether.team14_be.order.details.dto.create.CreateOrderDetailReq;
import com.ordertogether.team14_be.order.details.dto.create.CreateOrderDetailRes;
import com.ordertogether.team14_be.order.details.dto.get.GetCreatorOrderInfoRes;
import com.ordertogether.team14_be.order.details.dto.get.GetOrdersInfoRes;
import com.ordertogether.team14_be.order.details.dto.get.GetParticipantOrderInfoRes;
import com.ordertogether.team14_be.order.details.dto.get.MemberBriefInfo;
import com.ordertogether.team14_be.order.details.dto.get.OrderInfo;
import com.ordertogether.team14_be.order.details.dto.update.CompleteOrderReq;
import com.ordertogether.team14_be.order.details.dto.update.UpdateOrderPriceReq;
import com.ordertogether.team14_be.order.details.entity.OrderDetail;
import com.ordertogether.team14_be.order.details.repository.OrderDetailRepository;
import com.ordertogether.team14_be.spot.entity.Spot;
import com.ordertogether.team14_be.spot.repository.SimpleSpotRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailService {
	private final SimpleSpotRepository simpleSpotRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final MemberRepository memberRepository;

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
				.isPayed(savedOrderDetail.getIsPayed())
				.participantName(savedOrderDetail.getMember().getDeliveryName())
				.spotName(spot.getStoreName())
				.build();
	}

	@Transactional
	public CreateOrderDetailRes participantOrder(Member member, CreateOrderDetailReq dto) {
		Spot spot =
				simpleSpotRepository
						.findById(dto.getSpotId())
						.orElseThrow(() -> new IllegalArgumentException("스팟 정보가 없습니다."));

		if (member.getId().equals(spot.getMember().getId()))
			throw new IllegalArgumentException("방장입니다.(참여자만 사용 가능)");

		OrderDetail orderDetail =
				orderDetailRepository.save(
						OrderDetail.builder()
								.member(member)
								.spot(spot)
								.price(-1) // 추후 가격이 -1이면 주문이 안된 것으로 처리
								.isPayed(false)
								.build());
		return new CreateOrderDetailRes(
				orderDetail.getId(),
				orderDetail.getPrice(),
				orderDetail.getIsPayed(),
				member.getDeliveryName(),
				spot.getStoreName());
	}

	@Transactional(readOnly = true)
	public GetOrdersInfoRes getOrdersInfo(Member member, int page, int size, String sort) {
		Page<OrderDetail> orderDetails =
				orderDetailRepository.findByMember(
						member,
						PageRequest.of(
								page,
								size,
								sort == null ? Sort.unsorted() : Sort.by(Sort.Order.desc(sort.split(",")[0]))));

		log.info("orderDetails: {}", orderDetails);
		log.info("orderDetails.getTotalPages(): {}", orderDetails.getTotalPages());
		log.info("orderDetails.getTotalElements(): {}", orderDetails.getTotalElements());
		log.info("orderDetails.getContent(): {}", orderDetails.getContent());

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
						.findBySpotAndMember(spot, member)
						.orElseThrow(() -> new IllegalArgumentException("주문 정보가 없습니다."));

		return new GetParticipantOrderInfoRes(
				orderDetail.getId(),
				spot.getCategory().getStringCategory(),
				spot.getStoreName(),
				spot.getMinimumOrderAmount(),
				spot.getPickUpLocation(),
				orderDetail.getCreatedAt(),
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

		List<OrderDetail> filteredOrders =
				orderDetailRepository.findAllBySpot(spot).stream()
						.filter(order -> !order.getMember().getId().equals(creator.getId()))
						.toList(); // creator의 id가 아닌 것만 필터링

		return new GetCreatorOrderInfoRes(
				spot.getCategory().getStringCategory(),
				spot.getStoreName(),
				spot.getMinimumOrderAmount(),
				spot.getPickUpLocation(),
				spot.getCreatedAt(),
				filteredOrders.stream()
						.map(
								order -> {
									Member participant = order.getMember();
									return new MemberBriefInfo(
											participant.getId(),
											participant.getDeliveryName(),
											order.getPrice(),
											order.getIsPayed());
								})
						.toList()); // memberInfo
	}

	@Transactional
	public void updateOrderPrice(Member member, UpdateOrderPriceReq dto) {
		OrderDetail orderDetail =
				orderDetailRepository
						.findById(dto.orderId())
						.orElseThrow(() -> new IllegalArgumentException("주문 정보를 찾을 수 없습니다."));

		if (!orderDetail.getMember().getId().equals(member.getId())) {
			throw new IllegalArgumentException("주문의 참여자가 아닙니다.");
		}

		orderDetail.updatePrice(dto.price());
		orderDetail.updateIsPayed(true);
		orderDetailRepository.save(orderDetail);
	}

	@Transactional
	public void completeOrder(Member member, CompleteOrderReq dto) {
		OrderDetail orderDetail =
				orderDetailRepository
						.findById(dto.orderId())
						.orElseThrow(() -> new IllegalArgumentException("주문 정보를 찾을 수 없습니다."));

		if (!orderDetail.getMember().getId().equals(member.getId())) {
			throw new IllegalArgumentException("주문의 참여자가 아닙니다.");
		}

		orderDetail.updateIsPayed(true);
		orderDetailRepository.save(orderDetail);
	}

	@Transactional
	public void deleteOrderDetail(Member member, Long spotId) {
		Spot spot =
				simpleSpotRepository
						.findById(spotId)
						.orElseThrow(() -> new IllegalArgumentException("스팟 정보가 없습니다."));
		Member creator = spot.getMember();

		if (member.getId().equals(creator.getId()))
			throw new IllegalArgumentException("방장입니다.(참여자만 사용 가능)");

		OrderDetail orderDetail =
				orderDetailRepository
						.findBySpotAndMember(spot, member)
						.orElseThrow(() -> new IllegalArgumentException("주문 정보가 없습니다."));

		if (!orderDetail.getMember().getId().equals(member.getId())) {
			throw new IllegalArgumentException("주문의 참여자가 아닙니다.");
		}

		orderDetailRepository.delete(orderDetail);
	}
}
