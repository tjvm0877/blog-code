package com.its.spring_data_jpa_pagination.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.its.spring_data_jpa_pagination.domain.Item;

// Spring Data JPA의 쿼리 메서드에 페이징과 정렬 기능을 사용
public interface ItemRepository extends JpaRepository<Item, Long> {

	// Slice로 반환
	Slice<Item> findSliceByPrice(int price, Pageable pageable);

	// Page로 반환
	Page<Item> findPageByPrice(int price, Pageable pageable);
}
