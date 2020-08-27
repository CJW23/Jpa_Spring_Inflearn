package jpabook.jpashop.service;

import java.util.List;

import jpabook.jpashop.domain.item.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) //조회 성능 향상
@RequiredArgsConstructor        //lombok final로 잡힌 변수들 생성자를 자동으로 만들어준다.
public class ItemService {

	private final ItemRepository itemRepository;

	@Transactional
	public void updateItem(Long itemId, Book param) {
		Item findItem = itemRepository.findOne(itemId);
		findItem.setPrice(param.getPrice());
	}
	@Transactional		//readonly면 저장이 안된다.
	public void saveItem(Item item) {
		 itemRepository.save(item);
	}

	public List<Item> findItems(){
		return itemRepository.findAll();
	}

	public Item findOne(Long id){
		return itemRepository.findOne(id);
	}
}
