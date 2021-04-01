package com.brtvsk.lab4.service;

import com.brtvsk.lab4.model.BookDto;
import com.brtvsk.lab4.model.BookEntity;
import com.brtvsk.lab4.model.BookResponseDto;
import com.brtvsk.lab4.model.auth.UserEntity;
import com.brtvsk.lab4.repository.BookRep;
import com.brtvsk.lab4.repository.BookSpecs;
import com.brtvsk.lab4.repository.auth.IAuthenticationFacade;
import com.brtvsk.lab4.repository.auth.UserRepository;
import com.brtvsk.lab4.service.auth.UserNotAuthenticatedException;
import com.brtvsk.lab4.validation.IBookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.brtvsk.lab4.repository.BookSpecs.*;

@RequiredArgsConstructor
@Service
public class BookService implements IBookService {

    private final BookRep bookRepository;
    private final UserRepository userRepository;
    private final IBookValidator bookValidator;
    private final IAuthenticationFacade authenticationFacade;

    @Override
    public void likeBook(final String isbn) {
        BookEntity book = bookRepository.getBookEntityByIsbn(isbn);
        if (authenticationFacade.getAuthentication().isAuthenticated()) {
            Optional<UserEntity> user = userRepository.findByLogin(authenticationFacade.getAuthentication().getName());
            if (user.isEmpty()) {
                throw new UserNotAuthenticatedException("Користувач не зареєстрований :/");
            }
            user.ifPresent((userEntity -> {
                List<BookEntity> favBooks = userEntity.getFavBooks();
                if (!favBooks.contains(book)) {
                    favBooks.add(book);
                } else favBooks.remove(book);
                userEntity.setFavBooks(favBooks);
                userRepository.save(userEntity);
            }));
        } else throw new UserNotAuthenticatedException("Користувач не зареєстрований :/");
    }

    @Override
    public BookResponseDto createBook(BookDto bookDto) {
        bookValidator.validateBook(bookDto);
        BookEntity bookToSave = new BookEntity(bookDto.getBookISBN(), bookDto.getBookTitle(), bookDto.getBookAuthor(), bookDto.getBookYear());
        bookRepository.save(bookToSave);
        return BookResponseDto.of(bookToSave.getTitle(), bookToSave.getAuthor(), bookToSave.getPublicationYear(), bookToSave.getIsbn(), false);
    }

    @Override
    public Pair<Long, List<BookResponseDto>> getBooks(final int page, final int size) {
        Page<BookEntity> booksPage = bookRepository.findAll(PageRequest.of(page, size));
        if (authenticationFacade.getAuthentication().isAuthenticated()) {
            final String username = authenticationFacade.getAuthentication().getName();
            return Pair.of(booksPage.getTotalElements(), booksPage.getContent().stream().map(book -> bookToDtoResponseWithAuthentication(book, username)).
                    collect(Collectors.toList()));
        } else
            return Pair.of(booksPage.getTotalElements(), booksPage.getContent().stream().map(BookService::bookToDtoResponseWithoutAuthentication).
                    collect(Collectors.toList()));
    }

    @Override
    public BookResponseDto getBookByISBN(final String isbn) {
        final BookEntity book = bookRepository.getBookEntityByIsbn(isbn);
        if (authenticationFacade.getAuthentication().isAuthenticated()) {
            if (book.getUsers().stream().noneMatch(user -> user.getLogin().equals(authenticationFacade.getAuthentication().getName()))) {
                return BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn(), false);
            } else {
                return BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn(), true);
            }
        }
        return BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn(), false);
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

        if (authenticationFacade.getAuthentication().isAuthenticated()) {
            final String username = authenticationFacade.getAuthentication().getName();
            return Pair.of(booksPage.getTotalElements(), booksPage.getContent().stream().map(book -> bookToDtoResponseWithAuthentication(book, username)).
                    collect(Collectors.toList()));
        } else
            return Pair.of(booksPage.getTotalElements(), booksPage.getContent().stream().map(BookService::bookToDtoResponseWithoutAuthentication).
                    collect(Collectors.toList()));
    }

    @Override
    public List<BookResponseDto> searchBooks(final String title, final String author, final String isbn) {
        if (authenticationFacade.getAuthentication().isAuthenticated()) {
            final String username = authenticationFacade.getAuthentication().getName();
            if (!title.isEmpty())
                return bookRepository.findAll(getBooksByTitleSpec(title)).stream().map(book -> bookToDtoResponseWithAuthentication(book, username)).
                        collect(Collectors.toList());
            if (!author.isEmpty())
                return bookRepository.findAll(getBooksByAuthorSpec(author)).stream().map(book -> bookToDtoResponseWithAuthentication(book, username)).
                        collect(Collectors.toList());
            if (!isbn.isEmpty())
                return bookRepository.findAll(getBooksByIsbnSpec(isbn)).stream().map(book -> bookToDtoResponseWithAuthentication(book, username)).
                        collect(Collectors.toList());
        } else {
            if (!title.isEmpty())
                return bookRepository.findAll(getBooksByTitleSpec(title)).stream().map(BookService::bookToDtoResponseWithoutAuthentication).
                        collect(Collectors.toList());
            if (!author.isEmpty())
                return bookRepository.findAll(getBooksByAuthorSpec(author)).stream().map(BookService::bookToDtoResponseWithoutAuthentication).
                        collect(Collectors.toList());
            if (!isbn.isEmpty())
                return bookRepository.findAll(getBooksByIsbnSpec(isbn)).stream().map(BookService::bookToDtoResponseWithoutAuthentication).
                        collect(Collectors.toList());
        }
        return getBooks();
    }

    @Override
    public List<BookResponseDto> getBooks() {
        return bookRepository.findAll().stream().map(BookService::bookToDtoResponseWithoutAuthentication).
                collect(Collectors.toList());
    }

    private static BookResponseDto bookToDtoResponseWithAuthentication(final BookEntity book, final String username) {
        return BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn(), book.getUsers().stream().anyMatch(user -> user.getLogin().equals(username)));
    }

    private static BookResponseDto bookToDtoResponseWithoutAuthentication(final BookEntity book) {
        return BookResponseDto.of(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn(), false);
    }

}
