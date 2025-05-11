package com.its.book_info.domain.book.api;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.book_info.domain.book.domain.Book;
import com.its.book_info.domain.book.dto.BookCreateRequest;
import com.its.book_info.domain.book.repository.BookRepository;
import com.its.book_info.global.error.exception.ErrorCode;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	BookRepository bookRepository;

	@BeforeEach
	void setUp() {
		bookRepository.deleteAll();

		List<Book> bookList = new ArrayList<>();
		for (int i = 1; i < 20; i++) {
			Book book = Book.builder()
				.title("테스트 도서 " + i)
				.author("테스트 작가 " + i)
				.description("설명")
				.isbn("1" + i)
				.price(10000)
				.build();
			bookList.add(book);
		}
		bookRepository.saveAll(bookList);
	}

	@Test
	@DisplayName("도서 목록 조회")
	void getBookList() throws Exception {
		// given
		int page = 0;
		int size = 10;

		// given
		ResultActions resultActions = requestGetBookList(page, size);

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data", hasSize(size)));
	}

	@Test
	@DisplayName("저자별 목록 조회를 할 수 있다.")
	void getBookListByAuthor() throws Exception {
		// given
		String author = "테스트 작가 1";
		int page = 0;
		int size = 10;

		// when
		ResultActions resultActions = requestGetBookListByAuthor(author, page, size);

		// then
		resultActions.andExpect(status().isOk());
	}

	@Test
	@DisplayName("도서 ID로 상세 정보를 조회할 수 있다.")
	void getBookInfo() throws Exception {
		// given
		Long bookId = bookRepository.findAll().getFirst().getId();

		// when
		ResultActions resultActions = requestGetBookInfo(bookId);

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(bookId));
	}

	@Test
	@DisplayName("존재하지 않는 도서 ID로 조회시 적절한 예외가 발생한다.")
	void getBookInfo_notFound() throws Exception {
		// given
		Long nonExistentBookId = 9999L;

		// when
		ResultActions resultActions = requestGetBookInfo(nonExistentBookId);

		// then
		resultActions
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value(ErrorCode.BOOK_NOT_FOUND.getMessage()));
	}

	@Test
	@DisplayName("ISBN을 통해 상세 정보를 조회할 수 있다.")
	void getBookByIsbn() throws Exception {
		// given
		String isbn = bookRepository.findAll().getFirst().getIsbn();

		// when
		ResultActions resultActions = requestGetBookByIsbn(isbn);

		// then
		resultActions.andExpect(status().isOk());
	}

	@Test
	@DisplayName("존재하지 않는 ISBN으로 조회 시 적절한 예외가 발생한다.")
	void getBookByIsbn_notFound() throws Exception {
		// given
		String wrongIsbn = "wrongIsbn";

		// when
		ResultActions resultActions = requestGetBookByIsbn(wrongIsbn);

		// then
		resultActions
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value(ErrorCode.BOOK_NOT_FOUND.getMessage()));
	}

	@Test
	@DisplayName("새 도서를 등록할 수 있다.")
	void registerBook() throws Exception {
		// given
		BookCreateRequest request = new BookCreateRequest("테스트 작가", "테스트 도서", "설명", "12341234", 10000);

		// when
		ResultActions resultActions = requestRegisterBook(request);

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.author").value(request.author()))
			.andExpect(jsonPath("$.title").value(request.title()));
	}

	private ResultActions requestGetBookList(int page, int size) throws Exception {
		return mockMvc.perform(get("/books")
			.param("page", String.valueOf(page))
			.param("size", String.valueOf(size))
		);
	}

	private ResultActions requestGetBookListByAuthor(String author, int page, int size) throws Exception {
		return mockMvc.perform(get("/books/author/{author}", author)
			.param("page", String.valueOf(page))
			.param("size", String.valueOf(size)));
	}

	private ResultActions requestGetBookInfo(Long bookId) throws Exception {
		return mockMvc.perform(get("/books/{id}", bookId));
	}

	private ResultActions requestGetBookByIsbn(String isbn) throws Exception {
		return mockMvc.perform(get("/books/isbn/{isbn}", isbn));
	}

	private ResultActions requestRegisterBook(BookCreateRequest request) throws Exception {
		return mockMvc.perform(post("/books")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));
	}

}