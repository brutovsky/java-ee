package com.brtvsk.lab4.repository;

import com.brtvsk.lab4.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BookRep extends JpaRepository<BookEntity, Integer>, JpaSpecificationExecutor<BookEntity> {

    BookEntity getBookEntityById(final int id);

    BookEntity getBookEntityByIsbn(final String isbn);
    
    boolean existsByIsbn(final String isbn);

}