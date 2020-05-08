package me.ofnullable.jpa.order.dto;

import lombok.Getter;
import lombok.Setter;
import me.ofnullable.jpa.order.domain.OrderStatus;

@Getter @Setter
public class OrderSearch {

    private String memberName;

    private OrderStatus orderStatus;

}
