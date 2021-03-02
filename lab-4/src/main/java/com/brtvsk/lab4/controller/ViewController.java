package com.brtvsk.lab4.controller;

import com.brtvsk.lab4.repository.BookRepository;
import com.brtvsk.lab4.service.BookService;
import com.brtvsk.lab4.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final IBookService bookService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/book/{isbn}")
    public String bookPage(@PathVariable String isbn, Model model) {
        try {
            model.addAttribute("book", bookService.getBookByISBN(isbn));
            return "book-page";
        } catch (Exception e) {
            model.addAttribute("error", "Відсутня книга з таким ISBN :/");
            return "book-page";
        }
    }

}
