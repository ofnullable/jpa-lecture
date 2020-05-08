package me.ofnullable.jpa.order.repository;

import lombok.RequiredArgsConstructor;
import me.ofnullable.jpa.order.domain.Order;
import me.ofnullable.jpa.order.dto.OrderSearch;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public Order save(Order order) {
        em.persist(order);
        return order;
    }

    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }

    // TODO: QueryDSL
    public List<Order> searchOrder(OrderSearch orderSearch) {
        return Collections.emptyList();
    }

}
