package com.microservice.bookstore.service;

import com.microservice.bookstore.model.book.Book;

import java.util.List;

public interface BookStoreService {

    Book insertBook(String title, String author, Integer quantity, Float price);

    Book upsertBook(String title, String author, Integer quantity, Float price);

    Integer findQuantityByTitle(String title);

    Book deleteBook(String title);

    List<String> getAllBooks();

    List<String> getAllBooksByAuthor(String author);

    List<String> getAllBooksByPriceRange(Float upperThreshold, Float lowerThreshold);

    List<String> getAllBooksByAvailability();
}
