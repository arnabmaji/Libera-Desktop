package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.arnabmaji19.libera.desktop.model.Book;
import io.github.arnabmaji19.libera.desktop.model.IssuedBook;
import io.github.arnabmaji19.libera.desktop.model.User;
import io.github.arnabmaji19.libera.desktop.model.UserSignUpDetails;
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

    public CompletableFuture<List<IssuedBook>> getIssuesForUser() {
        /*
         * Fetch all issued books for currently authenticated User
         */

        var url = getBaseUrl() + getRoute() + "actions/issues";
        return getClient()
                .prepareGet(url)
                .addHeader(getAuthTokenHeaderString(), Session.getInstance().getAuthToken())
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(response -> {

                    // parse response to a list of IssuedBook
                    Type listType = new TypeToken<List<IssuedBook>>() {
                    }.getType();
                    return getGson().fromJson(response.getResponseBody(), listType);

                });
    }

    public CompletableFuture<List<Book>> getReadingHistory() {
        /*
         * Fetch Reading History for currently authenticated User
         */

        var url = getBaseUrl() + getRoute() + "actions/history";
        return getClient()
                .prepareGet(url)
                .addHeader(getAuthTokenHeaderString(), Session.getInstance().getAuthToken())
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(response -> {

                    var listType = new TypeToken<List<Book>>() {
                    }.getType();
                    var gson = new GsonBuilder()
                            .excludeFieldsWithoutExposeAnnotation()
                            .create();
                    return gson.fromJson(response.getResponseBody(), listType);

                });

    }

    public CompletableFuture<String> createNew(UserSignUpDetails userSignUpDetails) {
        /*
         * Make an http POST request to create new User account
         */
        var url = getBaseUrl() + getRoute();
        var requestBody = getGson().toJson(userSignUpDetails);
        return getClient()
                .preparePost(url)
                .addHeader("Content-Type", "application/json")
                .setBody(requestBody)
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(response -> {

                    var status = response.getStatusCode();

                    if (status == HttpConstants.ResponseStatusCodes.OK_200)
                        return "Successful!";

                    if (status == 400)
                        return response.getResponseBody();

                    return "Something Went Wrong!";

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
