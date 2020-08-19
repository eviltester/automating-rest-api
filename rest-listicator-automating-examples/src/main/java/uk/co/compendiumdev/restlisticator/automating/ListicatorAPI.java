package uk.co.compendiumdev.restlisticator.automating;

import io.restassured.response.Response;
import uk.co.compendiumdev.restlisticator.payloads.ListPayload;

/**
 * A 'static' version of the RestListicatorAPI to make it easier to use
 */
public class ListicatorAPI {

    private static RestListicatorApi api;
    private static Response lastResponse;

    public static void sendContentAsXML() {
        thisApi().sendContentAsXML();
    }

    private static RestListicatorApi thisApi() {
        if(api==null){
            api = new RestListicatorApi();
        }
        return api;
    }

    public static void acceptXML() {
        thisApi().acceptXML();
    }

    public static ApiResponse postList(ApiUser user, ListPayload list) {
        lastResponse = thisApi().createList(user, list);
        return new ApiResponse(lastResponse);
    }

    public Response getLastResponse(){
        return lastResponse;
    }
}
