package com.brtvsk.lab3.controller;

import com.brtvsk.lab3.data.Bookshelf;
import com.brtvsk.lab3.model.Book;
import com.brtvsk.lab3.model.BookFormModel;
import com.brtvsk.lab3.config.BookshelfConfig;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@AllArgsConstructor
@Controller
public class FormController {

    private final Bookshelf bookshelf;

    @RequestMapping(value = "/add-book", method = RequestMethod.GET)
    public String formControllerGet() {
        return "add-book-form";
    }

    @RequestMapping(value = "/add-book", method = RequestMethod.POST)
    public String formControllerPost(@ModelAttribute BookFormModel bookFormModel) {
        final Book book = Book.builder()
                .title(bookFormModel.getBookTitle())
                .author(bookFormModel.getBookAuthor())
                .isbn(bookFormModel.getBookISBN())
                .year(bookFormModel.getBookYear())
                .build();

        bookshelf.addBook(book);

        return "redirect:/bookshelf";
    }

}