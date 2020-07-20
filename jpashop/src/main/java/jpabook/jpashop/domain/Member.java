package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {
	@Id
	@GeneratedValue
	@Column(name = "member_id")	//컬럼 이름 지정, 지정 안할시 변수(id)값으로 지정
	private Long id;

	private String name;

	@Embedded
	private Address address;

	//order테이블의 member에 의해서 mapping 된 것이다.
	//member에 의해서 맵핑된 거울일 뿐이다.(읽기 전용)
	@OneToMany(mappedBy = "member")	//한사람이 여러 주문을 할 수 있다.
	private List<Order> orders = new ArrayList<>();


}
