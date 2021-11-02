package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderV2Repository extends JpaRepository<Order, Long> {
}
