package com.microservice.bookstore.request;

public class BookRequest {
    private String title;
    private String author;
    private Integer quantity;
    private Float price;

    public BookRequest(String title, String author, Integer quantity, Float price) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.price = price;
    }

    public BookRequest() {}

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
    public String toString() {
        return "title: " + title + "; author: " + author
                + "; price: " + price + "; quantity: " + quantity;
    }
}
