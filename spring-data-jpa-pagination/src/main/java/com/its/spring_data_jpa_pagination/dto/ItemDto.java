package com.its.spring_data_jpa_pagination.dto;

import com.its.spring_data_jpa_pagination.domain.Item;

public record ItemDto(
	Long id,
	String name,
	int price
) {
	public static ItemDto from(Item item) {
		return new ItemDto(item.getId(), item.getName(), item.getPrice());
	}
}
