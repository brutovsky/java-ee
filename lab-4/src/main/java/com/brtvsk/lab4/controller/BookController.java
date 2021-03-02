package com.brtvsk.lab4.controller;

import com.brtvsk.lab4.model.BookDto;
import com.brtvsk.lab4.model.BookResponseDto;
import com.brtvsk.lab4.service.IBookService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping(value = "/get-books")
    public ResponseEntity<List<BookResponseDto>> getBooksController(
            @RequestParam final Optional<String> title,
            @RequestParam final Optional<String> author,
            @RequestParam final Optional<String> isbn
    ) {
        if (title.isPresent() || isbn.isPresent() || author.isPresent()) {
            final String bookTitle = title.orElse("");
            final String bookAuthor = author.orElse("");
            final String bookISBN = isbn.orElse("");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(bookService.searchBooks(bookTitle, bookAuthor, bookISBN));
        } else
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(bookService.getBooks());
    }

}
