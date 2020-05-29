package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.reflect.TypeToken;
import io.github.arnabmaji19.libera.desktop.model.Author;
import io.github.arnabmaji19.libera.desktop.util.Session;
import org.asynchttpclient.Response;
import org.asynchttpclient.util.HttpConstants;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AuthorRequest extends EntityRequest<Author> {

    private static final AuthorRequest instance = new AuthorRequest();

    private AuthorRequest() {
        setRoute("authors/");
    }

    public CompletableFuture<Boolean> add(String firstName, String lastName) {
        /*
         * Make an http post request to create new author
         */
        var url = getBaseUrl() + getRoute();
        return getClient()
                .preparePost(url)
                .addHeader(getAuthTokenHeaderString(), Session.getInstance().getAuthToken())
                .addFormParam("first_name", firstName)
                .addFormParam("last_name", lastName)
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(response -> response.getStatusCode() == HttpConstants.ResponseStatusCodes.OK_200);
    }

    public static AuthorRequest getInstance() {
        return instance;
    }

    @Override
    List<Author> parseResponse(Response response) {
        Type listType = new TypeToken<List<Author>>() {
        }.getType();
        return getGson().fromJson(response.getResponseBody(), listType);
    }
}
