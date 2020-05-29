package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.reflect.TypeToken;
import io.github.arnabmaji19.libera.desktop.model.User;
import io.github.arnabmaji19.libera.desktop.util.Session;
import org.asynchttpclient.Response;
import org.asynchttpclient.util.HttpConstants;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserRequest extends EntityRequest<User> {

    private static final UserRequest instance = new UserRequest();

    private UserRequest() {
        setRoute("users/");
    }

    public static UserRequest getInstance() {
        return instance;
    }

    public CompletableFuture<User> getByEmail(String email) {
        /*
         * Make an http get request to fetch user by email
         */

        var urlWithParams = getBaseUrl() + getRoute() + email;
        return getClient()
                .prepareGet(urlWithParams)
                .addHeader(getAuthTokenHeaderString(), Session.getInstance().getAuthToken())
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(response -> {
                    if (response.getStatusCode() != HttpConstants.ResponseStatusCodes.OK_200)
                        return null;
                    return getGson().fromJson(response.getResponseBody(), User.class);
                });
    }

    @Override
    public List<User> parseResponse(Response response) {
        /*
         * Parse response to suitable a list of user details
         */
        Type listType = new TypeToken<List<User>>() {
        }.getType();
        return getGson().fromJson(response.getResponseBody(), listType);

    }
}
