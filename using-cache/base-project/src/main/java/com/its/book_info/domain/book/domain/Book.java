package com.its.book_info.domain.book.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "author", nullable = false)
	private String author;

	@Column(name = "title", nullable = false)
	private String title;

	@Lob
	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "isbn", nullable = false)
	private String isbn;

	@Column(name = "price", nullable = false)
	private int price;

	@CreatedDate
	@Column(name = "create_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "update_at", nullable = false)
	private LocalDateTime updateAt;

	@Builder
	public Book(String author, String title, String description, String isbn, int price) {
		this.author = author;
		this.title = title;
		this.description = description;
		this.isbn = isbn;
		this.price = price;
	}
}
