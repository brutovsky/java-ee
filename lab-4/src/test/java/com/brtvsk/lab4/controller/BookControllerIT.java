package com.brtvsk.lab4.controller;

import com.brtvsk.lab4.model.Book;
import com.brtvsk.lab4.model.BookDto;
import com.brtvsk.lab4.model.BookResponseDto;
import com.brtvsk.lab4.service.IBookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerIT {

    @Autowired
    private IBookService bookService;

    @LocalServerPort
    void savePort(final int port) {
        RestAssured.port = port;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Order(1)
    @Test
    void shouldCreateBookRequest() {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(BookControllerIT.class.getResourceAsStream("/book-request.json"))
                .when()
                .post("/add-book")
                .then()
                .body("title", CoreMatchers.is("Crime and Punishment"))
                .body("author", CoreMatchers.is("Fyodor Dostoevsky"))
                .body("year", CoreMatchers.is(1866))
                .body("isbn", CoreMatchers.is("978-3-11-111111-6"));
    }

    @Order(2)
    @Test
    void shouldCreateBooks() throws Exception {

        final ArrayList<BookResponseDto> expectedBooks = new ArrayList<>();
        final int initLength = bookService.getBooks().size();

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        InputStream is =
                BookControllerIT.class.getResourceAsStream("/input-books.json");

        mapper.readTree(is).iterator().forEachRemaining(jsonBody -> {
            try {
                final String jsonText = mapper.writeValueAsString(jsonBody);
                RestAssured.given()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(jsonText)
                        .when()
                        .post("/add-book");
                final BookDto bookDto = mapper.readValue(jsonText, BookDto.class);
                expectedBooks.add(BookResponseDto.of(bookDto.getBookTitle(), bookDto.getBookAuthor(), bookDto.getBookYear(), bookDto.getBookISBN(), false));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        final int expectedLength = expectedBooks.size() + initLength;

        Assertions.assertEquals(expectedLength, bookService.getBooks().size());
        Assertions.assertTrue(bookService.getBooks().containsAll(expectedBooks));
    }

    @Order(3)
    @Test
    void shouldGetBooksRequest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        InputStream is =
                BookControllerIT.class.getResourceAsStream("/output-books.json");

        final String response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/get-books")
                .then()
                .extract()
                .asString();

        mapper.readTree(is).iterator().forEachRemaining((jsonNode) -> {
            try {
                final String jsonText = mapper.writeValueAsString(jsonNode);
                Assertions.assertTrue(response.contains(jsonText));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    @Order(4)
    @Test
    void shouldSearchByTitleRequest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String expectedResponse = mapper.writeValueAsString(List.of(Book.of("Three Comrades", "Erich Maria Remarque", 1937, "978-3-30-148411-6")));

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("title", "Three Comrades")
                .when()
                .get("/get-books")
                .then()
                .body(CoreMatchers.is(expectedResponse));

    }

    @Order(5)
    @Test
    void shouldSearchByISBNRequest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        String expectedResponse = mapper.writeValueAsString(List.of(Book.of("Martin Eden", "Jack London", 1909, "978-3-20-148411-6")));

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("isbn", "978-3-20-148411-6")
                .when()
                .get("/get-books")
                .then()
                .body(CoreMatchers.is(expectedResponse));

    }

}
