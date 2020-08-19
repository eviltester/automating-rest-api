package uk.co.compendiumdev.advancedrestautomating.examples.marshalling;

import com.google.gson.Gson;
import groovy.json.JsonBuilder;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.compendiumdev.advancedrestautomating.examples.ServerConfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.UUID;

/**
 * A simple set of tests to demonstrate RESTAssured
 * against the RestListicator system
 */
public class GsonMarshallingTest {

    String rootUrl = ServerConfig.DEFAULT_SERVER_ROOT;

    @Before
    public void configForTest(){
        //RestAssured.proxy("localhost", 8080);
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

        // GET the lists
        Response getLists = RestAssured.when().
                            get(rootUrl + "/lists").andReturn();

        final AListsCollectionResponse lists = new Gson().fromJson(
                                                    getLists.body().asString(),
                                                    AListsCollectionResponse.class);


        // CREATE A New List
        int currentNumberOfLists = lists.lists.size();

        final AListPayload payloadObject = new AListPayload("my list title");
        final String payload = new Gson().toJson(payloadObject);

        final Response response = RestAssured.
                given().
                    auth().preemptive().
                    basic("admin", "password").
                    body(payload).
                when().
                    post(rootUrl + "/lists").andReturn();

        Assert.assertEquals(201, response.getStatusCode());



        // GET amended Lists
        Response getAmendedLists = RestAssured.when().
                get(rootUrl + "/lists").andReturn();

        final AListsCollectionResponse amendedLists = new Gson().fromJson(
                                                getAmendedLists.body().asString(),
                                                AListsCollectionResponse.class);

        Assert.assertTrue(String.format(
                "expected increase from %d to %d", currentNumberOfLists, currentNumberOfLists+1),
                amendedLists.lists.size() == currentNumberOfLists + 1
        );

    }


    @Test
    public void simpleCreateListWithJSON(){


        AListPayload desiredList = new AListPayload();
        desiredList.title = "my list " + System.currentTimeMillis();

        String payload = new Gson().toJson(desiredList);

        final Response response = RestAssured.
                given().
                    auth().preemptive().
                    basic("admin", "password").
                body(payload).
                when().
                    post(rootUrl + "/lists").andReturn();

        Assert.assertEquals(201, response.getStatusCode());

        // find the GUID that was assigned
        desiredList.guid  = response.getHeader("Location").replace("/lists/","");


        // First step towards abstracting away REST Assured into an API Abstraction
        final AListsCollectionResponse amendedLists = getLists();

        Assert.assertTrue(foundDesiredList(desiredList, amendedLists));

    }

    private AListsCollectionResponse getLists() {

        Response lists = RestAssured.when().
                get(rootUrl + "/lists").andReturn();

        return new Gson().fromJson(
                lists.body().asString(),
                AListsCollectionResponse.class);
    }

    @Test
    public void createAFullListWithJSONPut(){

        final AListsCollectionResponse lists = getLists();

        int currentNumberOfLists = lists.lists.size();

        final AListPayload desiredList = new AListPayload(UUID.randomUUID().toString(),
                "My Put Title",
                "My mini description",
                "2018-12-08-13-50-40",
                "2018-12-08-13-50-40");

        String payload = new Gson().toJson(desiredList);

        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
                body(payload).
            when().
                put(rootUrl + "/lists").
            then().assertThat().
                statusCode(201);

        final AListsCollectionResponse newlists = getLists();
        Assert.assertTrue(String.format(
                "expected increase from %d to %d", currentNumberOfLists, currentNumberOfLists+1),
                newlists.lists.size() == currentNumberOfLists + 1
        );

        Assert.assertTrue(foundDesiredList(desiredList, newlists));

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

        - ensure tests exist for Create, Amend, PUT, list with GSON Marshalling
           - add any missing tests
        - add tests for Create, Amend, Delete Users using GSON for Marshalling/Unmarshalling the JSON

     */
}
