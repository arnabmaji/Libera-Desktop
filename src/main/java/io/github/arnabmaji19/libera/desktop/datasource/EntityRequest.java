package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.Gson;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public abstract class EntityRequest<T> {

    private final AsyncHttpClient client;
    private final Gson gson;
    private String route;

    public EntityRequest() {
        this.client = RestConfig.getClient();
        this.gson = new Gson();
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public CompletableFuture<List<T>> get() {
        /*
         * Make an http request for fetching all entries
         */
        return CompletableFuture.supplyAsync(() -> {
            try {
                var url = RestConfig.getBaseUrl() + route;
                return client
                        .prepareGet(url)
                        .execute()
                        .toCompletableFuture()
                        .thenApply(this::parseResponse)
                        .get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return Collections.emptyList();
        });
    }

    public Gson getGson() {
        return gson;
    }

    abstract List<T> parseResponse(Response response);
}
