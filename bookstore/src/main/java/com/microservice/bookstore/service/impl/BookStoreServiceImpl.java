package com.microservice.bookstore.service.impl;

import com.microservice.bookstore.model.book.Book;
import com.microservice.bookstore.repository.bookstore.BookStoreRepository;
import com.microservice.bookstore.service.BookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookStoreServiceImpl implements BookStoreService {

    @Autowired
    private BookStoreRepository bookStoreRepository;

    @Override
    public Book insertBook(String title, String author, Integer quantity, Float price) {
        Book existingBook = this.bookStoreRepository.findBookByTitle(title).orElse(null);
        if (existingBook != null) {
            return upsertBook(title, author, quantity, price);
        } else {
            Book newBook = new Book();
            newBook.setTitle(title);
            newBook.setQuantity(quantity);
            newBook.setAuthor(author);
            newBook.setPrice(price);
            return this.bookStoreRepository.save(newBook);
        }
    }

    @Override
    public Book upsertBook(String title, String author, Integer quantity, Float price) {
        Book existingBook = this.bookStoreRepository.findBookByTitle(title).orElse(null);
        if (existingBook != null) {
            existingBook.setAuthor(author != null ? author : existingBook.getAuthor());
            existingBook.setQuantity(quantity != null ? quantity : existingBook.getQuantity());
            existingBook.setPrice(price != null ? price : existingBook.getPrice());
            return this.bookStoreRepository.save(existingBook);
        } else {
            return null;
        }
    }

    @Override
    public Integer findQuantityByTitle(String title) {
        Book book = this.bookStoreRepository.findBookByTitle(title).orElse(null);
        if (book != null) {
            return book.getQuantity();
        } else {
            return null;
        }
    }

    @Override
    public Book deleteBook(String title) {
        Book book = this.bookStoreRepository.findBookByTitle(title).orElse(null);
        if (book != null) {
            this.bookStoreRepository.deleteById(book.getBook_id());
            return book;
        } else {
            return null;
        }
    }

    @Override
    public List<String> getAllBooks() {
        return this.bookStoreRepository.findBooks();
    }

    @Override
    public List<String> getAllBooksByAuthor(String author) {
        return this.bookStoreRepository.findBooksByAuthor(author);
    }

    @Override
    public List<String> getAllBooksByPriceRange(Float upperThreshold, Float lowerThreshold) {
        return this.bookStoreRepository.findBooksByPriceRange(upperThreshold, lowerThreshold);
    }

    @Override
    public List<String> getAllBooksByAvailability() {
        return this.bookStoreRepository.findBooksByAvailability();
    }
}
