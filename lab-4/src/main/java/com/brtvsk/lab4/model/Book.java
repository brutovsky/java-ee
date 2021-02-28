package com.brtvsk.lab4.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "of")
public class Book {
    private final String title;
    private final String author;
    private final int year;
    private final String isbn;
}
