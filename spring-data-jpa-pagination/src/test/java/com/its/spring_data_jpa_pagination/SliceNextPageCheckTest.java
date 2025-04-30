package com.its.spring_data_jpa_pagination;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import com.its.spring_data_jpa_pagination.domain.Item;
import com.its.spring_data_jpa_pagination.dto.ItemDto;
import com.its.spring_data_jpa_pagination.repository.ItemRepository;

@SpringBootTest
public class SliceNextPageCheckTest {

	@Autowired
	ItemRepository itemRepository;

	@Test
	void test() {
		// Item 저장
		for (int i = 0; i < 30; i++) {
			Item item = new Item("상품" + i, 1000);
			itemRepository.save(item);
		}

		// 조회
		PageRequest pageRequest = PageRequest.of(0, 5);
		Slice<Item> itemSlice = itemRepository.findSliceByPrice(1000, pageRequest);
	}
}
