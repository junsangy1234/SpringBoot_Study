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

    private String category;

    @OneToMany(mappedBy = "category")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    //자기참조차례
    //부모: 여러 자식 가짐,  자식: 한 부모 가짐
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

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
