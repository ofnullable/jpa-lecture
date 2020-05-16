package me.ofnullable.jpa.order.repository;

import lombok.RequiredArgsConstructor;
import me.ofnullable.jpa.order.dto.OrdersResponse;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrdersResponse> findAllDtos() {
        return em.createQuery(
                "select new me.ofnullable.jpa.order.dto.OrdersResponse(o.id, o.status, o.orderDateTime, m.username, d.address) " +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrdersResponse.class)
                .getResultList();
    }
}
