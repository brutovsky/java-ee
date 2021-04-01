package com.brtvsk.lab4.service;

import com.brtvsk.lab4.model.BookDto;
import com.brtvsk.lab4.model.BookResponseDto;
import com.brtvsk.lab4.service.auth.UserNotAuthenticatedException;
import org.springframework.data.util.Pair;

import java.util.List;

public interface IBookService {

    BookResponseDto createBook(final BookDto bookDto);

    BookResponseDto getBookByISBN(final String isbn);

    void likeBook(final String isbn) throws UserNotAuthenticatedException;

    // For test consistency
    List<BookResponseDto> getBooks();

    //For test consistency
    List<BookResponseDto> searchBooks(String title, String author, String isbn);

    Pair<Long, List<BookResponseDto>> getBooks(final int page, final int size);

    Pair<Long, List<BookResponseDto>> searchBooks(String title, String author, String isbn, final int page, final int size);

}
