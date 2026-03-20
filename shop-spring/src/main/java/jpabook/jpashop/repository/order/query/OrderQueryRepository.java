package jpabook.jpashop.repository.order.query;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.order.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;

    /**
     * V6:한 번의 쿼리로 모든 데이터를 조인해서 가져온다. (Flat 데이터)
     */
    public List<OrderFlatDto> findAllByDto_flat() {
        return em.createQuery(
                        "select new jpabook.jpashop.repository.order.query.OrderFlatDto(o.id, m.name, o.orderTime, o.orderStatus, d.address, i.name, oi.orderPrice, oi.count)" +
                                " from Order o" +
                                " join o.member m" +
                                " join o.delivery d" +
                                " join o.orderItems oi" +
                                " join oi.item i", OrderFlatDto.class)
                .getResultList();
    }

    /**
     * V5: 컬렉션 쿼리 최적화 (IN 쿼리와 Map 사용)
     */
    public List<OrderQueryDto> findAllByDto_optimization() {
        // 1. 루트 조회 (ToOne 관계는 한 번에! - 쿼리 1번)
        List<OrderQueryDto> result = findOrders();

        // 2. 주문 아이디(orderId) 리스트만 추출
        List<Long> orderIds = result.stream()
                .map(o -> o.getOrderId())
                .toList();

        // 3. IN 절을 사용해서 orderId 리스트에 해당하는 모든 OrderItem을 한 번에 조회 (쿼리 1번)
        List<OrderItemQueryDto> orderItems = em.createQuery(
                        "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        // 4. 조회한 OrderItem들을 orderId를 기준으로 Map에 그룹화 (메모리 매칭을 위해)
        // 결과: { 1: [itemA, itemB], 2: [itemC, itemD] }
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));

        // 5. 메모리(Map)에서 찾아서 세팅해 준다 (쿼리가 안 나감!)
        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    public List<OrderQueryDto> findOrderQueryDtos(){
        List<OrderQueryDto> result = findOrders();
        result.forEach(o-> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return result;
    }

    //xToOne 관계만 먼저 Dto로 조회.
    public List<OrderQueryDto> findOrders(){
        return em.createQuery(
                "SELECT NEW jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderTime, o.orderStatus, d.address)" +
                " FROM Order o" +
                " JOIN o.member m" +
                " JOIN o.delivery d", OrderQueryDto.class).getResultList();
    }

    //xToMany 관계인 OrderItem은 별도로 Dto 조회.
    public List<OrderItemQueryDto> findOrderItems(Long orderId){
        return em.createQuery(
                "SELECT NEW jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " FROM OrderItem oi" +
                        " JOIN oi.item i" +
                        " WHERE oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }
}
