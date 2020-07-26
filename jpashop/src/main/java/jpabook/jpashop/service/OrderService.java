package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) //조회 성능 향상
@RequiredArgsConstructor        //lombok final로 잡힌 변수들 생성자를 자동으로 만들어준다.
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;

	//주문
	@Transactional
	public Long order(Long memberId, Long itemId, int count) {
		//엔티티 조회
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);

		//배송정보 생성
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());

		//주문상품 생성
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

		//주문 생성
		Order order = Order.createOrder(member, delivery, orderItem);

		//원래라면 delivery, orderItem도 persist를 해줘야하지만 order 엔티티의 cascade 속성 때문에 delivery, orderItem도 자동 persist
		orderRepository.save(order);
		return order.getId();
	}

	//취소

	@Transactional
	public void cancelOrder(Long orderId) {
		//주문 엔티티 조회
		Order order = orderRepository.findOne(orderId);
		//주문 취소 -> 비즈니스 로직이 엔티티에 있음
		order.cancel();
	}
	//검 색

	/*public List<Order> findOrders(OrderSearch orderSearch){
		return orderRepository.
	}*/

	//JPA를 사용할때는 엔티티에 비즈니스 로직을 넣는 방법을 많이 사용 이런 패턴을 도메인 모델 패턴이라 한다.
	//일반적으로 sql을 사용할 때 service에서 비즈니스 로직을 처리하는 경우를 트랜잭션 스크립트 패턴이라 한다.
}
