package io.github.arnabmaji19.libera.desktop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Book {

    @Expose
    @SerializedName("book_id")
    private int id;
    @Expose
    private String title;
    @Expose
    private String author;
    @Expose
    private String publisher;
    @Expose
    @SerializedName("year_published")
    private int yearPublished;
    @Expose(serialize = false, deserialize = false)
    @SerializedName("total_copies")
    private int totalCopies;
    @Expose(serialize = false, deserialize = false)
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
