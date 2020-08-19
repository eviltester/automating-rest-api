package uk.co.compendiumdev.advancedrestautomating.exercises.abstractions;

import com.google.gson.Gson;
import io.restassured.response.Response;
import uk.co.compendiumdev.advancedrestautomating.exercises.abstractions.http.AnHttpResponse;
import uk.co.compendiumdev.advancedrestautomating.exercises.abstractions.http.ApiHttpMessage;
import uk.co.compendiumdev.advancedrestautomating.exercises.abstractions.http.HttpVerbs;
import uk.co.compendiumdev.advancedrestautomating.exercises.abstractions.payloads.ListPayload;
import uk.co.compendiumdev.advancedrestautomating.exercises.abstractions.restassured.RestAssuredResponseConverter;

import java.util.HashMap;

public class RestListicatorApi {
    private final RestListicatorServer server;
    private final HashMap<String, String> headers;
    private ListicatorEndpoints endpoint;
    private String username;
    private String password;

    public RestListicatorApi(final RestListicatorServer server) {
        this.server = server;
        this.endpoint = new ListicatorEndpoints(server.getURL());
        this.headers = new HashMap<>();
    }

    public Response getHeartbeat() {

        return new ApiHttpMessage(HttpVerbs.GET, endpoint.forHeartbeat()).withBasicAuthentication(username, password).send();

        //return RestAssured.get(endpoint.forHeartbeat()).andReturn();

    }

    public void setBasicAuthentication(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public AnHttpResponse getLists() {

        Response response = new ApiHttpMessage(HttpVerbs.GET, endpoint.forLists()).withBasicAuthentication(username, password).andHeaders(headers).send();
        return new RestAssuredResponseConverter(response).toAnHttpResponse();
    }

    public AnHttpResponse getLists(final String guid) {
        Response response = new ApiHttpMessage(HttpVerbs.GET, endpoint.forLists(guid)).withBasicAuthentication(username, password).send();
        return new RestAssuredResponseConverter(response).toAnHttpResponse();
    }

    public AnHttpResponse postLists(final String listTitle) {

        final ListPayload listpayload = new ListPayload();

        listpayload.title = listTitle;

        return postLists(listpayload);

    }

    public AnHttpResponse postLists(final ListPayload listpayload) {

        String listBody = new Gson().toJson(listpayload);

        final Response response = new ApiHttpMessage(HttpVerbs.POST, endpoint.forLists()).
                withBasicAuthentication(username, password).andBody(listBody).
                send();

        return new RestAssuredResponseConverter(response).toAnHttpResponse();
    }

    public AnHttpResponse putLists(final ListPayload list) {

        String listBody = new Gson().toJson(list);

        final Response response = new ApiHttpMessage(HttpVerbs.PUT, endpoint.forLists()).
                withBasicAuthentication(username, password).andBody(listBody).
                send();

        return new RestAssuredResponseConverter(response).toAnHttpResponse();
    }


    public AnHttpResponse postList(final String guid, final ListPayload list) {
        String listBody = new Gson().toJson(list);

        final Response response = new ApiHttpMessage(HttpVerbs.POST, endpoint.forLists(guid)).
                withBasicAuthentication(username, password).andBody(listBody).
                send();

        return new RestAssuredResponseConverter(response).toAnHttpResponse();
    }

    public AnHttpResponse deleteList(final String aGuid) {
        final Response response = new ApiHttpMessage(HttpVerbs.DELETE, endpoint.forLists(aGuid)).
                withBasicAuthentication(username, password).
                send();

        return new RestAssuredResponseConverter(response).toAnHttpResponse();
    }

    public void setHeader(final String name, final String value) {
        headers.put(name, value);
    }

    public void clearHeaders() {
        this.headers.clear();
    }
}
