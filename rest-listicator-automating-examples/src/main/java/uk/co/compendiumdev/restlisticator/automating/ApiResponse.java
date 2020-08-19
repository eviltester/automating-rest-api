package uk.co.compendiumdev.restlisticator.automating;

import io.restassured.response.Response;
import uk.co.compendiumdev.restlisticator.payloads.ListPayload;
import uk.co.compendiumdev.restlisticator.payloads.ListsPayload;


public class ApiResponse {
    private final Response response;

    public ApiResponse(Response response) {
        this.response = response;
    }

    public int getStatusCode() {
        return this.response.getStatusCode();
    }

    public ListsPayload asListsPayload() {
        return this.response.getBody().as(ListsPayload.class);
    }

    public boolean payloadIsJson() {
        return response.header("content-type").endsWith("/json");
    }

    public boolean payloadIsXML() {
        return response.header("content-type").endsWith("/xml");
    }

    public ListPayload asListPayload() {
        return this.response.getBody().as(ListPayload.class);
    }
}
