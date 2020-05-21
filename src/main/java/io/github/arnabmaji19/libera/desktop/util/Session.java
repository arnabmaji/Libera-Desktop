package io.github.arnabmaji19.libera.desktop.util;

import io.github.arnabmaji19.libera.desktop.model.User;

public class Session {

    private static final Session instance = new Session();

    private User user;
    private String authToken;

    private Session() {
    }

    public static Session getInstance() {
        return instance;
    }

    public void create(User user, String authToken) {
        this.user = user;
        this.authToken = authToken;
    }

    public boolean isAvailable() {
        return user != null;
    }

    public void clear() {
        this.user = null;
        this.authToken = "";
    }

    public User getUser() {
        return user;
    }

    public String getAuthToken() {
        return authToken;
    }
}
