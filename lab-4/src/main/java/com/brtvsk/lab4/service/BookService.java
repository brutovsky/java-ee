package com.brtvsk.lab4.service;

import com.brtvsk.lab4.errorHandling.ApiError;
import com.brtvsk.lab4.model.Book;
import com.brtvsk.lab4.model.BookDto;
import com.brtvsk.lab4.model.BookResponseDto;
import com.brtvsk.lab4.repository.BookRepository;
import com.brtvsk.lab4.validation.BookValidationException;
import com.brtvsk.lab4.validation.BookValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookService implements IBookService {

    private final BookRepository bookRepository;
    private final BookValidator bookValidator;

    @Override
    public BookResponseDto addBook(BookDto bookDto) {
        bookValidator.validateBook(bookDto);
        final Book bookToAdd = Book.of(bookDto.getBookTitle(), bookDto.getBookAuthor(), bookDto.getBookYear(), bookDto.getBookISBN());
        bookRepository.addBook(bookToAdd);
        return BookResponseDto.of(bookToAdd.getTitle(), bookToAdd.getAuthor(), bookToAdd.getYear(), bookToAdd.getIsbn());
    }

    @Override
    public List<BookResponseDto> getBooks() {
        return bookRepository.getBooks().stream().map((book -> BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getYear(), book.getIsbn()))).
                collect(Collectors.toList());
    }

    @Override
    public List<BookResponseDto> searchBooks(final String title, final String isbn) {
        if (!title.isEmpty())
            return bookRepository.filterBooksByTitle(title).stream().map((book -> BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getYear(), book.getIsbn()))).
                    collect(Collectors.toList());
        if (!isbn.isEmpty())
            return bookRepository.filterBooksByISBN(isbn).stream().map((book -> BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getYear(), book.getIsbn()))).
                    collect(Collectors.toList());
        return getBooks();
    }

}
