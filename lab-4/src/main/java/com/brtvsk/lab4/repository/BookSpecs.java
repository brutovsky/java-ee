package com.brtvsk.lab4.repository;

import com.brtvsk.lab4.model.BookEntity;
import org.springframework.data.jpa.domain.Specification;


public class BookSpecs {
    public static Specification<BookEntity> getBooksByTitleSpec(final String title) {
        return (book, query, criteriaBuilder) -> {
            return criteriaBuilder.like(criteriaBuilder.lower(book.get("title")), "%" + title.toLowerCase() + "%");
        };
    }

    public static Specification<BookEntity> getBooksByAuthorSpec(final String author) {
        return (book, query, criteriaBuilder) -> {
            return criteriaBuilder.like(criteriaBuilder.lower(book.get("author")), "%" + author.toLowerCase() + "%");
        };
    }

    public static Specification<BookEntity> getBooksByIsbnSpec(final String isbn) {
        return (book, query, criteriaBuilder) -> {
            return criteriaBuilder.like(criteriaBuilder.lower(book.get("isbn")), "%" + isbn.toLowerCase() + "%");
        };
    }
}
