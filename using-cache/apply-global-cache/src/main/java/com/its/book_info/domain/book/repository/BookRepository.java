package com.its.book_info.domain.book.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.its.book_info.domain.book.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	Slice<Book> findAllByOrderById(Pageable pageable);

	Slice<Book> findAllByAuthor(String author, Pageable pageable);

	Optional<Book> findByIsbn(String isbn);
}
