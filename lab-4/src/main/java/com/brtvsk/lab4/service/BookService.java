package com.brtvsk.lab4.service;

import com.brtvsk.lab4.model.Book;
import com.brtvsk.lab4.model.BookDto;
import com.brtvsk.lab4.model.BookEntity;
import com.brtvsk.lab4.model.BookResponseDto;
import com.brtvsk.lab4.repository.BookRepository;
import com.brtvsk.lab4.validation.IBookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService implements IBookService {

    private final BookRepository bookRepository;
    private final IBookValidator bookValidator;

    @Override
    public BookResponseDto createBook(BookDto bookDto) {
        bookValidator.validateBook(bookDto);
        final Book bookToAdd = Book.of(bookDto.getBookTitle(), bookDto.getBookAuthor(), bookDto.getBookYear(), bookDto.getBookISBN());
        bookRepository.addBookToDB(bookToAdd);
        return BookResponseDto.of(bookToAdd.getTitle(), bookToAdd.getAuthor(), bookToAdd.getYear(), bookToAdd.getIsbn());
    }

    @Override
    public List<BookResponseDto> getBooks() {
        return bookRepository.getAllBooks().stream().map((book -> BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn()))).
                collect(Collectors.toList());
    }

    @Override
    public BookResponseDto getBookByISBN(final String isbn) {
        final BookEntity book = bookRepository.getBookByISBN(isbn);
        return BookResponseDto.of(book.getTitle(),book.getAuthor(),book.getPublicationYear(),book.getIsbn());
    }

    @Override
    public List<BookResponseDto> searchBooks(final String title, final String author, final String isbn) {
        if (!title.isEmpty())
            return bookRepository.getBooksByTitle(title).stream().map((book -> BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn()))).
                    collect(Collectors.toList());
        if (!author.isEmpty())
            return bookRepository.getBooksByAuthor(author).stream().map((book -> BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn()))).
                    collect(Collectors.toList());
        if (!isbn.isEmpty())
            return bookRepository.getBooksByISBN(isbn).stream().map((book -> BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn()))).
                    collect(Collectors.toList());
        return getBooks();
    }

}
