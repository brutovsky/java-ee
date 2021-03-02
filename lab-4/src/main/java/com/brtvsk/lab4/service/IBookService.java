package com.brtvsk.lab4.service;

import com.brtvsk.lab4.model.BookDto;
import com.brtvsk.lab4.model.BookResponseDto;

import java.util.List;

public interface IBookService {

    BookResponseDto createBook(final BookDto bookDto);

    BookResponseDto getBookByISBN(final String isbn);

    List<BookResponseDto> getBooks();

    List<BookResponseDto> searchBooks(String title, String author, String isbn);

}
