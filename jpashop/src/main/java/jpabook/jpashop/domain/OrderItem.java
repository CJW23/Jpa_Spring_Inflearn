package jpabook.jpashop.domain;

import javax.persistence.*;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class OrderItem {
	@Id
	@GeneratedValue
	@Column(name = "order_item_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	@ManyToOne(fetch = FetchType.LAZY) //많은 주문들이 하나의orderitem을 포함할 수 있다. 똑같은 상품을 여러명이 구매할 수 있다.
	@JoinColumn(name = "order_id")
	private Order order;

	private int orderPrice;

	private int count;

}
