package com.its.book_info.domain.book.application;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
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

	private final CacheManager cacheManager;
	private final BookRepository bookRepository;

	@Caching(evict = { // 여러 캐시 작업을 하나의 메서드에 그룹화
		@CacheEvict(value = "bookList", allEntries = true), // 목록 캐시 전체 무효화
		@CacheEvict(value = "authorBooks", allEntries = true), // 등록된 저자 관련 캐시 전체 무효화
		@CacheEvict(value = "bookDetail", key = "#result.id"), // 등록된 도서 상세 캐시 무효화
		@CacheEvict(value = "bookDetailByIsbn", key = "#request.isbn") // ISBN 캐시 무효화
	})
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

	@Caching(evict = { // 여러 캐시 작업을 하나의 메서드에 그룹화
		@CacheEvict(value = "bookList", allEntries = true), // 목록 캐시 전체 무효화
		@CacheEvict(value = "authorBooks", allEntries = true), // 도서의 저자 관련 캐시 무효화
		@CacheEvict(value = "bookDetail", key = "#id"), // 삭제된 도서 상세 캐시 무효화
	})
	@Transactional
	public void delete(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(
			() -> new CustomException(ErrorCode.BOOK_NOT_FOUND)
		);

		// ISBN 캐시 직접 무효화
		String isbn = book.getIsbn();
		if (isbn != null) {
			cacheManager.getCache("bookDetailByIsbn").evict(isbn);
		}

		bookRepository.delete(book);
	}
}
