package me.ofnullable.jpa.item.domain;

import lombok.Getter;
import lombok.Setter;
import me.ofnullable.jpa.exception.NotEnoughStockException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stock;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public void increaseStock(int quantity) {
        this.stock += quantity;
    }

    public void reduceStock(int quantity) {
        if (this.stock - quantity < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stock -= quantity;
    }

}
