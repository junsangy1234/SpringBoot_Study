package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderItem;
import jpabook.jpashop.domain.order.OrderStatus;
import jpabook.jpashop.repository.order.query.OrderFlatDto;
import jpabook.jpashop.repository.order.query.OrderItemQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import jpabook.jpashop.service.OrderService;
import jpabook.jpashop.service.query.OrderQueryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    //private final OrderService orderService;
    private final OrderQueryService orderQueryService;
    private final OrderQueryRepository orderQueryRepository;

    //OSIV 켜져 있을때.
//    @GetMapping("/api/v3/orders")
//    public Result orders(){
//        List<Order> orders = orderService.findOrdersWithMemberDelivery();
//        List<OrderDto> result = orders.stream()
//                .map(order -> new OrderDto(order))
//                .toList();
//        return new Result(result.size(), result);
//    }


    @GetMapping("/api/v3/orders")
    public Result ordersV3(){
        // 🟢 컨트롤러는 그저 호출하고 응답할 뿐! 코드가 엄청나게 깔끔해졌습니다.
        List<OrderQueryService.OrderDto> result = orderQueryService.ordersV3();
        return new Result(result.size(), result);
    }

    @GetMapping("/api/v4/orders")
    public Result ordersV4() {
        List<OrderQueryDto> result = orderQueryRepository.findOrderQueryDtos();
        return new Result(result.size(), result);
    }

    @GetMapping("/api/v5/orders")
    public Result ordersV5() {
        List<OrderQueryDto> result = orderQueryRepository.findAllByDto_optimization();
        return new Result(result.size(), result);
    }

    @GetMapping("/api/v6/orders")
    public Result ordersV6() {
        // 1. DB에서 평면 데이터를 쫙 긁어옵니다. (쿼리 1번)
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();

        // 2. 자바 메모리에서 뻥튀기된 데이터를 OrderQueryDto 구조로 다시 그룹핑합니다.
        List<OrderQueryDto> result = flats.stream()
                .collect(Collectors.groupingBy(o -> new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        Collectors.mapping(o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), Collectors.toList())
                )).entrySet().stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(), e.getKey().getAddress(), e.getValue()))
                .toList();

        return new Result(result.size(), result);
    }


    //OSIV 켜져 있을때.
//    @Data
//    static class OrderDto {
//        private Long orderId;
//        private String name;
//        private LocalDateTime orderDate;
//        private OrderStatus orderStatus;
//        private Address address;
//        private List<OrderItemDto> orderItems;
//
//        public OrderDto(Order order){
//            this.orderId = order.getId();
//            this.name = order.getMember().getName();
//            this.orderDate = order.getOrderTime();
//            this.orderStatus = order.getOrderStatus();
//            this.address = order.getDelivery().getAddress();
//            this.orderItems = order.getOrderItems().stream()
//                    .map(orderItem -> new OrderItemDto(orderItem))
//                    .toList();
//        }
//    }
//
//    @Data
//    static class OrderItemDto{
//        private String itemName;
//        private int price;
//        private int count;
//
//        public OrderItemDto(OrderItem orderItem){
//            this.itemName = orderItem.getItem().getName();
//            this.price = orderItem.getOrderPrice();
//            this.count = orderItem.getCount();
//        }
//    }

    @Data @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }
}
