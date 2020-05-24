package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.Gson;
import io.github.arnabmaji19.libera.desktop.model.UserDetails;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.asynchttpclient.util.HttpConstants;

import java.util.concurrent.CompletableFuture;

public class UserRequest {

    private static final UserRequest instance = new UserRequest();
    private static final String ROUTE = "users/";

    private final AsyncHttpClient client;
    private final Gson gson;

    private UserRequest() {
        this.client = RestConfig.getClient();
        this.gson = new Gson();
    }

    public static UserRequest getInstance() {
        return instance;
    }

    public CompletableFuture<UserDetails> getByEmail(String email) {
        /*
         * Make an http get request to fetch user by email
         */

        var urlWithParams = RestConfig.getBaseUrl() + ROUTE + email;
        return client
                .prepareGet(urlWithParams)
                .execute()
                .toCompletableFuture()
                .thenApply(this::parseResponse);


    }

    private UserDetails parseResponse(Response response) {
        /*
         * Parse response to suitable java object
         */
        if (response.getStatusCode() != HttpConstants.ResponseStatusCodes.OK_200)
            return null;
        return gson.fromJson(response.getResponseBody(), UserDetails.class);
    }
}
