package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.reflect.TypeToken;
import io.github.arnabmaji19.libera.desktop.model.Publisher;
import io.github.arnabmaji19.libera.desktop.util.Session;
import org.asynchttpclient.Response;
import org.asynchttpclient.util.HttpConstants;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PublisherRequest extends EntityRequest<Publisher> {

    private final static PublisherRequest instance = new PublisherRequest();

    private PublisherRequest() {
        setRoute("publishers/");
    }

    public CompletableFuture<Boolean> add(String name) {
        /*
         * Make an http post request to create new publisher
         */
        var url = getBaseUrl() + getRoute();
        return getClient()
                .preparePost(url)
                .addHeader(getAuthTokenHeaderString(), Session.getInstance().getAuthToken())
                .addFormParam("name", name)
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(response -> response.getStatusCode() == HttpConstants.ResponseStatusCodes.OK_200);
    }

    public static PublisherRequest getInstance() {
        return instance;
    }

    @Override
    List<Publisher> parseResponse(Response response) {
        Type listType = new TypeToken<List<Publisher>>() {
        }.getType();
        return getGson().fromJson(response.getResponseBody(), listType);
    }
}
