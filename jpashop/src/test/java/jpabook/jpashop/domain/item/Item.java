package jpabook.jpashop.domain.item;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Getter;
import lombok.Setter;

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

}
