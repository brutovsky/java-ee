package com.brtvsk.lab4.validation;

import com.brtvsk.lab4.model.BookDto;

public interface IBookValidator {
    boolean validateBook(final BookDto bookDto) throws BookValidationException;
}
