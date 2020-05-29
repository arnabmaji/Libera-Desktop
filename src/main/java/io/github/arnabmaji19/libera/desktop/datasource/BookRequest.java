package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.reflect.TypeToken;
import io.github.arnabmaji19.libera.desktop.model.Author;
import io.github.arnabmaji19.libera.desktop.model.Book;
import io.github.arnabmaji19.libera.desktop.model.Publisher;
import io.github.arnabmaji19.libera.desktop.util.Session;
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
                .addHeader(getAuthTokenHeaderString(), Session.getInstance().getAuthToken())
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
        var url = getBaseUrl() + getRoute() + "search";
        return getClient()
                .prepareGet(url)
                .addHeader(getAuthTokenHeaderString(), Session.getInstance().getAuthToken())
                .addQueryParam("keyword", keyword)
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(this::parseResponse);
    }

    public CompletableFuture<List<Integer>> getHoldings(ListType listType, int bookId) {
        /*
         *   Fetch all holdings for a book according to required list,
         *   ListType.AVAILABLE: Fetch all available holdings for a book
         *   ListType.ISSUED: Fetch all issued holdings for a book
         */

        var subRoute = listType.equals(ListType.AVAILABLE) ? "available/" : "issues/";
        var url = getBaseUrl() + getRoute() + subRoute + bookId;
        return getClient()
                .prepareGet(url)
                .addHeader(getAuthTokenHeaderString(), Session.getInstance().getAuthToken())
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(response -> {
                    Type responseListType = new TypeToken<List<Integer>>() {
                    }.getType();
                    return getGson().fromJson(response.getResponseBody(), responseListType);
                });
    }

    public enum ListType {AVAILABLE, ISSUED}

    private List<Book> parseResponse(Response response) {
        Type listType = new TypeToken<List<Book>>() {
        }.getType();
        return getGson().fromJson(response.getResponseBody(), listType);
    }

}
