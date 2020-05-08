package me.ofnullable.jpa.order.service;

import me.ofnullable.jpa.exception.NotEnoughStockException;
import me.ofnullable.jpa.item.domain.Book;
import me.ofnullable.jpa.item.domain.Item;
import me.ofnullable.jpa.member.domain.Address;
import me.ofnullable.jpa.member.domain.Member;
import me.ofnullable.jpa.order.domain.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;

    @Test
    @DisplayName("상품주문")
    void place_order() throws Exception {
        // given
        var member = createMember();
        var book = createBook("시골 JPA", 12000, 10);

        var orderCount = 2;

        // when
        var order = orderService.placeOrder(member.getId(), book.getId(), orderCount);

        // then
        var getOrder = orderService.findOne(order.getId());

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER.");
        assertEquals(1, order.getOrderItems().size(), "주문한 상품 종류가 정확해야 한다.");
        assertEquals(24000, getOrder.getTotalPrice(), "주문가격은 가격 * 수량 이다.");
        assertEquals(8, book.getStock(), "주문수량 만큼 재고가 줄어야 한다.");
    }

    @Test
    @DisplayName("상품주문 - 재고수량 초과")
    void place_order_more_than_stock() throws Exception {
        // given
        Member member = createMember();
        Item book = createBook("서울 JPA", 14000, 5);

        int orderCount = 7;

        // when
        var expected = catchThrowable(() -> orderService.placeOrder(member.getId(), book.getId(), orderCount));

        // then
        assertEquals(NotEnoughStockException.class, expected.getClass());
    }

    @Test
    @DisplayName("주문취소")
    void cancel_order() throws Exception {
        // given
        var member = createMember();
        var book = createBook("시골 JPA", 10000, 10);

        var orderCount = 2;

        var order = orderService.placeOrder(member.getId(), book.getId(), orderCount);
        
        // when
        orderService.cancelOrder(order.getId());

        // then
        var getOrder = orderService.findOne(order.getId());

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문취소 시 상태는 CANCEL");
        assertEquals(10, book.getStock(), "주문이 취소된 상품은 재고가 회복 되어야 한다.");
    }

    private Item createBook(String name, int price, int stock) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStock(stock);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        var member = new Member();
        member.setUsername("Lee");
        member.setAddress(new Address("서울", "테헤란로", "12345"));
        em.persist(member);
        return member;
    }

}