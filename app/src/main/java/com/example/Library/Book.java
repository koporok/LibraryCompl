package com.example.Library;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String title;
    private String genre;
    private String Author;
    private String review;
    public Book() {
    }
    public Book(String title, String genre, String Author, String review){
        this.title = title;
        this.genre = genre;
        this.Author = Author;
        this.review = review;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}