package com.brtvsk.lab4.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="books")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", columnDefinition = "serial")
    private int id;

    @Column(name="isbn", unique = true)
    private String isbn;

    @Column(name="title")
    private String title;

    @Column(name="author")
    private String author;

    @Column(name="publication_year")
    private int publicationYear;

    public BookEntity(final String isbn, final String title, final String author, final int year){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = year;
    }

}