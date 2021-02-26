package com.brtvsk.lab3.controller;

import com.brtvsk.lab3.data.Bookshelf;
import com.brtvsk.lab3.model.Book;
import com.brtvsk.lab3.model.BookFormModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@AllArgsConstructor
@Controller
public class FormController {

    private final Bookshelf bookshelf;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homeControllerGet() {
        return "redirect:/add-book";
    }

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

        try {
            bookshelf.addBook(book);
            return "redirect:/bookshelf";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/bookshelf";
        }
    }

}