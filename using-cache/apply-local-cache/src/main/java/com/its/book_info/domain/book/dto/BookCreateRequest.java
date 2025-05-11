package com.its.book_info.domain.book.dto;

public record BookCreateRequest(
	String author,
	String title,
	String description,
	String isbn,
	int price
) {
}
