package com.its.book_info.domain.book.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.its.book_info.domain.book.domain.Book;
import com.its.book_info.domain.book.dto.BookCreateRequest;
import com.its.book_info.domain.book.dto.BookResponse;
import com.its.book_info.domain.book.repository.BookRepository;
import com.its.book_info.global.error.exception.CustomException;
import com.its.book_info.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookManageService {

	private final BookRepository bookRepository;

	@Transactional
	public BookResponse register(BookCreateRequest request) {
		Book book = Book.builder()
			.author(request.author())
			.title(request.title())
			.description(request.description())
			.isbn(request.isbn())
			.price(request.price())
			.build();
		bookRepository.save(book);
		return BookResponse.from(book);
	}

	@Transactional
	public void delete(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(
			() -> new CustomException(ErrorCode.BOOK_NOT_FOUND)
		);
		bookRepository.delete(book);
	}
}
