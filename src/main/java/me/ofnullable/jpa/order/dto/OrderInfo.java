package me.ofnullable.jpa.order.dto;

import lombok.Getter;
import me.ofnullable.jpa.member.domain.Address;
import me.ofnullable.jpa.order.domain.Order;
import me.ofnullable.jpa.order.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderInfo {

    private final Long orderId;
    private final String username;
    private final LocalDateTime orderDateTime;
    private final OrderStatus orderStatus;
    private final Address address;
    private final List<OrderItemInfo> orderItems = new ArrayList<>();

    public OrderInfo(Order order) {
        this.orderId = order.getId();
        this.username = order.getMember().getUsername();
        this.orderDateTime = order.getOrderDateTime();
        this.orderStatus = order.getStatus();
        this.address = order.getDelivery().getAddress();
        this.orderItems.addAll(
                order.getOrderItems().stream()
                        .map(OrderItemInfo::new)
                        .collect(Collectors.toList())
        );
    }

}
