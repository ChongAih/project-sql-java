package com.microservice.bookstore.repository.bookstore;

import com.microservice.bookstore.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookStoreRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT b FROM Book b WHERE b.title = :title")
    Optional<Book> findBookByTitle(@Param("title") String title);

    @Query("SELECT DISTINCT b.title FROM Book b WHERE b.author = :author")
    List<String> findBooksByAuthor(@Param("author") String author);

    @Query("SELECT DISTINCT b.title FROM Book b WHERE b.price >= :lowerThreshold AND b.price < :upperThreshold")
    List<String> findBooksByPriceRange(@Param("upperThreshold") Float upperThreshold,
                                       @Param("lowerThreshold") Float lowerThreshold);

    @Query("SELECT DISTINCT b.title FROM Book b WHERE b.quantity > 0")
    List<String> findBooksByAvailability();

    @Query("SELECT DISTINCT b.title FROM Book b")
    List<String> findBooks();
}
