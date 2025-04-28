package com.its.transactional;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceTest {

	@Autowired
	OrderService orderService;

	@Autowired
	OrderRepository orderRepository;

	@Test
	void success() throws NotEnoughMoneyException {
		//given
		Order order = new Order();
		order.setUsername("정상");

		//when
		orderService.order(order);

		//then
		Order findOrder = orderRepository.findById(order.getId()).get();
		assertThat(findOrder.getPayStatus()).isEqualTo("완료");
	}

	@Test
	void whenUnCheckedExcaption() {
		//given
		Order order = new Order();
		order.setUsername("예외");

		//when, then
		assertThatThrownBy(() -> orderService.order(order))
			.isInstanceOf(RuntimeException.class);

		// 롤백되었으므로 데이터가 없어야 한다.
		Optional<Order> orderOptional = orderRepository.findById(order.getId());
		assertThat(orderOptional.isEmpty()).isTrue();
	}

	@Test
	void whenCheckedExcaption() {
		//given
		Order order = new Order();
		order.setUsername("잔고부족");

		//then
		assertThatThrownBy(() -> orderService.order(order))
			.isInstanceOf(NotEnoughMoneyException.class)
			.hasMessage("잔고가 부족합니다");
		Order findOrder = orderRepository.findById(order.getId()).get();
		assertThat(findOrder.getPayStatus()).isEqualTo("대기");
	}
}