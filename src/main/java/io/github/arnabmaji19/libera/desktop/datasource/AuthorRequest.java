package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.arnabmaji19.libera.desktop.model.Author;
import org.asynchttpclient.AsyncHttpClient;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AuthorRequest {

    private static final AuthorRequest instance = new AuthorRequest();
    private static final String ROUTE = "authors/";

    private final AsyncHttpClient client;
    private final Gson gson;

    private AuthorRequest() {
        this.client = RestConfig.getClient();
        this.gson = new Gson();
    }

    public static AuthorRequest getInstance() {
        return instance;
    }

    public CompletableFuture<List<Author>> get() {

        return CompletableFuture.supplyAsync(() -> {
            /*
             * Make an http request for fetching all Authors
             */

            try {
                var url = RestConfig.getBaseUrl() + ROUTE;
                var future = client
                        .prepareGet(url)
                        .execute();
                var response = future.get();
                response.getResponseBody();
                Type listType = new TypeToken<List<Author>>() {
                }.getType();
                return gson.fromJson(response.getResponseBody(), listType);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return Collections.emptyList();
        });
    }
}
