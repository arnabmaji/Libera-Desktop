package io.github.arnabmaji19.libera.desktop.datasource;

import io.github.arnabmaji19.libera.desktop.model.UserDetails;
import org.asynchttpclient.Response;
import org.asynchttpclient.util.HttpConstants;

import java.util.concurrent.CompletableFuture;

public class UserRequest extends HttpRequest {

    private static final UserRequest instance = new UserRequest();

    private UserRequest() {
        setRoute("users/");
    }

    public static UserRequest getInstance() {
        return instance;
    }

    public CompletableFuture<UserDetails> getByEmail(String email) {
        /*
         * Make an http get request to fetch user by email
         */

        var urlWithParams = getBaseUrl() + getRoute() + email;
        return getClient()
                .prepareGet(urlWithParams)
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(this::parseResponse);
    }

    private UserDetails parseResponse(Response response) {
        /*
         * Parse response to suitable java object
         */
        if (response.getStatusCode() != HttpConstants.ResponseStatusCodes.OK_200)
            return null;
        return getGson().fromJson(response.getResponseBody(), UserDetails.class);
    }
}
