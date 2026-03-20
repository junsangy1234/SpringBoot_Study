package jpabook.jpashop.domain.order;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.DeliveryStatus;
import lombok.Getter;

@Entity
@Getter
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Order order;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    protected void setOrder(Order order){
        this.order = order;
    }

    public static Delivery createDelivery(Address address){
        Delivery delivery = new Delivery();
        delivery.address = address;
        delivery.status = DeliveryStatus.READY;

        return delivery;
    }
}
