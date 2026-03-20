package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = createMember();
        Book book = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.save(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId).get();
        assertThat(getOrder.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(getOrder.getOrderItems().get(0).getTotalPrice()).isEqualTo(10000*orderCount);
        assertThat(book.getStockQuantity()).isEqualTo(8);
    }

    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = createMember();
        Book book = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.save(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancel(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId).get();
        assertThat(getOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);
    }

    @Test
    public void 상품주문_재고초과() throws  Exception{
        //given
        Member member = createMember();
        Book book = createBook("시골 JPA", 10000, 10);
        int orderCount = 11;

        //when & then
        assertThatThrownBy(() -> orderService.save(member.getId(), book.getId(), orderCount))
                .isInstanceOf(NotEnoughStockException.class);
    }


    // ========================================== //
    // 테스트용 헬퍼 메서드 (반복되는 코드를 줄여줍니다)
    // ========================================== //
    private Member createMember() {
        Member member = Member.createMember("회원 1", new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = Book.createBook(name,price, stockQuantity,"test", "test");
        em.persist(book);
        return book;
    }
}