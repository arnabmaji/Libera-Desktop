package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.annotations.SerializedName;
import org.asynchttpclient.util.HttpConstants;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class IssueRequest extends HttpRequest {

    private static final IssueRequest instance = new IssueRequest();

    private IssueRequest() {
        setRoute("issues/");
    }

    public static IssueRequest getInstance() {
        return instance;
    }

    public CompletableFuture<Boolean> checkout(int userId, List<Integer> holdingNumbers) {
        /*
         * Make an http post request to check out the added holdings for a user
         */

        var url = getBaseUrl() + getRoute();
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
