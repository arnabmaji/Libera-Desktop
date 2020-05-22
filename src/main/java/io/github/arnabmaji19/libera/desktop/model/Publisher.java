package io.github.arnabmaji19.libera.desktop.model;

import com.google.gson.annotations.SerializedName;

public class Publisher {

    @SerializedName("publisher_id")
    private int id;
    private String name;

    public Publisher() {
    }

    public Publisher(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
