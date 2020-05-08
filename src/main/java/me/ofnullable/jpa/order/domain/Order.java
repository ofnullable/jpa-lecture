package me.ofnullable.jpa.order.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.ofnullable.jpa.delivery.domain.Delivery;
import me.ofnullable.jpa.delivery.domain.DeliveryStatus;
import me.ofnullable.jpa.member.domain.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDateTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void addOrderItems(OrderItem ...orderItems) {
        var itemList = Arrays.stream(orderItems)
                .collect(Collectors.toList());

        this.orderItems.addAll(itemList);
        itemList.forEach(orderItem -> orderItem.setOrder(this));
    }

    public static Order createOrder(Member member, OrderItem ...orderItems) {
        var order = new Order();
        order.setMember(member);
        order.addOrderItems(orderItems);
        order.setOrderDateTime(LocalDateTime.now());

        var delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        order.setDelivery(delivery);
        return order;
    }

    public void cancel() {
        if (this.delivery.getStatus() == DeliveryStatus.COMPLETE) {
            throw new IllegalStateException("이미 배송완료된 주문은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        orderItems
                .forEach(OrderItem::cancel);
    }

    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }

}
