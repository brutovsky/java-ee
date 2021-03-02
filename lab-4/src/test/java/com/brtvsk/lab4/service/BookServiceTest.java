package com.brtvsk.lab4.service;

import com.brtvsk.lab4.model.BookDto;
import com.brtvsk.lab4.model.BookResponseDto;
import com.brtvsk.lab4.validation.BookValidationException;
import com.brtvsk.lab4.validation.IBookValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private IBookService bookService;

    @SpyBean
    private IBookValidator bookValidator;

    @Test
    void shouldCreateBook() {
        final BookDto testBook = new BookDto("The Fall", "978-3-11-222222-6", 1956, "Albert Camus");
        final BookResponseDto expectedBook = BookResponseDto.of("The Fall", "Albert Camus", 1956, "978-3-11-222222-6");

        bookService.createBook(testBook);

        Mockito.verify(bookValidator).validateBook(testBook);

        assertThat(bookService.getBooks())
                .contains(expectedBook);
    }

}