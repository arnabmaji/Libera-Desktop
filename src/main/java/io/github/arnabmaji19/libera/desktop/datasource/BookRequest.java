package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.reflect.TypeToken;
import io.github.arnabmaji19.libera.desktop.model.Author;
import io.github.arnabmaji19.libera.desktop.model.Book;
import io.github.arnabmaji19.libera.desktop.model.Publisher;
import org.asynchttpclient.Response;
import org.asynchttpclient.util.HttpConstants;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BookRequest extends HttpRequest {

    private static final BookRequest instance = new BookRequest();

    private BookRequest() {
        setRoute("books/");
    }

    public static BookRequest getInstance() {
        return instance;
    }

    public CompletableFuture<Boolean> add(String title, Author author, Publisher publisher, String yearPublished) {
        /*
         * Make an http post request to add new book
         */

        var url = getBaseUrl() + getRoute();
        return getClient()
                .preparePost(url)
                .addFormParam("title", title)
                .addFormParam("author_id", Integer.toString(author.getId()))
                .addFormParam("publisher_id", Integer.toString(publisher.getId()))
                .addFormParam("year_published", yearPublished)
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(response -> response.getStatusCode() == HttpConstants.ResponseStatusCodes.OK_200);
    }

    public CompletableFuture<List<Book>> search(final String keyword) {
        /*
         * Make an http request to fetch books with given keyword
         */
        var url = getBaseUrl() + getRoute() + "/search";
        return getClient()
                .prepareGet(url)
                .addQueryParam("keyword", keyword)
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(this::parseResponse);
    }

    private List<Book> parseResponse(Response response) {
        Type listType = new TypeToken<List<Book>>() {
        }.getType();
        return getGson().fromJson(response.getResponseBody(), listType);
    }

}
