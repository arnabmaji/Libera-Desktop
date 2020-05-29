package io.github.arnabmaji19.libera.desktop.datasource;

import io.github.arnabmaji19.libera.desktop.model.UserAuthDetails;
import org.asynchttpclient.util.HttpConstants;

import java.util.concurrent.CompletableFuture;

public class AuthRequest extends HttpRequest {

    private final static AuthRequest instance = new AuthRequest();

    private AuthRequest() {
        setRoute("auth/");
    }

    public static AuthRequest getInstance() {
        return instance;
    }

    public CompletableFuture<Response> execute(AuthType authType, String email, String password) {
        /*
         * Make an http post request to server for authenticating different types for users
         */

        String endPoint = authType.equals(AuthType.LIBRARIAN) ? "librarian" : "user";
        String url = getBaseUrl() + getRoute() + endPoint;

        return getClient()
                .preparePost(url)
                .addFormParam("email", email)
                .addFormParam("password", password)
                .execute()
                .toCompletableFuture()
                .thenApplyAsync(this::parseResponse);

    }

    private Response parseResponse(org.asynchttpclient.Response response) {
        UserAuthDetails userAuthDetails = null;
        String authToken = null;
        if (response.getStatusCode() == HttpConstants.ResponseStatusCodes.OK_200) {
            userAuthDetails = getGson().fromJson(response.getResponseBody(), UserAuthDetails.class);
            authToken = response.getHeader("x-auth-token");
        }
        return new Response(response.getStatusCode(), userAuthDetails, authToken);
    }

    public enum AuthType {LIBRARIAN, USER}

    // Response for the auth request
    public static class Response {

        private int statusCode;
        private UserAuthDetails userAuthDetails;
        private String authToken;

        public Response(int statusCode, UserAuthDetails userAuthDetails, String authToken) {
            this.statusCode = statusCode;
            this.userAuthDetails = userAuthDetails;
            this.authToken = authToken;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public UserAuthDetails getUserAuthDetails() {
            return userAuthDetails;
        }

        public String getAuthToken() {
            return authToken;
        }
    }
}
