package me.ofnullable.jpa.order.repository;

import lombok.RequiredArgsConstructor;
import me.ofnullable.jpa.order.repository.dto.OrderItemQueryDto;
import me.ofnullable.jpa.order.repository.dto.OrderQueryDto;
import me.ofnullable.jpa.order.repository.dto.OrderSimpleQueryDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderSimpleQueryDto> findAllDtos() {
        return em.createQuery(
                "select new me.ofnullable.jpa.order.repository.dto.OrderSimpleQueryDto(o.id, o.status, o.orderDateTime, m.username, d.address) " +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
    }

    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> orders = findOrders();

        orders.forEach(order -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(order.getOrderId());
            order.setOrderItems(orderItems);
        });

        return orders;
    }


    public List<OrderQueryDto> findAllByDtos() {
        List<OrderQueryDto> orders = findOrders();

        List<Long> orderIds = toOrderIds(orders);

        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(orderIds);

        orders.forEach(order -> order.setOrderItems(orderItemMap.get(order.getOrderId())));

        return orders;
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                "select new me.ofnullable.jpa.order.repository.dto.OrderQueryDto(o.id, m.username, o.orderDateTime, o.status, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                "select new me.ofnullable.jpa.order.repository.dto.OrderItemQueryDto(oi.order.id, i.name, oi.price, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    private List<Long> toOrderIds(List<OrderQueryDto> orders) {
        return orders.stream()
                .map(OrderQueryDto::getOrderId)
                .collect(Collectors.toList());
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems = em.createQuery(
                "select new me.ofnullable.jpa.order.repository.dto.OrderItemQueryDto(oi.order.id, i.name, oi.price, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        return orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
    }

}
