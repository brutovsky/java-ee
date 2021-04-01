package com.brtvsk.lab4.model;

import lombok.Data;

@Data(staticConstructor = "of")
public class BookResponseDto {
    private final String title;
    private final String author;
    private final int year;
    private final String isbn;
    private final boolean isFav;
}
