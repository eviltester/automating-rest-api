package uk.co.compendiumdev.http;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseDetails {

    public int statusCode;
    public String body;
    private Map<String, String> headers = new HashMap<>();

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {

        this.headers.putAll(headers);
    }

    public String asJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String raw() {
        StringBuilder rawOutput = new StringBuilder();

        for(Map.Entry<String, String> header : headers.entrySet()){
            if(header.getKey()==null) {
                rawOutput.append(header.getValue());
                rawOutput.append("\n");
            }
        }


        for(Map.Entry<String, String> header : headers.entrySet()){
            if(header.getKey()!=null) {
                if (header.getKey().equalsIgnoreCase("Transfer-Encoding")) {
                    //skip it
                } else {
                    rawOutput.append(header.getKey());
                    rawOutput.append(": ");
                    rawOutput.append(header.getValue());
                    rawOutput.append("\n");
                }
            }
        }

        if(body!=null){
            rawOutput.append("\n");
            rawOutput.append(body);
        }

        return rawOutput.toString();
    }
}
