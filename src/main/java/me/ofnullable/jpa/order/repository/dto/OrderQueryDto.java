package me.ofnullable.jpa.order.repository.dto;

import lombok.Getter;
import lombok.Setter;
import me.ofnullable.jpa.member.domain.Address;
import me.ofnullable.jpa.order.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class OrderQueryDto {

    private Long orderId;
    private String username;
    private LocalDateTime orderDateTime;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

    public OrderQueryDto(Long orderId, String username, LocalDateTime orderDateTime, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.username = username;
        this.orderDateTime = orderDateTime;
        this.orderStatus = orderStatus;
        this.address = address;
    }

}
