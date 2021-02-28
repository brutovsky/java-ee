package com.brtvsk.lab4.service;

import com.brtvsk.lab4.model.BookDto;
import com.brtvsk.lab4.model.BookResponseDto;

import java.util.List;

public interface IBookService {

    BookResponseDto createBook(final BookDto bookDto);

    List<BookResponseDto> getBooks();

    List<BookResponseDto> searchBooks(String title, String isbn);

}
