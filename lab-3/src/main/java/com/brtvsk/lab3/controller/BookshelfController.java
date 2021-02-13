package com.brtvsk.lab3.controller;

import com.brtvsk.lab3.data.Bookshelf;
import com.brtvsk.lab3.config.BookshelfConfig;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Controller
public class BookshelfController {

    private final Bookshelf bookshelf;

    @RequestMapping(value = "/bookshelf", method = RequestMethod.GET)
    public String bookshelfControllerGet(Model model) {
        try {
            model.addAttribute("bookshelf", bookshelf.getBooks());
            return "bookshelf-table";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("bookshelf", List.of());
            return "bookshelf-table";
        }
    }

}
