package com.microservice.bookstore.controller;

import com.microservice.bookstore.model.book.Book;
import com.microservice.bookstore.request.BookRequest;
import com.microservice.bookstore.response.StatusResponse;
import com.microservice.bookstore.service.BookStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("api/v1/bookstore")
@Api(value = "Book Controller", description = "Controller to perform different book store operations")
public class BookStoreController {
    private static final Logger logger = LoggerFactory.getLogger(BookStoreController.class);

    @Autowired
    private BookStoreService bookStoreService;

    @PostMapping(value = "/book")
    @ApiOperation(value = "Add a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added a book",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatusResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)}
    )
    public ResponseEntity<StatusResponse> addBook(
            @Parameter(description = "Information of a new book") @RequestBody BookRequest request
    ) {
        if (request.getAuthor() == null || request.getPrice() == null
                || request.getTitle() == null || request.getQuantity() == null) {
            String message = "Incorrect payload given: " + request;
            logger.info(message);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new StatusResponse(message, null));
        }
        try {
            logger.info("Adding a new book...");
            Book book = bookStoreService.insertBook(request.getTitle(),
                    request.getAuthor(), request.getQuantity(), request.getPrice());
            return ResponseEntity.ok(new StatusResponse("Successfully added a book", book));
        } catch (Exception e) {
            String message ="Exception occurs when adding a new book: " + e.getMessage();
            logger.info(message);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StatusResponse(message, null));
        }
    }

    @PatchMapping(value = "/book/{bookTitle}")
    @ApiOperation(value = "Update a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated a book",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatusResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)}
    )
    public ResponseEntity<StatusResponse> updateBook(
            @Parameter(description = "New information of a book") @RequestBody BookRequest request,
            @Parameter(description = "Book title") @PathVariable("bookTitle") String title
    ) {
        try {
            logger.info("Updating a new book...");
            Book book = bookStoreService.upsertBook(title,
                    request.getAuthor(), request.getQuantity(), request.getPrice());
            if (book != null) {
                return ResponseEntity.ok(new StatusResponse("Successfully updated a book", book));
            } else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new StatusResponse("No book with title: " + title, null));
            }
        } catch (Exception e) {
            String message ="Exception occurs when updating a book: " + e.getMessage();
            logger.info(message);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StatusResponse(message, null));
        }
    }

    @GetMapping(value = "/book/book-quantity/{bookTitle}")
    @ApiOperation(value = "Retrieve a book's quantity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved a book's quantity",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatusResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)}
    )
    public ResponseEntity<StatusResponse> updateBook(
            @Parameter(description = "Book title") @PathVariable("bookTitle") String title
    ) {
        try {
            logger.info("Retrieving quantity of a books in inventory...");
            Integer bookQuantity = bookStoreService.findQuantityByTitle(title);
            if (bookQuantity != null) {
                return ResponseEntity.ok(new StatusResponse("Successfully retrieved a book's quantity", bookQuantity));
            } else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new StatusResponse("No book with title: " + title, null));
            }
        } catch (Exception e) {
            String message ="Exception occurs when retrieving quantity of a books in inventory: " + e.getMessage();
            logger.info(message);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StatusResponse(message, null));
        }
    }

    @DeleteMapping(value = "/book/{bookTitle}")
    @ApiOperation(value = "Delete a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted a book",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatusResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)}
    )
    public ResponseEntity<StatusResponse> deleteBook(
            @Parameter(description = "Book title") @PathVariable("bookTitle") String title
    ) {
        try {
            logger.info("Deleting a new book...");
            Book book = bookStoreService.deleteBook(title);
            if (book != null) {
                return ResponseEntity.ok(new StatusResponse("Successfully deleted a book", book));
            } else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new StatusResponse("No book with title: " + title, null));
            }
        } catch (Exception e) {
            String message ="Exception occurs when deleting a book: " + e.getMessage();
            logger.info(message);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StatusResponse(message, null));
        }
    }

    @GetMapping(value = "/books")
    @ApiOperation(value = "List all books in inventory according to parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully listed books",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatusResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)}
    )
    public ResponseEntity<StatusResponse> getBooksByParam(
            @Parameter(description = "Book author") @RequestParam(name = "author",
                    required = false) String author,
            @Parameter(description = "Price upper threshold") @RequestParam(name = "upperThreshold",
                    required = false) Float upperThreshold,
            @Parameter(description = "Price lower threshold") @RequestParam(name = "lowerThreshold",
                    required = false) Float lowerThreshold
            ) {
        try {
            if (author != null) {
                logger.info("Listing all books in inventory by author...");
                List<String> books = bookStoreService.getAllBooksByAuthor(author);
                return ResponseEntity.ok(new StatusResponse("Successfully listed books by author", books));
            } else if (upperThreshold != null && lowerThreshold != null) {
                logger.info("Listing all books in inventory by price range...");
                List<String> books = bookStoreService.getAllBooksByPriceRange(upperThreshold, lowerThreshold);
                return ResponseEntity.ok(new StatusResponse("Successfully listed books by price range", books));
            } else {
                logger.info("Listing all books in inventory...");
                List<String> books = bookStoreService.getAllBooks();
                return ResponseEntity.ok(new StatusResponse("Successfully listed books", books));
            }
        } catch (Exception e) {
            String message ="Exception occurs when listing all books in inventory by author: " + e.getMessage();
            logger.info(message);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StatusResponse(message, null));
        }
    }
}