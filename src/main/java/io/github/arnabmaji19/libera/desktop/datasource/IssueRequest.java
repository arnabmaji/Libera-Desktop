package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import io.github.arnabmaji19.libera.desktop.model.IssuedBook;
import org.asynchttpclient.util.HttpConstants;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class IssueRequest extends HttpRequest {

    private static final IssueRequest instance = new IssueRequest();
    private final String url;

    private IssueRequest() {
        setRoute("issues/");
        this.url = getBaseUrl() + getRoute();
    }

    public static IssueRequest getInstance() {
        return instance;
    }

    public CompletableFuture<Boolean> checkout(int userId, List<Integer> holdingNumbers) {
        /*
         * Make an http post request to check out the added holdings for a user
         */

        var body = new RequestBody(userId, holdingNumbers);
        var jsonBody = getGson().toJson(body);
        return getClient()
                .preparePost(url)
                .setHeader("Content-Type", "application/json")
                .setBody(jsonBody)
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(response -> response.getStatusCode() == HttpConstants.ResponseStatusCodes.OK_200);

    }

    public CompletableFuture<Boolean> returnHolding(int holdingNumber) {
        /*
         * Make an http put request to return the holding
         */

        var urlWithParam = url + holdingNumber;
        return getClient()
                .preparePut(urlWithParam)
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(response -> response.getStatusCode() == HttpConstants.ResponseStatusCodes.OK_200);
    }

    public CompletableFuture<List<IssuedBook>> getIssuesForUser(int id) {
        /*
         * Make an http get request to fetch all issued books for a user
         */
        var urlWithParam = url + "user/" + id;
        return getClient()
                .prepareGet(urlWithParam)
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(response -> {

                    // parse response to a list of IssuedBook
                    Type listType = new TypeToken<List<IssuedBook>>() {
                    }.getType();
                    return getGson().fromJson(response.getResponseBody(), listType);

                });
    }

    private static class RequestBody {
        @SerializedName("user_id")
        private int userId;
        @SerializedName("holding_numbers")
        private List<Integer> holdingNumbers;

        public RequestBody() {
        }

        public RequestBody(int userId, List<Integer> holdingNumbers) {
            this.userId = userId;
            this.holdingNumbers = holdingNumbers;
        }
    }
}
