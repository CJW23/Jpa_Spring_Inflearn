package jpabook.jpashop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)		//생성자를 통한 생성 막음
public class Order {

	@Id
	@GeneratedValue
	@Column(name = "order_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY) //많은 주문들은 한사람에 의해서 주문된다.
	@JoinColumn(name = "member_id")    //FK 이름이 member_id
	private Member member;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItemList = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;

	private LocalDateTime orderDate;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;        //주문 상태 (ORDER, CANCEL)

	//연관 관계 메서드
	//이 주문은 한명의 멤버꺼
	//멤버 입장에서는 자기가 주문한 것 중에 하나
	public void setMember(Member member) {
		this.member = member;
		member.getOrders().add(this);
	}

	//하나의 주문에는 여러 아이템이 있을 수 있다.
	//아이템하나는 하나의 주문으로 된다.
	public void addOrderItem(OrderItem orderItem) {
		orderItemList.add(orderItem);
		orderItem.setOrder(this);
	}

	//하나의 주문은 한곳으로 배달된다.
	//하나의 배달은 하나의 주문이다.
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}

	//정적 팩토리 메소드
	//생성 메소드
	public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
		Order order = new Order();
		order.setMember(member);
		order.setDelivery(delivery);
		for (OrderItem orderItem : orderItems) {
			order.addOrderItem(orderItem);
		}
		order.setStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());
		return order;
	}

	//주문 취소
	public void cancel() {
		if (delivery.getStatus() == DeliveryStatus.COMP) {
			//IllegalStateException -> 불법적이거나 부적절한 방식으로 메소드가 호출
			throw new IllegalStateException("이미 배송 완료");
		}

		this.setStatus(OrderStatus.CANCEL);
		//재고 되돌리기
		for (OrderItem orderItem : orderItemList) {
			orderItem.cancel();
		}
	}

	//조회 로직
	public int getTotalPrice() {
        int totalPrice = 0;
	    for(OrderItem orderItem : orderItemList){
            totalPrice += orderItem.getTotalPrice();
        }
	    return totalPrice;
	}
}
