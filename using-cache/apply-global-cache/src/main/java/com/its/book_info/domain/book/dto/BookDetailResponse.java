package com.its.book_info.domain.book.dto;

import com.its.book_info.domain.book.domain.Book;

public record BookDetailResponse(
	Long id,
	String author,
	String title,
	String description,
	String isbn,
	int price
) {
	public static BookDetailResponse from(Book book) {
		return new BookDetailResponse(
			book.getId(),
			book.getAuthor(),
			book.getTitle(),
			book.getDescription(),
			book.getIsbn(),
			book.getPrice()
		);
	}
}
