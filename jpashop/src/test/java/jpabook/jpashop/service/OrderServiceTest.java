package jpabook.jpashop.service;

import javax.persistence.EntityManager;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.repository.OrderRepository;

import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

	@Autowired
	EntityManager em;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderRepository orderRepository;

	@Test
	public void 상품주문() throws Exception {
		Member member = new Member();
		member.setName("회원1");
		member.setAddress(new Address("서울", "강가", "123-123"));
		//멤버 집어넣고
		em.persist(member);

		//상품 엔티티
		Book book = new Book();
		book.setName("시골 JPA");
		book.setPrice(10000);
		book.setStockQuantity(10);
		//상품 등록하고
		em.persist(book);

		int orderCount = 2;
		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

		Order getOrder = orderRepository.findOne(orderId);

		//검증
		Assert.assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
		Assert.assertEquals("주문한 상품 종류 수가 정확해야 한다", 1, getOrder.getOrderItemList().size());
		Assert.assertEquals("주무 가격은 가격*수량", 10000*orderCount, getOrder.getTotalPrice());
		Assert.assertEquals("주문 수량만큼 재고가 줄어야함", 8, book.getStockQuantity());
	}

	@Test(expected = IllegalStateException.class)
	public void 주문취소() throws Exception {
		//given
		Member member = new Member();
		member.setName("회원1");
		member.setAddress(new Address("서울", "강가", "123-123"));
		//멤버 집어넣고
		em.persist(member);

		//상품 엔티티
		Book book = new Book();
		book.setName("시골 JPA");
		book.setPrice(10000);
		book.setStockQuantity(10);
		//상품 등록하고
		em.persist(book);

		int orderCount = 2;		//초과 주문
		Long id = orderService.order(member.getId(), book.getId(), orderCount);
		Order getOrder = orderRepository.findOne(id);
		getOrder.getDelivery().setStatus(DeliveryStatus.COMP);

		//when
		orderService.cancelOrder(id);

		//then
		fail("이미 주문 완료이기 떄문에 throw");
		//getOrder.getDelivery().setStatus(DeliveryStatus.COMP);

		//Assert.assertEquals("상태는 CANCEL", OrderStatus.CANCEL, getOrder.getStatus());
		//Assert.assertEquals("상품 개수 원상복구", 10, book.getStockQuantity());

	}

	@Test(expected = NotEnoughStockException.class)
	public void 상품주문_재고수량초과() throws Exception {
		Member member = new Member();
		member.setName("회원1");
		member.setAddress(new Address("서울", "강가", "123-123"));
		//멤버 집어넣고
		em.persist(member);

		//상품 엔티티
		Book book = new Book();
		book.setName("시골 JPA");
		book.setPrice(10000);
		book.setStockQuantity(10);
		//상품 등록하고
		em.persist(book);

		int orderCount = 11;		//초과 주문

		orderService.order(member.getId(), book.getId(), orderCount);

		fail("예외가 발생해야함 여기까지 도달하면 안됨");

	}
}