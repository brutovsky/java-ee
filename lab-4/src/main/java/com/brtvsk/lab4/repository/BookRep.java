package com.brtvsk.lab4.repository;

import com.brtvsk.lab4.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRep extends JpaRepository<BookEntity, Integer> {

    BookEntity getBookEntityById(final int id);

    BookEntity getBookEntityByIsbn(final String isbn);

    List<BookEntity> getBookEntitiesByTitle(final String title);

    List<BookEntity> getBookEntitiesByAuthor(final String title);

    List<BookEntity> getBookEntitiesByIsbn(final String title);

    boolean existsByIsbn(final String isbn);

}