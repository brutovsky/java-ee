package com.brtvsk.lab4.repository;

import com.brtvsk.lab4.model.Book;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BookRepository {
    private final ArrayList<Book> DB = new ArrayList<>();

    @PostConstruct
    public void initDB() {
        DB.add(Book.of("Dune", "Frank Herbert", 1965, "978-3-16-148410-0"));
        DB.add(Book.of("Great Gatsby", "F. Scott Fitzgerald", 1925, "978-3-66-148410-0"));
        DB.add(Book.of("The Lord Of The Rings", "J.R.R.Tolkien", 1968, "999-3-16-148410-0"));
        DB.add(Book.of("Lord Of The Flies", "William Golding", 1954, "978-7-16-148410-3"));
        DB.add(Book.of("One Flew Over the Cuckoo's Nest", "Ken Kesey", 1962, "955-3-78-148410-0"));
        DB.add(Book.of("The Hitchhiker's Guide to the Galaxy", "Douglas Adams", 1979, "978-3-42-148410-0"));
    }

    public void addBook(final Book book) {
        DB.add(book);
    }

    public final List<Book> getBooks() {
        return List.copyOf(DB);
    }

    public boolean bookExists(final String isbn) {
        return DB.stream().anyMatch((book -> book.getIsbn().equals(isbn)));
    }

    public final List<Book> filterBooksByTitle(final String title) {
        return DB.stream().filter((book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))).collect(Collectors.toList());
    }

    public final List<Book> filterBooksByISBN(final String isbn) {
        return DB.stream().filter((book -> book.getIsbn().contains(isbn))).collect(Collectors.toList());
    }

}
