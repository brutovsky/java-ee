package com.brtvsk.lab3.config;

import com.brtvsk.lab3.data.Bookshelf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookshelfConfig {

    @Bean(name = "bookshelf")
    Bookshelf bookshelf() {
        return new Bookshelf("src/main/resources/data/books.json");
    }

}
