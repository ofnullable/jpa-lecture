package me.ofnullable.jpa;

import lombok.RequiredArgsConstructor;
import me.ofnullable.jpa.item.domain.Book;
import me.ofnullable.jpa.member.domain.Address;
import me.ofnullable.jpa.member.domain.Member;
import me.ofnullable.jpa.order.domain.Order;
import me.ofnullable.jpa.order.domain.OrderItem;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init1();
        initService.init2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void init1() {
            Member member = createMember("UserA", "서울시", "갱냄구", "12345");

            Book book1 = createBook("JPA Book1", 10000, 100);
            Book book2 = createBook("JPA Book2", 11000, 100);

            em.persist(member);
            em.persist(book1);
            em.persist(book2);

            var orderItem1 = OrderItem.createOrderItem(book1, 1);
            var orderItem2 = OrderItem.createOrderItem(book2, 2);

            em.persist(orderItem1);
            em.persist(orderItem2);

            var order = Order.createOrder(member, orderItem1, orderItem2);
            em.persist(order);
        }

        public void init2() {
            Member member = createMember("UserB", "갱기도", "부천시", "54321");

            Book book1 = createBook("SPRING Book1", 20000, 200);
            Book book2 = createBook("SPRING Book2", 32000, 300);

            em.persist(member);
            em.persist(book1);
            em.persist(book2);

            var orderItem1 = OrderItem.createOrderItem(book1, 3);
            var orderItem2 = OrderItem.createOrderItem(book2, 4);

            var order = Order.createOrder(member, orderItem1, orderItem2);
            em.persist(order);
        }

        private Member createMember(String userA, String city, String street, String zipcode) {
            var member = new Member();
            member.setUsername(userA);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        private Book createBook(String name, int price, int stock) {
            var book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStock(stock);
            return book1;
        }

    }

}
