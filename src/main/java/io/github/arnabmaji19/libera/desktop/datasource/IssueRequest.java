package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.util.HttpConstants;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class IssueRequest {

    private static final IssueRequest instance = new IssueRequest();
    private static final String ROUTE = "issues/";

    private final AsyncHttpClient client;
    private final Gson gson;

    private IssueRequest() {
        this.client = RestConfig.getClient();
        this.gson = new Gson();
    }

    public static IssueRequest getInstance() {
        return instance;
    }

    public CompletableFuture<Boolean> checkout(int userId, List<Integer> holdingNumbers) {
        /*
         * Make an http post request to check out the added holdings for a user
         */

        var url = RestConfig.getBaseUrl() + ROUTE;
        var body = new RequestBody(userId, holdingNumbers);
        var jsonBody = gson.toJson(body);
        return client
                .preparePost(url)
                .setHeader("Content-Type", "application/json")
                .setBody(jsonBody)
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(response -> response.getStatusCode() == HttpConstants.ResponseStatusCodes.OK_200);

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
