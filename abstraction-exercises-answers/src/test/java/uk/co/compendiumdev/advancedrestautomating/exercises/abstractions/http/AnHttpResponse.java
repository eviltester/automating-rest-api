package uk.co.compendiumdev.advancedrestautomating.exercises.abstractions.http;

import java.util.HashMap;
import java.util.Map;

public class AnHttpResponse {
    private final int statusCode;
    private final String body;
    private Map<String, String> headers;

    public AnHttpResponse(final int statusCode, final String body) {
        this.statusCode = statusCode;
        this.body = body;
        headers = new HashMap();
    }

    public void addHeader(final String name, final String value) {
        headers.put(name.toLowerCase(), value);
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String body() {
        if(body==null){
            return "";
        }
        return body;
    }

    public String getHeader(final String headername) {
        return headers.get(headername.toLowerCase());
    }
}
