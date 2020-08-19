package uk.co.compendiumdev.advancedrestautomating.exercises.abstractions;

import java.net.MalformedURLException;
import java.net.URL;

public class RestListicatorServer {
    private final String host;
    private final String port;
    private final String path;
    private String scheme = "http";

    public RestListicatorServer(final String host, final String port, final String path) {
        this.host = host;
        this.port = port;
        this.path = path;
    }

    public static RestListicatorServer getDefault() {
        return new RestListicatorServer("rest-list-system.herokuapp.com", "", "/listicator");
        //return new RestListicatorServer("localhost", ":4567", "/listicator");
    }

    public URL getURL() {

        String aURL =  String.format("%s://%s%s%s", scheme, host, port, path);

        URL theUrl;

        try {
            theUrl = new URL(aURL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(String.format("Invalid url format %s", aURL));
        }

        return theUrl;
    }
}
