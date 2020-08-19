package uk.co.compendiumdev.advancedrestautomating.exercises.abstractions.http;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.net.URL;
import java.util.HashMap;

public class ApiHttpMessage {
    private final HttpVerbs verb;
    private final URL url;
    private final HashMap<String, String> headers;
    private String username;
    private String password;
    private String body;

    public ApiHttpMessage(final HttpVerbs verb, final URL url) {
        this.verb = verb;
        this.url = url;
        this.headers = new HashMap<>();
    }

    public ApiHttpMessage withBasicAuthentication(final String username, final String password) {
        this.username = username;
        this.password = password;
        return this;
    }

    public Response send() {

        switch (verb){
            case OPTIONS:
                return sendOptionsMessage();

            case HEAD:
                return sendHeadMessage();

            case GET:
                return sendGetMessage();

            case POST:
                return sendPostMessage();

            case PUT:
                return sendPutMessage();

            case DELETE:
                return sendDeleteMessage();

            default:
                throw new RuntimeException("What verb did you want in the message?");
        }
    }


    private RequestSpecification prepareMessageWithAuth() {

        RequestSpecification messageDetails= RestAssured.given();

        if(username!=null && password!=null){
            messageDetails = messageDetails.
                    auth().preemptive().
                    basic(username, password);

        }

        messageDetails.headers(this.headers);

        return messageDetails;

    }

    private Response sendDeleteMessage() {

        return prepareMessageWithAuth().delete(url.toString()).andReturn();
    }


    private Response sendPostMessage() {

        return prepareMessageWithAuth().body(body).post(url.toString()).andReturn();
    }

    private Response sendPutMessage() {

        return prepareMessageWithAuth().body(body).put(url.toString()).andReturn();

    }

    private Response sendOptionsMessage() {

        return prepareMessageWithAuth().options(url.toString()).andReturn();

    }

    private Response sendGetMessage() {

        return prepareMessageWithAuth().get(url.toString()).andReturn();

    }

    private Response sendHeadMessage() {

        return prepareMessageWithAuth().head(url.toString()).andReturn();

    }

    public ApiHttpMessage andBody(final String messageBody) {
        this.body = messageBody;
        return this;
    }

    public ApiHttpMessage andHeaders(final HashMap<String, String> headers) {

        this.headers.putAll(headers);
        return this;
    }
}
