package jpabook.jpashop.domain.order;

import jakarta.persistence.*;
import jpabook.jpashop.domain.DeliveryStatus;
import jpabook.jpashop.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id @GeneratedValue
    @Column(name ="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime orderTime;

    private void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }
    private void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
    private void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public static Order createOrder(Member member, Delivery delivery,List<OrderItem> orderItems){
        Order order = new Order();

        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem item : orderItems){
            order.addOrderItem(item);
        }
        order.orderTime = LocalDateTime.now();
        order.orderStatus = OrderStatus.ORDER;

        return order;
    }

    public static Order createOrder(Member member, Delivery delivery,OrderItem... orderItems){
        return createOrder(member, delivery, List.of(orderItems));
    }

    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalArgumentException("이미 배송이 도착하였습니다.");
        }

        this.orderStatus = OrderStatus.CANCEL;

        for(OrderItem item : orderItems){
            item.cancel();
        }
    }

}
