package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.arnabmaji19.libera.desktop.model.Publisher;
import org.asynchttpclient.AsyncHttpClient;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PublisherRequest {

    private final static PublisherRequest instance = new PublisherRequest();
    private final static String ROUTE = "publishers/";

    private final AsyncHttpClient client;
    private final Gson gson;

    private PublisherRequest() {
        this.client = RestConfig.getClient();
        this.gson = new Gson();
    }

    public static PublisherRequest getInstance() {
        return instance;
    }

    public CompletableFuture<List<Publisher>> get() {
        /*
         * Make an http request to fetch all publishers
         */

        return CompletableFuture.supplyAsync(() -> {
            try {
                var url = RestConfig.getBaseUrl() + ROUTE;
                var response = client
                        .prepareGet(url)
                        .execute()
                        .get();
                Type listType = new TypeToken<List<Publisher>>() {
                }.getType();
                return gson.fromJson(response.getResponseBody(), listType);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return Collections.emptyList();
        });
    }
}
