package com.brtvsk.lab3.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Book {
    private String title;
    private String isbn;
    private String author;
    private int year;
}
