package me.ofnullable.jpa.order.controller;

import lombok.RequiredArgsConstructor;
import me.ofnullable.jpa.order.domain.Order;
import me.ofnullable.jpa.order.dto.OrderSearch;
import me.ofnullable.jpa.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @GetMapping("/api/v1/orders")
    public List<Order> getOrdersWithItems() {
        List<Order> orders = orderService.searchOrders(new OrderSearch());

        orders.forEach(order -> {
            order.getMember().getId();
            order.getDelivery().getAddress();
            order.getOrderItems().forEach(orderItem -> orderItem.getItem().getId());
        });

        return orders;
    }

}
