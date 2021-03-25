package com.brtvsk.lab4.service;

import com.brtvsk.lab4.model.BookDto;
import com.brtvsk.lab4.model.BookEntity;
import com.brtvsk.lab4.model.BookResponseDto;
import com.brtvsk.lab4.repository.BookRep;
import com.brtvsk.lab4.validation.IBookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.brtvsk.lab4.repository.BookSpecs.*;

@RequiredArgsConstructor
@Service
public class BookService implements IBookService {

    private final BookRep bookRepository;
    private final IBookValidator bookValidator;

    @Override
    public BookResponseDto createBook(BookDto bookDto) {
        bookValidator.validateBook(bookDto);
        BookEntity bookToSave = new BookEntity(bookDto.getBookISBN(), bookDto.getBookTitle(), bookDto.getBookAuthor(), bookDto.getBookYear());
        bookRepository.save(bookToSave);
        return BookResponseDto.of(bookToSave.getTitle(), bookToSave.getAuthor(), bookToSave.getPublicationYear(), bookToSave.getIsbn());
    }

    @Override
    public List<BookResponseDto> getBooks() {
        return bookRepository.findAll().stream().map((book -> BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn()))).
                collect(Collectors.toList());
    }

    @Override
    public BookResponseDto getBookByISBN(final String isbn) {
        final BookEntity book = bookRepository.getBookEntityByIsbn(isbn);
        return BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn());
    }

    @Override
    public List<BookResponseDto> searchBooks(final String title, final String author, final String isbn) {
        if (!title.isEmpty())
            return bookRepository.findAll(getBooksByTitleSpec(title)).stream().map((book -> BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn()))).
                    collect(Collectors.toList());
        if (!author.isEmpty())
            return bookRepository.findAll(getBooksByAuthorSpec(author)).stream().map((book -> BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn()))).
                    collect(Collectors.toList());
        if (!isbn.isEmpty())
            return bookRepository.findAll(getBooksByIsbnSpec(isbn)).stream().map((book -> BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn()))).
                    collect(Collectors.toList());
        return getBooks();
    }

}
