package com.brtvsk.lab3.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookFormModel {

    private String bookTitle;
    private String bookISBN;
    private int bookYear;
    private String bookAuthor;

}
