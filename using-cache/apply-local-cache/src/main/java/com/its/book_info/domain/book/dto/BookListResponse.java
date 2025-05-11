package com.its.book_info.domain.book.dto;

import java.util.List;

public record BookListResponse(
	List<BookResponse> data,
	boolean hasNext
) {
}
