package jpabook.jpashop.domain.category;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    //아이템 하나가 여러 카테고리 가질수도 있고. 카테고리하나가 여러 아이템 가지기도 가능.
    //결론 다대다 관계. 근데 다대다로 바로 구현은 하지마. 중간 테이블을 직접 구현.
    @OneToMany(mappedBy = "category")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    //카테고리 자기참조 필요. 책 -> 프로그래밍 -> 자바 이렇게 가능
    //부모. 자식 여러명 가능
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //자식. 부모 한명
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    public void updateParent(Category parent){
        this.parent = parent;
    }

    public void addChild(Category child){
        this.child.add(child);
        child.updateParent(this);
    }

}
