package com.brtvsk.lab4.repository;

import com.brtvsk.lab4.model.Book;
import com.brtvsk.lab4.model.BookEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final EntityManager entityManager;

    @Transactional
    public BookEntity addBookToDB(final Book book) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setIsbn(book.getIsbn());
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setPublicationYear(book.getYear());
        return entityManager.merge(bookEntity);
    }

    @Transactional
    public BookEntity getBookById(final int id) {
        return entityManager.find(BookEntity.class, id);
    }

    @Transactional
    public BookEntity getBookByISBN(final String isbn) {
        return entityManager.createQuery("SELECT b FROM BookEntity b WHERE b.isbn LIKE :query", BookEntity.class)
                .setParameter("query", isbn)
                .getSingleResult();
    }

    @Transactional
    public List<BookEntity> getBooksByTitle(final String title) {
        return entityManager.createQuery("SELECT b FROM BookEntity b WHERE LOWER(b.title) LIKE :query", BookEntity.class)
                .setParameter("query", "%" + title.toLowerCase() + "%")
                .getResultList();
    }

    @Transactional
    public List<BookEntity> getBooksByAuthor(final String author) {
        return entityManager.createQuery("SELECT b FROM BookEntity b WHERE LOWER(b.author) LIKE :query", BookEntity.class)
                .setParameter("query", "%" + author.toLowerCase() + "%")
                .getResultList();
    }

    @Transactional
    public List<BookEntity> getBooksByISBN(final String isbn) {
        return entityManager.createQuery("SELECT b FROM BookEntity b WHERE b.isbn LIKE :query", BookEntity.class)
                .setParameter("query", "%" + isbn + "%")
                .getResultList();
    }

    @Transactional
    public List<BookEntity> getAllBooks() {
        return entityManager.createQuery("FROM BookEntity", BookEntity.class).getResultList();
    }

    @Transactional
    public boolean bookExists(final String isbn) {
        return entityManager
                .createQuery("SELECT b FROM BookEntity b WHERE b.isbn LIKE :query")
                .setParameter("query", "%" + isbn + "%")
                .getSingleResult() != null;
    }

}
