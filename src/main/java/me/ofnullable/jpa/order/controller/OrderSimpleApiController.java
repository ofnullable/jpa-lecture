package me.ofnullable.jpa.order.controller;

import lombok.RequiredArgsConstructor;
import me.ofnullable.jpa.order.domain.Order;
import me.ofnullable.jpa.order.dto.OrderSearch;
import me.ofnullable.jpa.order.repository.dto.OrderSimpleQueryDto;
import me.ofnullable.jpa.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderService orderService;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> getOrders() {
        List<Order> orders = orderService.searchOrders(new OrderSearch());
        orders.forEach(order -> {
                    order.getMember().getId();   // Lazy load
                    order.getDelivery().getId(); // Lazy load
                }
        );
        return orders;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<OrderSimpleQueryDto> getOrdersV2() {
        List<Order> orders = orderService.searchOrders(new OrderSearch());

        return orders.stream()
                .map(OrderSimpleQueryDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<OrderSimpleQueryDto> getOrdersV3() {
        List<Order> orders = orderService.findAllWithMemberDelivery();

        return orders.stream()
                .map(OrderSimpleQueryDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> getOrdersV4() {
        return orderService.findAllDtos();
    }

}
