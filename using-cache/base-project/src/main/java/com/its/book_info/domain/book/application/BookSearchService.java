package com.its.book_info.domain.book.application;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.its.book_info.domain.book.domain.Book;
import com.its.book_info.domain.book.dto.BookDetailResponse;
import com.its.book_info.domain.book.dto.BookListResponse;
import com.its.book_info.domain.book.dto.BookResponse;
import com.its.book_info.domain.book.repository.BookRepository;
import com.its.book_info.global.error.exception.CustomException;
import com.its.book_info.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookSearchService {

	private final BookRepository bookRepository;

	public BookListResponse getList(Pageable pageable) {
		Slice<Book> books = bookRepository.findAllByOrderById(pageable);
		List<BookResponse> list = books.stream()
			.map((BookResponse::from))
			.toList();
		return new BookListResponse(list, books.hasNext());
	}

	public BookListResponse getListByAuthor(String author, Pageable pageable) {
		Slice<Book> books = bookRepository.findAllByAuthor(author, pageable);
		List<BookResponse> list = books.stream()
			.map((BookResponse::from))
			.toList();
		return new BookListResponse(list, books.hasNext());
	}

	public BookDetailResponse getDetail(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(
			() -> new CustomException(ErrorCode.BOOK_NOT_FOUND)
		);
		return BookDetailResponse.from(book);
	}

	public BookDetailResponse getDetailByIsbn(String isbn) {
		Book book = bookRepository.findByIsbn(isbn).orElseThrow(
			() -> new CustomException(ErrorCode.BOOK_NOT_FOUND)
		);
		return BookDetailResponse.from(book);
	}
}
