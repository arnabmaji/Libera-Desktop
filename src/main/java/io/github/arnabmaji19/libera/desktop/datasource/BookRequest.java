package io.github.arnabmaji19.libera.desktop.datasource;

import io.github.arnabmaji19.libera.desktop.model.Author;
import io.github.arnabmaji19.libera.desktop.model.Publisher;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class BookRequest {

    private static final BookRequest instance = new BookRequest();

    private final String ROUTE = "books/";
    private final AsyncHttpClient client;

    private BookRequest() {
        this.client = RestConfig.getClient();
    }

    public static BookRequest getInstance() {
        return instance;
    }

    public CompletableFuture<Boolean> add(String title, Author author, Publisher publisher, String yearPublished) {
        /*
         * Make an http post request to add new book
         */
        return CompletableFuture.supplyAsync(() -> {
            try {
                var url = RestConfig.getBaseUrl() + ROUTE;
                var statusCode = client
                        .preparePost(url)
                        .addFormParam("title", title)
                        .addFormParam("author_id", Integer.toString(author.getId()))
                        .addFormParam("publisher_id", Integer.toString(publisher.getId()))
                        .addFormParam("year_published", yearPublished)
                        .execute()
                        .toCompletableFuture()
                        .thenApply(Response::getStatusCode)
                        .get();
                System.out.println(statusCode);
                return statusCode == 200;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return false;
        });
    }
}
