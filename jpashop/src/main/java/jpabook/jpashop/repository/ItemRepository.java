package jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
	private final EntityManager em;

	//상품 저장
	public void save(Item item) {
		//??? -> 물음표를 쳐놨었네 ㅋㅋㅋㅋ
		//item을 create 할 때는 id를 설정하지 않는다->Generate Value -> 새로운 엔티티
		if (item.getId() == null) {
			em.persist(item);
		} else {		//update에서 id를 set해준다 -> 즉 원래 있던 엔티티를 업데이틑 하는 것이기 때문
			em.merge(item);
		}
	}

	public Item findOne(Long id) {
		return em.find(Item.class, id);
	}

	public List<Item> findAll(){
		return em.createQuery("select i from Item i", Item.class)
			.getResultList();
	}

}
