package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.order.Order;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @Embedded
    private Address address;

    public static Member createMember(String name, Address address){
        Member member = new Member();
        member.name = name;
        member.address = address;
        return member;
    }

    public void changeName(String newName){
        if(newName == null || newName.trim().isEmpty()){
            throw new IllegalArgumentException("이름은 비어 있을수 없습니다.");
        }
        this.name = newName;
    }
}
