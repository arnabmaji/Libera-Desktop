package io.github.arnabmaji19.libera.desktop.datasource;

import io.github.arnabmaji19.libera.desktop.util.Session;
import org.asynchttpclient.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class StatisticsRequest extends HttpRequest {

    private static final StatisticsRequest instance = new StatisticsRequest();

    private final Map<Entity, String> entityMap;

    private StatisticsRequest() {
        setRoute("statistics/");
        this.entityMap = new HashMap<>();
        entityMap.put(Entity.BOOKS, "books");
        entityMap.put(Entity.AUTHORS, "authors");
        entityMap.put(Entity.HOLDINGS, "holdings");
        entityMap.put(Entity.PUBLISHERS, "publishers");
        entityMap.put(Entity.ISSUES, "issues");
        entityMap.put(Entity.USERS, "users");

    }

    public static StatisticsRequest getInstance() {
        return instance;
    }

    public CompletableFuture<Integer> getCount(Entity entity) {
        /*
         * Make an http GET request to get the count of entity
         */

        var urlWithParam = getBaseUrl() + getRoute() + "count/" + entityMap.get(entity);
        return getClient()
                .prepareGet(urlWithParam)
                .addHeader(getAuthTokenHeaderString(), Session.getInstance().getAuthToken())
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(Response::getResponseBody)
                .thenApplyAsync(Integer::parseInt);
    }

    public enum Entity {
        BOOKS,
        HOLDINGS,
        AUTHORS,
        PUBLISHERS,
        ISSUES,
        USERS
    }
}
