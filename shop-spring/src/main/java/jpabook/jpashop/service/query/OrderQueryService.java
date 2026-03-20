package jpabook.jpashop.service.query;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderItem;
import jpabook.jpashop.domain.order.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
// 🟢 핵심! 여기서 트랜잭션을 열어줍니다. (읽기 전용이므로 성능 최적화)
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepository;

    // 🟢 컨트롤러에 있던 로직을 그대로 가져옵니다.
    public List<OrderDto> ordersV3() {
        // 1. 엔티티 조회
        List<Order> orders = orderRepository.findAllWithMemberDelivery();

        // 2. DTO로 변환 (이 순간 트랜잭션이 살아있으므로 지연 로딩이 완벽하게 동작합니다!)
        return orders.stream()
                .map(order -> new OrderDto(order))
                .toList();
    }

    // 🟢 컨트롤러에 있던 DTO들도 서비스 쪽으로 가져옵니다. (혹은 별도 패키지로 분리해도 됨)
    @Data
    public static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order){
            this.orderId = order.getId();
            this.name = order.getMember().getName(); // 지연 로딩 발생 (성공!)
            this.orderDate = order.getOrderTime();
            this.orderStatus = order.getOrderStatus();
            this.address = order.getDelivery().getAddress(); // 지연 로딩 발생 (성공!)
            this.orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem)) // 지연 로딩 발생 (성공!)
                    .toList();
        }
    }

    @Data
    public static class OrderItemDto{
        private String itemName;
        private int price;
        private int count;

        public OrderItemDto(OrderItem orderItem){
            this.itemName = orderItem.getItem().getName(); // 지연 로딩 발생 (성공!)
            this.price = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }
}