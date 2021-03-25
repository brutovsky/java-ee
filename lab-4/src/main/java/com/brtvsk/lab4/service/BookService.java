package com.brtvsk.lab4.service;

import com.brtvsk.lab4.model.BookDto;
import com.brtvsk.lab4.model.BookEntity;
import com.brtvsk.lab4.model.BookResponseDto;
import com.brtvsk.lab4.repository.BookRep;
import com.brtvsk.lab4.validation.IBookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
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
    public Pair<Long, List<BookResponseDto>> getBooks(final int page, final int size) {
        Page<BookEntity> booksPage = bookRepository.findAll(PageRequest.of(page, size));
        return Pair.of(booksPage.getTotalElements(), booksPage.getContent().stream().map((book -> BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn()))).
                collect(Collectors.toList()));
    }

    @Override
    public BookResponseDto getBookByISBN(final String isbn) {
        final BookEntity book = bookRepository.getBookEntityByIsbn(isbn);
        return BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn());
    }

    @Override
    public Pair<Long, List<BookResponseDto>> searchBooks(final String title, final String author, final String isbn, final int page, final int size) {
        Page<BookEntity> booksPage;

        if (!title.isEmpty())
            booksPage = bookRepository.findAll(getBooksByTitleSpec(title), PageRequest.of(page, size));
        else if (!author.isEmpty())
            booksPage = bookRepository.findAll(getBooksByAuthorSpec(author), PageRequest.of(page, size));
        else if (!isbn.isEmpty())
            booksPage = bookRepository.findAll(getBooksByIsbnSpec(isbn), PageRequest.of(page, size));
        else booksPage = bookRepository.findAll(PageRequest.of(page, size));

        return Pair.of(booksPage.getTotalElements(), booksPage.getContent().stream().map((book -> BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn()))).
                collect(Collectors.toList()));
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

    @Override
    public List<BookResponseDto> getBooks() {
        return bookRepository.findAll().stream().map((book -> BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn()))).
                collect(Collectors.toList());
    }

}
