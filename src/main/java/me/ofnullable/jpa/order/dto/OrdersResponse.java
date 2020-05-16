package me.ofnullable.jpa.order.dto;

import lombok.Getter;
import lombok.Setter;
import me.ofnullable.jpa.member.domain.Address;
import me.ofnullable.jpa.order.domain.Order;
import me.ofnullable.jpa.order.domain.OrderStatus;

import java.time.LocalDateTime;

@Getter @Setter
public class OrdersResponse {

    private Long orderId;
    private OrderStatus orderStatus;
    private LocalDateTime orderDateTime;
    private String username;
    private Address address;

    public OrdersResponse(Long orderId, OrderStatus orderStatus, LocalDateTime orderDateTime, String username, Address address) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
        this.username = username;
        this.address = address;
    }

    public OrdersResponse(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getStatus();
        this.orderDateTime = order.getOrderDateTime();
        this.username = order.getMember().getUsername();
        this.address = order.getDelivery().getAddress();
    }

}
