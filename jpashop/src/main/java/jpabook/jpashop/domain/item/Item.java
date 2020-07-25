package jpabook.jpashop.domain.item;

import javax.persistence.*;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)    //싱글테이블에 모든 아이템을 다 때려박겠다.
@DiscriminatorColumn(name = "dtype")
public abstract class Item {
	@Id
	@GeneratedValue
	@Column(name = "item_id")
	private Long id;

	private String name;
	private int price;
	private int stockQuantity;

	@ManyToMany(mappedBy = "items")
	private List<Category> categories = new ArrayList<>();

	public void addStock(int quantity) {
		this.stockQuantity += quantity;
	}

	public void removeStock(int quantity) {
		int rstStock = this.stockQuantity - quantity;
		if(rstStock < 0){
			throw new NotEnoughStockException("재고 수량 부족");
		}
		this.stockQuantity = rstStock;
	}
}
