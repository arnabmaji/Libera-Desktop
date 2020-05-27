package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.reflect.TypeToken;
import io.github.arnabmaji19.libera.desktop.model.LibrarianDetails;
import org.asynchttpclient.Response;

import java.lang.reflect.Type;
import java.util.List;

public class LibrarianRequest extends EntityRequest<LibrarianDetails> {

    private static final LibrarianRequest instance = new LibrarianRequest();

    private LibrarianRequest() {
        setRoute("librarians/");
    }

    public static LibrarianRequest getInstance() {
        return instance;
    }

    @Override
    List<LibrarianDetails> parseResponse(Response response) {
        Type listType = new TypeToken<List<LibrarianDetails>>() {
        }.getType();
        return getGson().fromJson(response.getResponseBody(), listType);
    }
}
