package com.its.book_info.domain.book.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.its.book_info.domain.book.application.BookManageService;
import com.its.book_info.domain.book.application.BookSearchService;
import com.its.book_info.domain.book.dto.BookCreateRequest;
import com.its.book_info.domain.book.dto.BookDetailResponse;
import com.its.book_info.domain.book.dto.BookListResponse;
import com.its.book_info.domain.book.dto.BookResponse;
import com.its.book_info.global.aop.annotation.CacheLogging;
import com.its.book_info.global.aop.annotation.LogExecutionTime;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

	private final BookManageService bookManageService;
	private final BookSearchService bookSearchService;

	// 전체 도서 목록 조회
	@GetMapping
	@LogExecutionTime
	public ResponseEntity<?> getBookList(
		@PageableDefault(size = 10, page = 0, direction = Sort.Direction.DESC) Pageable pageable) {
		BookListResponse response = bookSearchService.getList(pageable);
		return ResponseEntity.ok(response);
	}

	// 저자별 도서 목록 조회
	@GetMapping("/author/{author}")
	@LogExecutionTime
	public ResponseEntity<?> getBookListByAuthor(
		@PathVariable("author") String author,
		@PageableDefault(size = 10, page = 0, direction = Sort.Direction.DESC) Pageable pageable
	) {
		BookListResponse response = bookSearchService.getListByAuthor(author, pageable);
		return ResponseEntity.ok(response);
	}

	// ID로 도서 상세 정보 조회
	@GetMapping("/{id}")
	@LogExecutionTime
	public ResponseEntity<?> getBookInfo(@PathVariable("id") Long id) {
		BookDetailResponse response = bookSearchService.getDetail(id);
		return ResponseEntity.ok(response);
	}

	// IBSN으로 도서 상세 정보 조회
	@GetMapping("/isbn/{isbn}")
	@LogExecutionTime
	public ResponseEntity<?> getBookByIsbn(@PathVariable("isbn") String isbn) {
		BookDetailResponse response = bookSearchService.getDetailByIsbn(isbn);
		return ResponseEntity.ok(response);
	}

	// 도서 등록
	@PostMapping
	@CacheLogging
	public ResponseEntity<?> registerBook(@RequestBody BookCreateRequest request) {
		BookResponse response = bookManageService.register(request);
		return ResponseEntity.ok(response);
	}

	// 도서 삭제
	@DeleteMapping("/{id}")
	@CacheLogging
	public ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
		bookManageService.delete(id);
		return ResponseEntity.ok().build();
	}
}
