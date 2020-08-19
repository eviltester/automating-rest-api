package uk.co.compendiumdev.http;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class HttpMessageSender {


    private CanSendHttpRequests sender;
    private URL baseUrl;
    public String DEFAULT_USER_AGENT="Mozilla/5.0";

    public final String HEADER_USER_AGENT = "User-Agent";
    public final String HEADER_CONTENT_TYPE = "Content-Type";
    public final String HEADER_ACCEPT = "Accept";
    public final String HEADER_AUTHORIZATION = "Authorization";

    public final String CONTENT_XML = "application/xml";
    public final String CONTENT_JSON = "application/json";

    public Map<String, String> headers = new HashMap<>();
    private String proxyHost;
    private int proxyPort;

    public HttpMessageSender(String baseUrl) {
        try {
            this.baseUrl = new URL(baseUrl);
        }catch(MalformedURLException e){
            e.printStackTrace();
            System.out.println("*** BASE URL is wrong!! " + baseUrl);
        }

        headers = new HashMap<>();

        sender = new HttpRequestSender(null, 0);
    }

    public HttpRequestDetails getLastRequest(){
        return sender.getLastRequest();
    }
    public HttpResponseDetails getLastResponse(){
        return sender.getLastResponse();
    }

    public void setProxy(String ip, int port){
        proxyHost = ip;
        proxyPort = port;
        sender = new HttpRequestSender(proxyHost, proxyPort);
    }

    public void setUserAgent(){
        headers.put(HEADER_USER_AGENT, DEFAULT_USER_AGENT);
    }


    // https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods
    public HttpResponseDetails get(String url) {
        return sender.send( getUrl(url), "GET", headers, "");
    }

    public HttpResponseDetails head(String url) {
        return sender.send( getUrl(url), "HEAD", headers, "");
    }
    
    public HttpResponseDetails post(String url, String body) {
        return sender.send( getUrl(url), "POST", headers, body);
    }

    public HttpResponseDetails put(String url, String body) {
        return sender.send( getUrl(url), "PUT", headers, body);
    }

    public HttpResponseDetails delete(String url) {
        return sender.send( getUrl(url), "DELETE", headers, "");
    }

    public HttpResponseDetails connect(String url) {
        return sender.send( getUrl(url), "CONNECT", headers, "");
    }
    
    public HttpResponseDetails options(String url) {
        return sender.send( getUrl(url), "OPTIONS", headers, "");
    }

    public HttpResponseDetails trace(String url) {
        return sender.send( getUrl(url), "TRACE", headers, "");
    }

    public HttpResponseDetails patch(String url, String body) {
        return sender.send( getUrl(url), "PATCH", headers, body);
    }

    private URL getUrl(String url) {
        URL thisUrl;

        try{
            thisUrl = new URL(url);
            return thisUrl;
        }catch(MalformedURLException e){
        }

        try{
            thisUrl = new URL(this.baseUrl, url);
            return thisUrl;
        }catch(MalformedURLException e){
            e.printStackTrace();
            System.out.println("What url are you trying to build? " + this.baseUrl.toString() + url);
        }

        return null;
    }

    public void setHeader(String headername, String headervalue) {
        headers.put(headername, headervalue);
    }


    public void setBasicAuth(String username, String password) {

        String basicAuth = username + ":" + password;
        basicAuth = Base64.getEncoder().encodeToString(basicAuth.getBytes());

        setHeader(HEADER_AUTHORIZATION, "Basic " + basicAuth);
    }

    public void deleteHeader(String headername) {
        headers.remove(headername);
    }
}
