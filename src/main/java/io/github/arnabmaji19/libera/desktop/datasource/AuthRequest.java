package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.Gson;
import io.github.arnabmaji19.libera.desktop.model.User;
import org.asynchttpclient.AsyncHttpClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AuthRequest {

    private final static AuthRequest instance = new AuthRequest();

    private final String ROUTE = "auth/";
    private final AsyncHttpClient client;
    private final Gson gson;

    private AuthRequest() {
        this.client = RestConfig.getClient();
        this.gson = new Gson();
    }

    public static AuthRequest getInstance() {
        return instance;
    }

    public CompletableFuture<Response> execute(AuthType authType, String email, String password) {
        /*
         * Make an http post request to server for authenticating different types for users
         */

        return CompletableFuture.supplyAsync(() -> {

            // determine auth type
            String endPoint = authType.equals(AuthType.LIBRARIAN) ? "librarian" : "user";
            String url = RestConfig.getBaseUrl() + ROUTE + endPoint;

            try {

                var future = client
                        .preparePost(url)
                        .addFormParam("email", email)
                        .addFormParam("password", password)
                        .execute();

                var response = future.get();
                User user = null;
                String authToken = null;
                if (response.getStatusCode() == 200) {
                    user = gson.fromJson(response.getResponseBody(), User.class);
                    authToken = response.getHeader("x-auth-token");
                }
                return new Response(response.getStatusCode(), user, authToken);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public enum AuthType {LIBRARIAN, USER}

    // Response for the auth request
    public static class Response {

        private int statusCode;
        private User user;
        private String authToken;

        public Response(int statusCode, User user, String authToken) {
            this.statusCode = statusCode;
            this.user = user;
            this.authToken = authToken;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public User getUser() {
            return user;
        }

        public String getAuthToken() {
            return authToken;
        }
    }
}
