package com.brtvsk.lab4.controller;

import com.brtvsk.lab4.errorHandling.ApiError;
import com.brtvsk.lab4.model.Book;
import com.brtvsk.lab4.model.BookDto;
import com.brtvsk.lab4.model.BookResponseDto;
import com.brtvsk.lab4.service.BookService;
import com.brtvsk.lab4.validation.BookValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Test
    void shouldCreateBook() throws Exception {
        final Book toCreate = Book.of("Do Androids Dream of Electric Sheep?", "Philip K. Dick", 1968, "978-3-16-148410-6");

        final BookResponseDto bookResponse = BookResponseDto.of(toCreate.getTitle(), toCreate.getAuthor(), toCreate.getYear(), toCreate.getIsbn(), false);
        final BookDto bookRequest = new BookDto(toCreate.getTitle(), toCreate.getAuthor(), toCreate.getYear(), toCreate.getIsbn());

        Mockito.when(bookService.createBook(any()))
                .thenReturn(bookResponse);

        final String jsonRequest = objectMapper.writeValueAsString(bookRequest);
        final String expectedResponse = objectMapper.writeValueAsString(bookResponse);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/add-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));

        Mockito.verify(bookService).createBook(any());
        Mockito.verify(bookService).createBook(bookRequest);
    }

    @Test
    void shouldNotCreateBook() throws Exception {
        final Book toCreate = Book.of("Do Androids Dream of Electric Sheep?", "Philip K. Dick", 1968, "978-3-16-148410-0");

        final BookResponseDto bookResponse = BookResponseDto.of(toCreate.getTitle(), toCreate.getAuthor(), toCreate.getYear(), toCreate.getIsbn(), false);

        final BookValidationException expectedException = new BookValidationException("Book isbn already exists");
        final ApiError apiResponse =
                new ApiError(HttpStatus.BAD_REQUEST, expectedException.getMessage(), expectedException.toString());
        final BookDto bookRequest = new BookDto(toCreate.getTitle(), toCreate.getAuthor(), toCreate.getYear(), toCreate.getIsbn());

        Mockito.when(bookService.createBook(any()))
                .thenThrow(expectedException);

        final String jsonRequest = objectMapper.writeValueAsString(bookRequest);
        final String expectedResponse = objectMapper.writeValueAsString(apiResponse);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/add-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));

        Mockito.verify(bookService).createBook(any());
        Mockito.verify(bookService).createBook(bookRequest);
    }

    @Test
    void shouldReturnBooks() throws Exception {
        final Book toCreate = Book.of("Do Androids Dream of Electric Sheep?", "Philip K. Dick", 1968, "978-3-16-148410-6");

        final BookResponseDto bookResponse = BookResponseDto.of(toCreate.getTitle(), toCreate.getAuthor(), toCreate.getYear(), toCreate.getIsbn(), false);
        final BookDto bookRequest = new BookDto(toCreate.getTitle(), toCreate.getAuthor(), toCreate.getYear(), toCreate.getIsbn());

        Mockito.when(bookService.createBook(any()))
                .thenReturn(bookResponse);

        final String jsonRequest = objectMapper.writeValueAsString(bookRequest);
        final String expectedResponse = objectMapper.writeValueAsString(bookResponse);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/add-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));

        Mockito.verify(bookService).createBook(any());
        Mockito.verify(bookService).createBook(bookRequest);
    }

}