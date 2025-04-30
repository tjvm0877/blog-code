package com.its.spring_data_jpa_pagination;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import com.its.spring_data_jpa_pagination.domain.Item;
import com.its.spring_data_jpa_pagination.repository.ItemRepository;

@SpringBootTest
class SliceAndPageTest {

	@Autowired
	ItemRepository itemRepository;

	@Test
	void getSlice() {
		// Item 저장
		for (int i = 0; i < 30; i++) {
			Item item = new Item("상품" + i, 1000);
			itemRepository.save(item);
		}

		// PageRequest 생성
		PageRequest pageRequest = PageRequest.of(0, 5);

		// 조회
		Slice<Item> itemSlice = itemRepository.findSliceByPrice(1000, pageRequest);

		// 페이지 번호
		System.out.println("itemSlice.getNumber() = " + itemSlice.getNumber());

		// 페이지 크기
		System.out.println("itemSlice.getSize() = " + itemSlice.getSize());

		// 첫번째 페이지 여부
		System.out.println("itemSlice.isFirst() = " + itemSlice.isFirst());

		// 마지막 페이지 여부
		System.out.println("itemSlice.isLast() = " + itemSlice.isLast());

		// 다음 페이지 존재 여부
		System.out.println("itemSlice.hasNext() = " + itemSlice.hasNext());

		// 조회된 데이터 리스트 출력
		for (Item item : itemSlice.getContent()) {
			System.out.println(item.toString());
		}
	}

	@Test
	void getPage() {
		// Item 저장
		for (int i = 0; i < 30; i++) {
			Item item = new Item("상품" + i, 1000);
			itemRepository.save(item);
		}

		// PageRequest 생성
		PageRequest pageRequest = PageRequest.of(0, 5);

		// 조회
		Page<Item> itemPage = itemRepository.findPageByPrice(1000, pageRequest);

		// 페이지 번호
		System.out.println("itemPage.getNumber() = " + itemPage.getNumber());

		// 페이지 크기
		System.out.println("itemPage.getSize() = " + itemPage.getSize());

		// 첫번째 페이지 여부
		System.out.println("itemPage.isFirst() = " + itemPage.isFirst());

		// 마지막 페이지 여부
		System.out.println("itemPage.isLast() = " + itemPage.isLast());

		// 다음 페이지 존재 여부
		System.out.println("itemPage.hasNext() = " + itemPage.hasNext());

		// 전체 페이지 번호
		System.out.println("itemPage.getTotalPages = " + itemPage.getTotalPages());

		// 전체 데이터 갯수
		System.out.println("itemPage.getTotalElements = " + itemPage.getTotalElements());

		// 페이지 데이터 리스트 출력
		for (Item item : itemPage.getContent()) {
			System.out.println(item.toString());
		}
	}
}