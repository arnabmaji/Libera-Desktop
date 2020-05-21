package io.github.arnabmaji19.libera.desktop.datasource;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;

public class RestConfig {

    private final static String BASE_URL = "http://localhost:3000/api/";
    private final static AsyncHttpClient client = Dsl.asyncHttpClient();

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static AsyncHttpClient getClient() {
        return client;
    }
}
