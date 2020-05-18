package me.ofnullable.jpa.order.controller;

import lombok.RequiredArgsConstructor;
import me.ofnullable.jpa.order.domain.Order;
import me.ofnullable.jpa.order.dto.OrderInfo;
import me.ofnullable.jpa.order.dto.OrderSearch;
import me.ofnullable.jpa.order.repository.OrderQueryRepository;
import me.ofnullable.jpa.order.repository.dto.OrderQueryDto;
import me.ofnullable.jpa.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;
    private final OrderQueryRepository orderQueryRepository;

    /*
     * Lazy load and return entity
     */
    @GetMapping("/api/v1/orders")
    public List<Order> getOrdersV1() {
        List<Order> orders = orderService.searchOrders(new OrderSearch());

        orders.forEach(order -> {
            order.getMember().getId();
            order.getDelivery().getAddress();
            order.getOrderItems().forEach(orderItem -> orderItem.getItem().getId());
        });

        return orders;
    }

    /*
     * Lazy load and return dto
     */
    @GetMapping("/api/v2/orders")
    public List<OrderInfo> ordersV2() {
        List<Order> orders = orderService.searchOrders(new OrderSearch());
        return orders.stream()
                .map(OrderInfo::new)
                .collect(Collectors.toList());
    }

    /*
     * Fetch join and return dto
     */
    @GetMapping("/api/v3/orders")
    public List<OrderInfo> ordersV3() {
        List<Order> orders = orderService.findAllWithItem();
        return orders.stream()
                .map(OrderInfo::new)
                .collect(Collectors.toList());
    }

    /*
     * Fetch join and Lazy loading
     */
    @GetMapping("/api/v3.1/orders")
    public List<OrderInfo> ordersV3_1(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "offset", defaultValue = "100") int limit
    ) {
        List<Order> orders = orderService.findAllWithMemberDelivery(offset, limit);
        return orders.stream()
                .map(OrderInfo::new)
                .collect(Collectors.toList());
    }

    /*
     * Lazy(?) load and return dto
     */
    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }

}
