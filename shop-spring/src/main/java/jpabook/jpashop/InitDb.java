package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.order.Delivery;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;

/**
 * 총 2개의 주문 데이터를 생성합니다.
 * userA: JPA1 BOOK, JPA2 BOOK 주문
 * userB: SPRING1 BOOK, SPRING2 BOOK 주문
 */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct //스트링빈이 다 올라가고 직후에 메서드 자동 실행
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = Member.createMember("userA",new Address("서울", "1", "1111"));
            em.persist(member);

            Book book1 = Book.createBook("JPA1 BOOK", 10000, 100, "", "");
            em.persist(book1);

            Book book2 = Book.createBook("JPA2 BOOK", 20000, 100, "", "");
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = Delivery.createDelivery(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = Member.createMember("userB",new Address("진주", "2", "2222"));
            em.persist(member);

            Book book1 = Book.createBook("SPRING1 BOOK", 20000, 200, "", "");
            em.persist(book1);

            Book book2 = Book.createBook("SPRING2 BOOK", 40000, 300, "", "");
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = Delivery.createDelivery(member.getAddress());

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
    }
}