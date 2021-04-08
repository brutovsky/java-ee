package com.brtvsk.lab4.validation;

import com.brtvsk.lab4.model.BookDto;
import com.brtvsk.lab4.repository.BookRep;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookValidator implements IBookValidator {

    private final BookRep bookRepository;
    private final Validator validator;

    @Override
    public List<String> validateBook(BookDto bookDto) {
        final Set<ConstraintViolation<BookDto>> validationResult = validator.validate(bookDto);
        final List<String> errors = validationResult
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        try {
            isISBN(bookDto.getBookISBN());
            if (bookExists(bookDto.getBookISBN())) {
                errors.add("Book ISBN already exists");
            }
        } catch (final BookValidationException bve) {
            errors.add(bve.getMessage());
        }
        return errors;
    }

    private boolean bookExists(final String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }

    public static boolean isISBN(String number) {
        number = number.replace("-", "").replace(" ", "");

        try {
            Long.parseLong(number);
        } catch (NumberFormatException nfe) {
            throw new BookValidationException("Book ISBN must contain only numbers");
        }

        switch (number.length()) {
            case 10:
                return isISBN10(number);
            case 13:
                return isISBN13(number);
            default:
                throw new BookValidationException("Book ISBN number must consist of 10 or 13 number");
        }
    }

    private static boolean isISBN10(String number) {
        int sum = 0;
        int digit = 0;
        char ch = '\0';

        for (int i = 1; i <= 10; i++) {
            ch = number.charAt(i - 1);
            digit = Character.getNumericValue(ch);
            sum += (i * digit);
        }

        if (sum % 11 == 0)
            return true;
        else
            throw new BookValidationException("Book ISBN-10 checksum is wrong");
    }

    private static boolean isISBN13(String number) {
        int sum = 0;
        int multiple = 0;
        char ch = '\0';
        int digit = 0;

        for (int i = 1; i <= 13; i++) {

            if (i % 2 == 0)
                multiple = 3;
            else multiple = 1;

            ch = number.charAt(i - 1);
            digit = Character.getNumericValue(ch);
            sum += (multiple * digit);
        }

        if (sum % 10 == 0)
            return true;
        else throw new BookValidationException("Book ISBN-13 checksum is wrong");
    }

}
