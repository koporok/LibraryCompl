package com.example.Library;

import java.io.Serializable;

public class Book implements Serializable {
    public int id;
    public String title;
    public String genre;
    public String author;
    public String review;
    public Book() {
    }
    public Book(int id, String title, String author, String genre, String review){
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
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
        return author;
    }

    public void setAuthor(String author) {
        author = author;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}