package com.brtvsk.lab4.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class Book {
    private final String title;
    private final String author;
    private final int year;
    private final String isbn;
}
