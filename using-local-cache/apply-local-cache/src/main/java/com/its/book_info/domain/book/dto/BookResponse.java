package com.its.book_info.domain.book.dto;

import com.its.book_info.domain.book.domain.Book;

public record BookResponse(
	Long id,
	String title,
	String author
) {
	public static BookResponse from(Book book) {
		return new BookResponse(
			book.getId(),
			book.getTitle(),
			book.getAuthor());
	}
}
