package uk.co.compendiumdev.http;

//import sun.net.www.MessageHeader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alan on 04/07/2017.
 */
public class HttpRequestSender implements CanSendHttpRequests {

    // https://stackoverflow.com/questions/1432961/how-do-i-make-httpurlconnection-use-a-proxy
    // Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.1", 8080));
    // conn = new URL(urlString).openConnection(proxy);
    Proxy proxy;
    private HttpRequestDetails lastRequest;
    private HttpResponseDetails lastResponse;

    public HttpRequestSender(String proxyHost, int proxyPort) {
        if(proxyHost!=null){
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        }
    }

//    public static void setProxy(String ip, int port){
//        proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
//    }

    public HttpRequestDetails getLastRequest(){
        return this.lastRequest;
    }

    public HttpResponseDetails getLastResponse(){
        return this.lastResponse;
    }

    public HttpResponseDetails send(URL url, String verb, Map<String, String> headers, String body) {

        HttpResponseDetails response = new HttpResponseDetails();

        try {

            HttpURLConnection con;

            if(proxy == null) {
                con = (HttpURLConnection) url.openConnection();
            }else{
                con = (HttpURLConnection) url.openConnection(proxy);
            }

            // HTTP VERB
            switch (verb.toLowerCase()){
                case "patch":
                    headers.put("X-HTTP-Method-Override", "PATCH");
                    //con.setRequestProperty("X-HTTP-Method-Override", "PATCH");
                    con.setRequestMethod("POST");
                    break;
                case "connect":
                    headers.put("X-HTTP-Method-Override", "CONNECT");
                    //con.setRequestProperty("X-HTTP-Method-Override", "PATCH");
                    con.setRequestMethod("POST");
                    break;    
                default:
                    headers.remove("X-HTTP-Method-Override"); // make sure we do not override the verb
                    con.setRequestMethod(verb);
                    break;
            }


            // SET HEADERS
            for(String headerName : headers.keySet()){
                con.setRequestProperty(headerName, headers.get(headerName));
                System.out.println("Header - " + headerName + " : " +  headers.get(headerName) );
            }

            String payload = body;

            System.out.println("\nSending '" + verb +"' request to URL : " + url);

            if(body.length()>0) {
                // Send post request
                System.out.println(verb + " Body : " + payload);
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(payload);
                wr.flush();
                wr.close();
            }

            // this writesout a lot of rubbish to the console when it runs
            int statusCode = con.getResponseCode();
            response.statusCode = statusCode;


            System.out.println("Response Code : " + statusCode);


            String responseBody = getResponseBody(con);


            //print result
            System.out.println("Response Body: " + responseBody);
            response.body = responseBody.toString();


            // add the headers
            Map<String, String> responseHeaders = new HashMap<>();
            for(String headerKey : con.getHeaderFields().keySet()){
                String headerValue =  con.getHeaderField(headerKey);
                responseHeaders.put(headerKey,headerValue);
                System.out.println("Header: " + headerKey + " - " + headerValue);
            }
            response.setHeaders(responseHeaders);

            lastRequest = new HttpRequestDetails();

            lastResponse = response;

            for(String sentHeader : lastRequest.getHeaders().keySet()){
                System.out.println(String.format("Request Header - %s:%s", sentHeader, lastRequest.getHeaders().get(sentHeader)));
            }


            if(body.length()>0) {
                lastRequest.body = body;
            }

        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return response;
    }

    private String getResponseBody(HttpURLConnection con) {
        BufferedReader in=null;

        // https://stackoverflow.com/questions/24707506/httpurlconnection-how-to-read-payload-of-400-response
        try {
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
        }catch(Exception e){
            // handle 400 exception messages
            InputStream stream = con.getErrorStream();
            if(stream!=null) {
                in = new BufferedReader(
                        new InputStreamReader(stream));
            }
        }

        String inputLine;
        StringBuffer responseBody = new StringBuffer();

        try{
            if(in!=null) {
                while ((inputLine = in.readLine()) != null) {
                    responseBody.append(inputLine);
                }
                in.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        return responseBody.toString();
    }
}
