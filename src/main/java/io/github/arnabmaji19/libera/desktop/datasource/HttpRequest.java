package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.Gson;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;

public abstract class HttpRequest {
    /*
     * Base class for creating all HttpRequests
     */

    private static final String BASE_URL = "http://localhost:3000/api/";
    private static final AsyncHttpClient client = Dsl.asyncHttpClient();
    private static final Gson gson = new Gson();

    private String route;

    public static Gson getGson() {
        return gson;
    }

    public static AsyncHttpClient getClient() {
        return client;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
