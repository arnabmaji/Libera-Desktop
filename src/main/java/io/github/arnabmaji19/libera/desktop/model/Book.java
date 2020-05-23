package io.github.arnabmaji19.libera.desktop.model;

import com.google.gson.annotations.SerializedName;

public class Book {

    @SerializedName("book_id")
    private int id;
    private String title;
    private String author;
    private String publisher;
    @SerializedName("year_published")
    private int yearPublished;
    @SerializedName("total_copies")
    private int totalCopies;
    @SerializedName("available_copies")
    private int availableCopies;

    public Book() {
    }

    public Book(int id, String title, String author, String publisher, int yearPublished, int totalCopies, int availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }
}
