package com.brtvsk.lab4.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String bookTitle;
    private String bookISBN;
    private int bookYear;
    private String bookAuthor;
}
