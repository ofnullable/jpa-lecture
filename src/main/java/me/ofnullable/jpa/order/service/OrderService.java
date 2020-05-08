package me.ofnullable.jpa.order.service;

import lombok.RequiredArgsConstructor;
import me.ofnullable.jpa.item.repository.ItemRepository;
import me.ofnullable.jpa.member.repository.MemberRepository;
import me.ofnullable.jpa.order.domain.Order;
import me.ofnullable.jpa.order.domain.OrderItem;
import me.ofnullable.jpa.order.dto.OrderSearch;
import me.ofnullable.jpa.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Order placeOrder(Long memberId, Long itemId, int count) {
        var member = memberRepository.findOne(memberId);
        var item = itemRepository.findOne(itemId);

        var orderItem = OrderItem.createOrderItem(item, count);

        var order = Order.createOrder(member, orderItem);

        return orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        var order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> searchOrders(OrderSearch orderSearch) {
        return orderRepository.searchOrder(orderSearch);
    }

}
