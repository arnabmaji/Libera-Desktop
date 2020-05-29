package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.reflect.TypeToken;
import io.github.arnabmaji19.libera.desktop.util.Session;
import org.asynchttpclient.Response;
import org.asynchttpclient.util.HttpConstants;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HoldingRequest extends HttpRequest {

    private static final HoldingRequest instance = new HoldingRequest();

    private final String url;

    public HoldingRequest() {
        setRoute("holdings/");
        this.url = getBaseUrl() + getRoute();
    }

    public static HoldingRequest getInstance() {
        return instance;
    }

    public CompletableFuture<List<Integer>> generate(int bookId, int quantity) {
        /*
         * Make an http request to create new Holdings
         */
        return getClient()
                .preparePost(url)
                .addHeader(getAuthTokenHeaderString(), Session.getInstance().getAuthToken())
                .addFormParam("book_id", Integer.toString(bookId))
                .addFormParam("items", Integer.toString(quantity))
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(this::parseResponse);
    }

    public CompletableFuture<Boolean> remove(int holdingNumber) {
        /*
         * Make an http DELETE request to remove the holding
         */
        var urlWithParam = url + "/" + holdingNumber;
        return getClient()
                .prepareDelete(urlWithParam)
                .addHeader(getAuthTokenHeaderString(), Session.getInstance().getAuthToken())
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(response -> response.getStatusCode() == HttpConstants.ResponseStatusCodes.OK_200);
    }

    private List<Integer> parseResponse(Response response) {
        Type listType = new TypeToken<List<Integer>>() {
        }.getType();
        return getGson().fromJson(response.getResponseBody(), listType);
    }
}
