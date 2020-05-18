package me.ofnullable.jpa.order.repository.dto;

import lombok.Getter;
import lombok.Setter;
import me.ofnullable.jpa.member.domain.Address;
import me.ofnullable.jpa.order.domain.Order;
import me.ofnullable.jpa.order.domain.OrderStatus;

import java.time.LocalDateTime;

@Getter @Setter
public class OrderSimpleQueryDto {

    private Long orderId;
    private OrderStatus orderStatus;
    private LocalDateTime orderDateTime;
    private String username;
    private Address address;

    public OrderSimpleQueryDto(Long orderId, OrderStatus orderStatus, LocalDateTime orderDateTime, String username, Address address) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
        this.username = username;
        this.address = address;
    }

    public OrderSimpleQueryDto(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getStatus();
        this.orderDateTime = order.getOrderDateTime();
        this.username = order.getMember().getUsername();
        this.address = order.getDelivery().getAddress();
    }

}
