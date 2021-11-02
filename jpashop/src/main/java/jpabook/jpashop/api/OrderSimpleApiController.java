package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.OrderV2Repository;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderV2Repository orderRepository;

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrder> ordersV1() {
        List<Order> orderArrayList = orderRepository.findAll();
        return orderArrayList.stream()
                .map(SimpleOrder::of).collect(Collectors.toList());
    }

    @Data
    @Builder
    static class SimpleOrder {
        private String name;
        private Address address;
        private

        static SimpleOrder of(Order o) {
            return SimpleOrder.builder()
                    .address(o.getMember().getAddress())
                    .name(o.getMember().getName()).build();
        }
    }
}
