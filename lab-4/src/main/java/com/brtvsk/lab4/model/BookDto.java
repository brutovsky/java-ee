package com.brtvsk.lab4.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    @NotBlank(message = "Book title is mandatory")
    private String bookTitle;
    @NotBlank(message = "Book ISBN is mandatory")
    private String bookISBN;
    @Max(value = 2077,message = "Book year must be less than 2077")
    @Min(value = 0,message = "Book year must be not less than 0")
    private int bookYear;
    @NotBlank(message = "Book author is mandatory")
    private String bookAuthor;
}
