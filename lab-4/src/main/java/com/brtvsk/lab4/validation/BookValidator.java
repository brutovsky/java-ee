package com.brtvsk.lab4.validation;

import com.brtvsk.lab4.model.BookDto;
import com.brtvsk.lab4.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookValidator implements IBookValidator {

    private final BookRepository bookRepository;

    @Override
    public boolean validateBook(BookDto bookDto) throws BookValidationException{
        return true;
    }
}
