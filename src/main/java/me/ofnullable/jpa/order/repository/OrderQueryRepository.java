package me.ofnullable.jpa.order.repository;

import lombok.RequiredArgsConstructor;
import me.ofnullable.jpa.order.repository.dto.OrderItemQueryDto;
import me.ofnullable.jpa.order.repository.dto.OrderQueryDto;
import me.ofnullable.jpa.order.repository.dto.OrderSimpleQueryDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

}
