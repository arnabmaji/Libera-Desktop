package io.github.arnabmaji19.libera.desktop.datasource;

import com.google.gson.reflect.TypeToken;
import io.github.arnabmaji19.libera.desktop.model.Publisher;
import org.asynchttpclient.Response;

import java.lang.reflect.Type;
import java.util.List;

public class PublisherRequest extends EntityRequest<Publisher> {

    private final static PublisherRequest instance = new PublisherRequest();

    private PublisherRequest() {
        setRoute("publishers/");
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
