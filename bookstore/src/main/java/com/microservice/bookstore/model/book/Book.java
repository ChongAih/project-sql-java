package com.microservice.bookstore.model.book;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "book", indexes = {@Index(name = "idx_title", columnList="title")})
public class Book {
    @Id
    @SequenceGenerator(
        name = "book_id",
        sequenceName = "book_id",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "book_id"
    )
    private Integer book_id;
    private String title;
    private String author;
    private Integer quantity;
    private Float price;

    public Book(Integer book_id, String title, String author, Integer quantity, Float price) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.price = price;
    }

    public Book(){}

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book bookStore = (Book) o;
        return Objects.equals(book_id, bookStore.book_id) && Objects.equals(title, bookStore.title) && Objects.equals(author, bookStore.author) && Objects.equals(quantity, bookStore.quantity) && Objects.equals(price, bookStore.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book_id, title, author, quantity, price);
    }
}
