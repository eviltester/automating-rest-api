package uk.co.compendiumdev.advancedrestautomating.exercises.abstractions;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import uk.co.compendiumdev.advancedrestautomating.exercises.abstractions.http.AnHttpResponse;
import uk.co.compendiumdev.advancedrestautomating.exercises.abstractions.payloads.ListPayload;
import uk.co.compendiumdev.advancedrestautomating.exercises.abstractions.payloads.ListsCollectionResponse;

public class PayloadApi {
    private final RestListicatorApi api;
    private AnHttpResponse lastResponse;
    private int lastStatusCode;

    public PayloadApi(final RestListicatorApi api) {
        this.api = api;
    }

    public ListsCollectionResponse getLists() {

        ListsCollectionResponse lists = new ListsCollectionResponse();

        final AnHttpResponse response = api.getLists();

        setLastResponse(response);

        if(response.getStatusCode()==200 ) {
            return getListsCollectionResponse(response);

        }

        return lists;
    }

    private ListsCollectionResponse getListsCollectionResponse(final AnHttpResponse response) {
        if(response.getHeader("Content-Type").contains("xml")){
            return new XmlPath(response.body()).getObject(".", ListsCollectionResponse.class);
        }

        //else assume JSON
        return new JsonPath(response.body()).getObject(".", ListsCollectionResponse.class);
    }

    private ListPayload getListPayloadResponse(final AnHttpResponse response) {
        if(response.getHeader("Content-Type").contains("xml")){
            return new XmlPath(response.body()).getObject(".", ListPayload.class);
        }

        //else assume JSON
        return new JsonPath(response.body()).getObject(".", ListPayload.class);
    }

    public ListPayload getList(final String guid) {

        final AnHttpResponse response = api.getLists(guid);

        setLastResponse(response);

        if(response.getStatusCode()==200 ) {
            return getListPayloadResponse(response);
        }

        return new ListPayload();
    }

    private void setLastResponse(final AnHttpResponse response) {
        this.lastResponse = response;
        this.lastStatusCode = response.getStatusCode();

    }


    public int getLastStatusCode() {
        return lastStatusCode;
    }


    public ListsCollectionResponse postLists(final String listTitle) {

        final ListPayload list = new ListPayload();
        list.title = listTitle;

        return postLists(list);

    }


    public ListsCollectionResponse postLists(final ListPayload list) {

        ListsCollectionResponse lists = new ListsCollectionResponse();
        final AnHttpResponse response = api.postLists(list);

        setLastResponse(response);

        if(response.getStatusCode()==201 ) {
            return getListsCollectionResponse(response);
        }

        return lists;

    }


    public void postList(final String guid, final ListPayload list) {
        final AnHttpResponse response = api.postList(guid, list);
        setLastResponse(response);
    }

    public ListsCollectionResponse putLists(final ListPayload list) {

        ListsCollectionResponse lists = new ListsCollectionResponse();
        final AnHttpResponse response = api.putLists(list);

        setLastResponse(response);

        return lists;
    }


    public AnHttpResponse getLastResponse() {
        return this.lastResponse;
    }


    public void deleteList(final String aGuid) {
        final AnHttpResponse response = api.deleteList(aGuid);
        setLastResponse(response);
    }
}
