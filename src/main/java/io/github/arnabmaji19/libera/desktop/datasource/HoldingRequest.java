package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HoldingRequest {

    private static final HoldingRequest instance = new HoldingRequest();
    private static final String ROUTE = "holdings/";

    private final AsyncHttpClient client;
    private final Gson gson;

    public HoldingRequest() {
        this.client = RestConfig.getClient();
        this.gson = new Gson();
    }

    public static HoldingRequest getInstance() {
        return instance;
    }

    public CompletableFuture<List<Integer>> generate(int bookId, int quantity) {
        /*
         * Make an http request to create new Holdings
         */
        var url = RestConfig.getBaseUrl() + ROUTE;
        return client
                .preparePost(url)
                .addFormParam("book_id", Integer.toString(bookId))
                .addFormParam("items", Integer.toString(quantity))
                .execute()
                .toCompletableFuture()
                .thenApply(this::parseResponse);
    }

    private List<Integer> parseResponse(Response response) {
        Type listType = new TypeToken<List<Integer>>() {
        }.getType();
        return gson.fromJson(response.getResponseBody(), listType);
    }
}
