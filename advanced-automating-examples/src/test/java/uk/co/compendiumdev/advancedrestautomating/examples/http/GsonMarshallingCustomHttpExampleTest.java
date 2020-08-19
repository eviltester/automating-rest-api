package uk.co.compendiumdev.advancedrestautomating.examples.http;

import com.google.gson.Gson;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.compendiumdev.advancedrestautomating.examples.ServerConfig;
import uk.co.compendiumdev.http.HttpMessageSender;
import uk.co.compendiumdev.http.HttpResponseDetails;

import java.util.List;


/**
 * A simple set of tests to demonstrate Apache HTTP Client diretly with GSON
 * this does not use RestAssured at all
 */
public class GsonMarshallingCustomHttpExampleTest {

    String rootUrl = ServerConfig.DEFAULT_SERVER_ROOT;

    @Before
    public void configForTest(){

    }


    private class AListPayload{

        public String guid;
        public String title;
        public String description;
        public String owner;
        public String createdDate;
        public String amendedDate;

        public AListPayload(){
        }

        public AListPayload(String aTitle){
            this.title = aTitle;
        }

        public AListPayload(final String guid,
                            final String title,
                            final String description,
                            final String createdDate,
                            final String amendedDate) {
            this.guid=guid;
            this.title=title;
            this.description=description;
            this.createdDate=createdDate;
            this.amendedDate=amendedDate;
        }
    }


    private class AListsCollectionResponse{
        public List<AListPayload> lists;
    }

    /**
     * Marshall and Unmarshall to objects with Gson
     * REST Assured is now being used as a 'transport' layer
     */
    @Test
    public void createListWithJSON(){


        HttpMessageSender sender = new HttpMessageSender(ServerConfig.DEFAULT_SERVER_ROOT);

        //sender.setProxy("localhost", 8080);
        sender.setHeader(sender.HEADER_ACCEPT, "application/json");
        sender.setBasicAuth("admin", "password");

        // GET the lists
        HttpResponseDetails getLists = sender.get(rootUrl + "/lists");

        Assert.assertEquals(200, getLists.statusCode);

        final AListsCollectionResponse lists = new Gson().fromJson(
                                                getLists.body,
                                                AListsCollectionResponse.class);

        int currentNumberOfLists = lists.lists.size();

        // CREATE A New List
        final AListPayload payloadObject = new AListPayload("my list title");
        final String payload = new Gson().toJson(payloadObject);
        final HttpResponseDetails response = sender.post(rootUrl + "/lists", payload);
        Assert.assertEquals(201, response.statusCode);

        String header = response.getHeaders().get("Location");

        payloadObject.guid  = header.replace("/lists/","");



        // GET amended Lists
        HttpResponseDetails getAmendedLists = sender.get(rootUrl + "/lists");

        final AListsCollectionResponse amendedLists = new Gson().fromJson(
                                                getAmendedLists.body,
                                                AListsCollectionResponse.class);

        Assert.assertTrue(String.format(
                "expected increase from %d to %d", currentNumberOfLists, currentNumberOfLists+1),
                amendedLists.lists.size() == currentNumberOfLists + 1
        );

        Assert.assertTrue(foundDesiredList(payloadObject, amendedLists));

    }





    private boolean foundDesiredList(final AListPayload desiredList, final AListsCollectionResponse newlists) {
        boolean found = false;
        for(AListPayload list : newlists.lists){
            if(list.guid.contentEquals(desiredList.guid)){
                if(desiredList.title!=null) {
                    Assert.assertEquals(desiredList.title, list.title);
                }
                if(desiredList.description!=null) {
                    Assert.assertEquals(desiredList.description, list.description);
                }
                if(desiredList.amendedDate!=null){
                    Assert.assertEquals(desiredList.amendedDate, list.amendedDate);
                }
                if(desiredList.createdDate!=null) {
                    Assert.assertEquals(desiredList.createdDate, list.createdDate);
                }
                if(desiredList.owner!=null){
                    Assert.assertEquals(desiredList.owner, list.owner);
                }
                found=true;
            }
        }
        return found;
    }


    /*
        Suggested Exercises:

        - re-create some of the RestAssured tests using Gson and HttpClient directly
        - refactor the HTTP client into local methods to make sending requests and creating connections easier

     */
}
