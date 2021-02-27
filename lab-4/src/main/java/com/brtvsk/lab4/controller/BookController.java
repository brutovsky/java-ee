package com.brtvsk.lab4.controller;

import com.brtvsk.lab4.model.BookDto;
import com.brtvsk.lab4.model.BookResponseDto;
import com.brtvsk.lab4.service.BookService;
import com.brtvsk.lab4.validation.BookValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @ResponseBody
    @PostMapping(value = "/add-book")
    public ResponseEntity<BookResponseDto> addBookController(
            @RequestBody final BookDto bookDto
    ) {
        System.out.println("Add book request: " + bookDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.addBook(bookDto));
    }

    @GetMapping(value = "/get-books")
    public ResponseEntity<List<BookResponseDto>> getBooksController(
            @RequestParam final Optional<String> title,
            @RequestParam final Optional<String> isbn
    ) {
        if (title.isPresent() || isbn.isPresent()) {
            final String bookTitle = title.orElse("");
            final String bookISBN = isbn.orElse("");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(bookService.searchBooks(bookTitle, bookISBN));
        } else
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(bookService.getBooks());
    }

}
