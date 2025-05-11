package com.its.book_info.domain.book.application;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.its.book_info.domain.book.domain.Book;
import com.its.book_info.domain.book.dto.BookDetailResponse;
import com.its.book_info.domain.book.dto.BookListResponse;
import com.its.book_info.domain.book.dto.BookResponse;
import com.its.book_info.domain.book.repository.BookRepository;
import com.its.book_info.global.aop.annotation.CacheLogging;
import com.its.book_info.global.error.exception.CustomException;
import com.its.book_info.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookSearchService {

	private final BookRepository bookRepository;

	// 페이지 번호와 크기를 키로 사용하여 결과를 캐시
	@Cacheable(value = "bookList", key = "#pageable.pageNumber + '_' + #pageable.pageSize")
	public BookListResponse getList(Pageable pageable) {
		Slice<Book> books = bookRepository.findAllByOrderById(pageable);
		List<BookResponse> list = books.stream()
			.map((BookResponse::from))
			.toList();
		return new BookListResponse(list, books.hasNext());
	}

	// author와 페이지 번호, 크기 정보를 키로 사용하여 결과를 캐시
	@Cacheable(value = "authorBooks", key = "#author + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
	public BookListResponse getListByAuthor(String author, Pageable pageable) {
		Slice<Book> books = bookRepository.findAllByAuthor(author, pageable);
		List<BookResponse> list = books.stream()
			.map((BookResponse::from))
			.toList();
		return new BookListResponse(list, books.hasNext());
	}

	// 도서 ID를 키로 사용하여 결과를 캐시
	@Cacheable(value = "bookDetail", key = "#id")
	public BookDetailResponse getDetail(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(
			() -> new CustomException(ErrorCode.BOOK_NOT_FOUND)
		);
		return BookDetailResponse.from(book);
	}

	// ISBN을 키로 사용하여 결과를 캐시
	@Cacheable(value = "bookDetailByIsbn", key = "#isbn")
	public BookDetailResponse getDetailByIsbn(String isbn) {
		Book book = bookRepository.findByIsbn(isbn).orElseThrow(
			() -> new CustomException(ErrorCode.BOOK_NOT_FOUND)
		);
		return BookDetailResponse.from(book);
	}
}