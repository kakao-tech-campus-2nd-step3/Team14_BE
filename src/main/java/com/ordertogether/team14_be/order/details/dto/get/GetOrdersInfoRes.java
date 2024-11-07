package com.ordertogether.team14_be.order.details.dto.get;

import java.util.List;

public record GetOrdersInfoRes(int totalPages, long totalElements, List<OrderInfo> ordersInfo) {}
