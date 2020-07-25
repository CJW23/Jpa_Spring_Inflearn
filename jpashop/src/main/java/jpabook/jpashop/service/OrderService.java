package jpabook.jpashop.service;

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
	public Long order(Long memberId, Long itemId, int count){
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

	//검색
}
