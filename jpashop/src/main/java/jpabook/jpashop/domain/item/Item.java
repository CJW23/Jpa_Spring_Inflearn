package jpabook.jpashop.domain.item;

import javax.persistence.*;

import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)	//싱글테이블에 모든 아이템을 다 때려박겠다.
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
	private List<Category> categories= new ArrayList<>();


}
