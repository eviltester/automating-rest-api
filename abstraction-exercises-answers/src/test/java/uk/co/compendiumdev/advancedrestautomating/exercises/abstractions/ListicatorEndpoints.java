package uk.co.compendiumdev.advancedrestautomating.exercises.abstractions;

import java.net.MalformedURLException;
import java.net.URL;

public class ListicatorEndpoints {


    private final URL url;

    public ListicatorEndpoints(final URL url) {
        this.url = url;
    }

    public URL forHeartbeat() {
        return getAsUrl(url, "/heartbeat");
    }

    public URL forLists() {
        return getAsUrl(url,"/lists");
    }

    public URL forLists(final String guid) {
        return getAsUrl(forLists(), "/"+ guid);
    }


    private static URL getAsUrl(final URL url, final String path) {
        try {
            return new URL(url.toString() + path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Somehow you tried to create an invalid url %s%s", url.toString(), path));
        }

    }


}
