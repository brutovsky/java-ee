package com.brtvsk.lab3.data;

import com.brtvsk.lab3.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public class Bookshelf {

    private final String bookshelfPath;

    public void addBook(final Book book) throws IOException {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File(bookshelfPath);

        if (file.exists()) {
            List<Book> booksFileContent = new ArrayList<>(Arrays.asList(mapper.readValue(file, Book[].class)));
            booksFileContent.add(book);
            mapper.writeValue(file, booksFileContent);
        } else {
            FileWriter fileWriter = new FileWriter(file, true);
            SequenceWriter sequenceWriter = mapper.writerWithDefaultPrettyPrinter().writeValuesAsArray(fileWriter);
            sequenceWriter.write(book);
            sequenceWriter.close();
        }
    }

    public List<Book> getBooks() throws IOException {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        return Arrays.asList(mapper.readValue(Paths.get(bookshelfPath).toFile(), Book[].class));
    }

}
