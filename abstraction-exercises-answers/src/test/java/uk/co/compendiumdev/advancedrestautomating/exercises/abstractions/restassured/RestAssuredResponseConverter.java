package uk.co.compendiumdev.advancedrestautomating.exercises.abstractions.restassured;

import io.restassured.http.Header;
import io.restassured.response.Response;
import uk.co.compendiumdev.advancedrestautomating.exercises.abstractions.http.AnHttpResponse;

public class RestAssuredResponseConverter {
    private final Response response;

    public RestAssuredResponseConverter(final Response response) {
        this.response = response;
    }

    public AnHttpResponse toAnHttpResponse() {
        final AnHttpResponse theResponse = new AnHttpResponse(response.getStatusCode(), response.getBody().prettyPrint());

        for(Header header : response.getHeaders()){
            header.getName();
            theResponse.addHeader(header.getName(), header.getValue());
        }

        return theResponse;
    }
}
