package io.github.arnabmaji19.libera.desktop.model;

import com.google.gson.annotations.SerializedName;

public class IssuedBook {

    @SerializedName("holding_number")
    private String holdingNumber;
    private String book;
    @SerializedName("due_date")
    private String dueDate;

    public IssuedBook(String holdingNumber, String book, String dueDate) {
        this.holdingNumber = holdingNumber;
        this.book = book;
        this.dueDate = dueDate;
    }

    public String getHoldingNumber() {
        return holdingNumber;
    }

    public String getBook() {
        return book;
    }

    public String getDueDate() {
        return dueDate;
    }
}
