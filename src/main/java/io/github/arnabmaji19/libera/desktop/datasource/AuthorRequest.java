package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.reflect.TypeToken;
import io.github.arnabmaji19.libera.desktop.model.Author;
import org.asynchttpclient.Response;

import java.lang.reflect.Type;
import java.util.List;

public class AuthorRequest extends EntityRequest<Author> {

    private static final AuthorRequest instance = new AuthorRequest();

    private AuthorRequest() {
        setRoute("authors/");
    }

    public static AuthorRequest getInstance() {
        return instance;
    }

    @Override
    List<Author> parseResponse(Response response) {
        Type listType = new TypeToken<List<Author>>() {
        }.getType();
        return getGson().fromJson(response.getResponseBody(), listType);
    }
}
