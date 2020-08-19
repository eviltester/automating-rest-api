package uk.co.compendiumdev.advancedrestautomating.examples.marshalling;

import io.restassured.RestAssured;
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

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * A simple set of tests to demonstrate RESTAssured
 * against the RestListicator system
 */
public class RestAssuredMarshallingTest {

    String rootUrl = ServerConfig.DEFAULT_SERVER_ROOT;

    @Before
    public void configForTest(){
        //RestAssured.proxy("localhost", 8080);
    }

    // Fields need to be public for JAXB processing - not required for GSON
    // XMLannotations are for JAXB processing - not required for GSON
    // needs to be static for JAXB (since it is private) not required for GSON
    @XmlRootElement(name="list")
    private static class AListPayload{

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

    @XmlRootElement(name = "lists")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class AListsCollectionResponse{
        @XmlElement(name="list")
        public List<AListPayload> lists;
    }

    /**
     * Marshall and Unmarshall to objects rather than use JsonPath
     */
    @Test
    public void createListWithJSON(){

        final AListsCollectionResponse lists = RestAssured.
                when().
                get(rootUrl + "/lists").as(AListsCollectionResponse.class);

        int currentNumberOfLists = lists.lists.size();

        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
                body(new AListPayload("my list title")).
            when().
                post(rootUrl + "/lists").
            then().assertThat().
                statusCode(201);

        final AListsCollectionResponse amendedLists = RestAssured.
                when().
                get(rootUrl + "/lists").as(AListsCollectionResponse.class);

        Assert.assertTrue(String.format(
                "expected increase from %d to %d", currentNumberOfLists, currentNumberOfLists+1),
                amendedLists.lists.size() == currentNumberOfLists + 1
        );

    }

    @Test
    public void simpleCreateListWithJSON(){


        AListPayload desiredList = new AListPayload();
        desiredList.title = "my list " + System.currentTimeMillis();

        final Response response = RestAssured.
                given().
                    auth().preemptive().
                    basic("admin", "password").
                body(desiredList).
                when().
                    post(rootUrl + "/lists").andReturn();

        Assert.assertEquals(201, response.getStatusCode());

        // find the GUID that was assigned
        desiredList.guid  = response.getHeader("Location").replace("/lists/","");

        final AListsCollectionResponse amendedLists = RestAssured.
                when().
                get(rootUrl + "/lists").as(AListsCollectionResponse.class);

        Assert.assertTrue(foundDesiredList(desiredList, amendedLists));

    }

    @Test
    public void createAFullListWithJSONPut(){

        final AListsCollectionResponse lists = RestAssured.
                when().
                get(rootUrl + "/lists").as(AListsCollectionResponse.class);

        int currentNumberOfLists = lists.lists.size();

        final AListPayload desiredList = new AListPayload(UUID.randomUUID().toString(),
                "My Put Title",
                "My mini description",
                "2018-12-08-13-50-40",
                "2018-12-08-13-50-40");

        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
                body(desiredList).
            when().
                put(rootUrl + "/lists").
            then().assertThat().
                statusCode(201);

        final AListsCollectionResponse newlists = RestAssured.
                when().
                get(rootUrl + "/lists").as(AListsCollectionResponse.class);

        Assert.assertTrue(String.format(
                "expected increase from %d to %d", currentNumberOfLists, currentNumberOfLists+1),
                newlists.lists.size() == currentNumberOfLists + 1
        );

        Assert.assertTrue(foundDesiredList(desiredList, newlists));

    }




    @Test
    public void createListWithXML(){

        final AListsCollectionResponse lists = RestAssured.
                given().
                    accept("application/xml").
                when().
                    get(rootUrl + "/lists").as(AListsCollectionResponse.class);

        int currentNumberOfLists = lists.lists.size();

        AListPayload desiredList = new AListPayload("my list title");

        final Response response = RestAssured.
                given().
                auth().preemptive().
                basic("admin", "password").
                contentType("application/xml").
                accept("application/xml").
                body(desiredList).
                when().
                post(rootUrl + "/lists").andReturn();

        Assert.assertEquals(201, response.getStatusCode());

        // find the GUID that was assigned
        desiredList.guid  = response.getHeader("Location").replace("/lists/","");

        final AListsCollectionResponse amendedLists = RestAssured.
                given().
                    accept("application/xml").
                when().
                    get(rootUrl + "/lists").as(AListsCollectionResponse.class);

        Assert.assertTrue(String.format(
                "expected increase from %d to %d", currentNumberOfLists, currentNumberOfLists+1),
                amendedLists.lists.size() == currentNumberOfLists + 1
        );

        Assert.assertTrue(foundDesiredList(desiredList, amendedLists));

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

        - ensure tests exist for Create, Amend, PUT, list with Marshalling for both JSON and XML
           - add any missing tests
        - add tests for Create, Amend, Delete Users using Marshalling for both JSON and XML

     */
}
