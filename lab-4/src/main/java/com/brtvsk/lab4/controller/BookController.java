package com.brtvsk.lab4.controller;

import com.brtvsk.lab4.model.BookDto;
import com.brtvsk.lab4.model.BookEntity;
import com.brtvsk.lab4.model.BookResponseDto;
import com.brtvsk.lab4.model.LikeBookDto;
import com.brtvsk.lab4.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final IBookService bookService;

    @ResponseBody
    @PostMapping(value = "/add-book")
    public ResponseEntity<BookResponseDto> addBookController(
            @RequestBody final BookDto bookDto
    ) {
        System.out.println("Add book request: " + bookDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.createBook(bookDto));
    }

    @ResponseBody
    @PostMapping(value = "/like-book")
    public ResponseEntity<String> likeBookController(
            @RequestBody final LikeBookDto likeBook
    ) {
        bookService.likeBook(likeBook.getBookISBN());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping(value = "/get-books")
    public ResponseEntity<List<BookResponseDto>> getBooksController(
            @RequestParam final Optional<String> title,
            @RequestParam final Optional<String> author,
            @RequestParam final Optional<String> isbn,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);

        System.out.println(currentPage);
        System.out.println(pageSize);

        Pair<Long, List<BookResponseDto>> booksPage;

        if (title.isPresent() || isbn.isPresent() || author.isPresent()) {
            final String bookTitle = title.orElse("");
            final String bookAuthor = author.orElse("");
            final String bookISBN = isbn.orElse("");
            booksPage = bookService.searchBooks(bookTitle, bookAuthor, bookISBN, currentPage, pageSize);
        } else booksPage = bookService.getBooks(currentPage, pageSize);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Total-Count", String.valueOf(booksPage.getFirst()));

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(booksPage.getSecond());

    }

}
