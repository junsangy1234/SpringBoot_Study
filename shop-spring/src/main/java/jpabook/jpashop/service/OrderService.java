package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.order.Delivery;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.dto.OrderRequestDto;
import jpabook.jpashop.dto.OrderSearch;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(Long memberId, Long itemId, int count){
        Member member = memberRepository.findOne(memberId).get();
        Item item = itemRepository.findOne(itemId).get();

        Delivery delivery = Delivery.createDelivery(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public Long multiOrderSave(Long memberId, List<OrderRequestDto> requestItems){
        Member member = memberRepository.findOne(memberId).get();
        Delivery delivery = Delivery.createDelivery(member.getAddress());
        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderRequestDto dto : requestItems) {
            Item item = itemRepository.findOne(dto.getItemId()).get();
            OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), dto.getCount());

            orderItems.add(orderItem);
        }

        Order order = Order.createOrder(member,delivery,orderItems);
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancel(Long orderId){
        Order order = orderRepository.findOne(orderId).get();

        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }
}
