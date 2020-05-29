package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.reflect.TypeToken;
import io.github.arnabmaji19.libera.desktop.model.Librarian;
import io.github.arnabmaji19.libera.desktop.model.LibrarianDetails;
import io.github.arnabmaji19.libera.desktop.util.Session;
import org.asynchttpclient.Response;
import org.asynchttpclient.util.HttpConstants;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LibrarianRequest extends EntityRequest<LibrarianDetails> {

    private static final LibrarianRequest instance = new LibrarianRequest();

    private LibrarianRequest() {
        setRoute("librarians/");
    }

    public CompletableFuture<Boolean> add(Librarian librarian) {
        /*
         * Make an http post request to create new librarian
         */
        var url = getBaseUrl() + getRoute();
        var body = getGson().toJson(librarian);
        return getClient()
                .preparePost(url)
                .addHeader(getAuthTokenHeaderString(), Session.getInstance().getAuthToken())
                .addHeader("Content-Type", "application/json")
                .setBody(body)
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(response -> response.getStatusCode() == HttpConstants.ResponseStatusCodes.OK_200);
    }

    public CompletableFuture<Boolean> delete(int id) {
        /*
         * Make an http delete request to delete a librarian
         */
        var urlWithParam = getBaseUrl() + getRoute() + id;
        return getClient()
                .prepareDelete(urlWithParam)
                .addHeader(getAuthTokenHeaderString(), Session.getInstance().getAuthToken())
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(response -> response.getStatusCode() == HttpConstants.ResponseStatusCodes.OK_200);
    }

    public static LibrarianRequest getInstance() {
        return instance;
    }

    @Override
    List<LibrarianDetails> parseResponse(Response response) {
        Type listType = new TypeToken<List<LibrarianDetails>>() {
        }.getType();
        return getGson().fromJson(response.getResponseBody(), listType);
    }
}
