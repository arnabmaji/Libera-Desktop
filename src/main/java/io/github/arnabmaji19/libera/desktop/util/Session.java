package io.github.arnabmaji19.libera.desktop.util;

import io.github.arnabmaji19.libera.desktop.model.UserAuthDetails;

public class Session {

    private static final Session instance = new Session();

    private UserAuthDetails userAuthDetails;
    private String authToken;

    private Session() {
    }

    public static Session getInstance() {
        return instance;
    }

    public void create(UserAuthDetails userAuthDetails, String authToken) {
        this.userAuthDetails = userAuthDetails;
        this.authToken = authToken;
    }

    public boolean isAvailable() {
        return userAuthDetails != null;
    }

    public void clear() {
        this.userAuthDetails = null;
        this.authToken = "";
    }

    public UserAuthDetails getUserAuthDetails() {
        return userAuthDetails;
    }

    public String getAuthToken() {
        return authToken;
    }
}
