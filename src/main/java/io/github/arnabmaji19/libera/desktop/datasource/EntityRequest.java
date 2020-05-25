package io.github.arnabmaji19.libera.desktop.datasource;

import org.asynchttpclient.Response;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class EntityRequest<T> extends HttpRequest {

    public CompletableFuture<List<T>> get() {
        /*
         * Make an http request for fetching all entries
         */
        var url = getBaseUrl() + getRoute();
        return getClient()
                .prepareGet(url)
                .execute()
                .toCompletableFuture()
                .thenApply(this::parseResponse);
    }

    abstract List<T> parseResponse(Response response);
}
