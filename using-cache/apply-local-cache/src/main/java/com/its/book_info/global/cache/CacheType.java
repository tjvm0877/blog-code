package com.its.book_info.global.cache;

import lombok.Getter;

@Getter
public enum CacheType {
	BOOK_LIST("bookList", 100, 300),            // 5분
	AUTHOR_BOOKS("authorBooks", 100, 300),      // 5분
	BOOK_DETAIL("bookDetail", 500, 1800),       // 30분
	BOOK_DETAIL_BY_ISBN("bookDetailByIsbn", 500, 1800);  // 30분

	private final String cacheName;
	private final int maximumSize;
	private final int expireAfterWrite;

	CacheType(String cacheName, int maximumSize, int expireAfterWrite) {
		this.cacheName = cacheName;
		this.maximumSize = maximumSize;
		this.expireAfterWrite = expireAfterWrite;
	}
}