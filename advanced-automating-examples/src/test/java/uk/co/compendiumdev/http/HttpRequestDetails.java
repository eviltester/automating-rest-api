package uk.co.compendiumdev.http;


import java.util.HashMap;
import java.util.Map;

public class HttpRequestDetails {

    public String body;
    private Map<String, String> headers = new HashMap<>();

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {

        this.headers.putAll(headers);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String raw(){

        StringBuilder rawOutput = new StringBuilder();

        for(String key : headers.keySet()){
            // output only the null key which is the VERB header
            if(headers.get(key)==null){
                rawOutput.append(key);
                rawOutput.append("\n");
            }
        }

        // output the rest of the headers
        for(String key : headers.keySet()){
            if(headers.get(key)!=null){
                rawOutput.append(key);
                rawOutput.append(": ");
                rawOutput.append(headers.get(key));
                rawOutput.append("\n");
            }
        }
        
        if(body!=null){
            rawOutput.append("\n");
            rawOutput.append(body);
        }

        return rawOutput.toString();
    }
}
