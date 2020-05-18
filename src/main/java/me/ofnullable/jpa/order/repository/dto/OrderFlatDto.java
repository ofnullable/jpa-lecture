package me.ofnullable.jpa.order.repository.dto;

import lombok.Getter;
import lombok.Setter;
import me.ofnullable.jpa.member.domain.Address;
import me.ofnullable.jpa.order.domain.OrderStatus;

import java.time.LocalDateTime;

@Getter @Setter
public class OrderFlatDto {

    private Long orderId;
    private String username;
    private LocalDateTime orderDateTime;
    private OrderStatus orderStatus;
    private Address address;

    private String itemName;
    private int orderPrice;
    private int count;

    public OrderFlatDto(Long orderId, String username, LocalDateTime orderDateTime, OrderStatus orderStatus, Address address, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.username = username;
        this.orderDateTime = orderDateTime;
        this.orderStatus = orderStatus;
        this.address = address;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }

}
