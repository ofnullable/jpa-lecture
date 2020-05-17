package me.ofnullable.jpa.order.dto;

import lombok.Getter;
import me.ofnullable.jpa.order.domain.OrderItem;

@Getter
public class OrderItemInfo {

    private final String itemName;
    private final int orderPrice;
    private final int count;

    public OrderItemInfo(OrderItem orderItem) {
        this.itemName = orderItem.getItem().getName();
        this.orderPrice = orderItem.getPrice();
        this.count = orderItem.getCount();
    }

}
