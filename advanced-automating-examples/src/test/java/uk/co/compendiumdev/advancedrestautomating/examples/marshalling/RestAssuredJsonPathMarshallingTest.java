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

import java.util.List;
import java.util.UUID;

/**
 * A simple set of tests to demonstrate RESTAssured
 * against the RestListicator system
 */
public class RestAssuredJsonPathMarshallingTest {

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


        // need to add getters in order to use JsonBuilder to convert object to JSON
        public String getGuid(){
            return this.guid;
        }

        public String getTitle(){
            return this.title;
        }

        public String getDescription(){
            return this.description;
        }

        public String getOwner(){
            return this.owner;
        }

        public String getCreatedDate(){
            return this.createdDate;
        }

        public String getAmendedDate(){
            return this.amendedDate;
        }

    }


    private class AListsCollectionResponse{
        public List<AListPayload> lists;
    }

    /**
     * Marshall and Unmarshall to objects with JSONPath and Groovy JsonBuilder
     * since RestAssured JSON functionality is a wrapper around these classes
     * REST Assured is now being used as a 'transport' layer
     *
     * and - both of these could be replaced by Gson - see GsonMarshallingTest
     *
     */

    @Test
    public void exampleOFJsonPathAsAGsonAbstractionLayer(){

        // GET the lists
        Response getLists = RestAssured.when().
                get(rootUrl + "/lists").andReturn();

        // Use JsonPath (from RestAssured) to convert JSON string to an object
        final AListsCollectionResponse lists = new JsonPath(getLists.body().asString()).getObject(".", AListsCollectionResponse.class);

        // CREATE A New List
        int currentNumberOfLists = lists.lists.size();

        final AListPayload payloadObject = new AListPayload("my list title");

        // Could Use Groovy JsonBuilder to convert object to Json String
        // Warning: but this includes 'nulls' - fortunately our API can handle that
        final String payload = new JsonBuilder(payloadObject).toString();

        // cannot use JsonGenerator because RestAssured wraps Groovy v 2.4.15 and not > 2.5.0
        //http://groovy-lang.org/json.html#_customizing_output
        //new JsonGenerator().Options().excludeNulls().build().toJson(payloadObject);

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

        final AListsCollectionResponse amendedLists = new JsonPath(getAmendedLists.body().asString()).getObject(".", AListsCollectionResponse.class);

        Assert.assertTrue(String.format(
                "expected increase from %d to %d", currentNumberOfLists, currentNumberOfLists+1),
                amendedLists.lists.size() == currentNumberOfLists + 1
        );

    }



}
