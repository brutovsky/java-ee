package com.brtvsk.lab4.validation;

import com.brtvsk.lab4.model.BookDto;

import java.util.List;

public interface IBookValidator {
    List<String> validateBook(final BookDto bookDto);
}
