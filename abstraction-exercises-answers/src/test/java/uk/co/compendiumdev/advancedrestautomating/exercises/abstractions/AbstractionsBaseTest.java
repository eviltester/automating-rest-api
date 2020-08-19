package uk.co.compendiumdev.advancedrestautomating.exercises.abstractions;

import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.compendiumdev.advancedrestautomating.exercises.abstractions.http.*;
import uk.co.compendiumdev.advancedrestautomating.exercises.abstractions.payloads.ListPayload;
import uk.co.compendiumdev.advancedrestautomating.exercises.abstractions.payloads.ListsCollectionResponse;

import java.util.UUID;

/**
 * A simple set of tests to refactor into abstraction layers
 */
public class AbstractionsBaseTest {

    /**
     * Exercise: create a RestListicatorServer abstraction class
     *
     * rather than a rootUrl String,
     * create a RestListicatorServer class
     * this should have default values that you can use e.g. "http://localhost:4567/listicator"
     * But should also let you set a new host ip as well
     * Use this in the tests rather than the `rootUrl` String
     *
     * e.g. `RestListicatorServer.getDefault()` to return a default configured server
     * or `new RestListicatorServer("localhost", 4567, "listicator");`
     *
     * You can chooose the best way for you to model it.
     *
     */
    //String rootUrl = "http://localhost:4567/listicator";
//    String rootUrl = ServerConfig.DEFAULT_SERVER_ROOT;

    RestListicatorServer server;
    String rootUrl;
//    private ListicatorEndpoints endpoint;
    RestListicatorApi api;
    PayloadApi payloadApi;

    @Before
    public void configForTest(){

        //RestAssured.proxy("localhost", 8080);
        server = RestListicatorServer.getDefault();
//        endpoint = new ListicatorEndpoints(server.getURL());
        rootUrl = server.getURL().toString();
        api = new RestListicatorApi(server);
        payloadApi = new PayloadApi(api);

    }

    @Test
    public void canCheckThatServerIsRunning(){

        /**
         * Exercise: create API EndPoints Abstraction
         *
         * Rather than having hardcoded strings for the end points
         *
         * Abstract this into a class to make it easier
         *
         * This might be
         *
         * new ListicatorEndpoints(server).forHeartbeat()
         *
         * or possibly on your RestListicatorServer object
         *
         * server.heartbeatEndPointUrl()
         *
         * or possibly ListicatorEndpoints.forHeartbeat(server)
         *
         * You choose how best to model it for yourself
         *
         * Repeat this for the remaining usages throughout the test
         *
         */

//        RestAssured.
//            get(endpoint.forHeartbeat()).
//                then().assertThat().
//            statusCode(200);



        /**
         * Exercise: avoid the use of RestAssured assertions
         *
         * Because we have used RestAssured assertions we can't really
         * creat an API abstraction that makes API calls, because this would
         * also assert on the responses and we want the test to do that
         * for the tests, split the use of RestAssured so that assertions are from
         * the JUnit assertions or any other assertion library that you choose to add
         * to the project
         * e.g.
         *
         *         final Response response = RestAssured.get(rootUrl + "/heartbeat").andReturn();
         *         Assert.assertEquals(200, response.getStatusCode());
         */

//        final Response response = RestAssured.get(endpoint.forHeartbeat()).andReturn();
//        Assert.assertEquals(200, response.getStatusCode());

        /**
         * Exercise: create an API abstraction
         *
         * Now that Assertions are separate from API calls, create an API abstraction
         *
         * e.g. it might be
         *
         * RestListicatorApi api = new RestListicatorApi(server);
         * Response response = api.heartbeat();
         * Assert.assertEquals(200, response.getStatusCode());
         *
         * But you can write this however you want, the point is to have an API
         * abstraction that lets us easily make api calls from within our test code
         * and assert on them in our tests
         */

//        RestListicatorApi api = new RestListicatorApi(server);

        Response response = api.getHeartbeat();
        Assert.assertEquals(200, response.getStatusCode());

    }


    @Test
    public void options(){

        /**
         * Exercise: add authentication to your API
         *
         * Some of our API calls require authentication
         *
         * Add authentication functionality into your API abstraction
         *
         * This might be:
         *
         * api.lists(Verbs.OPTIONS, "admin", "password")
         *
         * or
         * User aUSer = new User("admin", "password");
         * new RestListicatorApi(server, aUser).lists(Verb.OPTIONS);
         *
         * or
         *
         * new RestListicatorApi(server, aUser).options(Endpoints.LISTS_END_POINT);
         *
         * You Choose. Perhaps we didn't model the API effectively enough.
         *
         * Perhaps we need an HttpMessage abstraction for more adhoc usage e.g.
         *
         * new ApiHttpMessage(Verbs.OPTIONS, server.listsEndPointUrl()).send();
         *
         * Part of the challenge of writing abstractions is modelling it the
         * way we want our tests to read, and to make it easy to do adhoc testing
         * is OPTIONS something we would test a lot? should it be on the API?
         *
         * This is an 'advanced' workshop so you have to decide and refactor.
         *
         */

        final ListicatorEndpoints endpoints = new ListicatorEndpoints(server.getURL());

        final Response response =
                    new ApiHttpMessage(HttpVerbs.OPTIONS, endpoints.forLists()).
                            withBasicAuthentication("admin", "password").
                    send();

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("GET, POST, PUT", response.getHeader("Allow"));

    }

    @Test
    public void headOnListNotAllowed(){

        /**
         *
         * Exercise: extend your approach to HEAD
         *
         * Whatever approach you took for options, extend it to
         * cover HEAD
         *
         * HEAD is another 'adhoc' type method that we will need to
         * test, but may not need to test regularly enough to have as part
         * of the API
         *
         * And - this use is 'negative' so it probably shouldn't be in the
         * API abstraction, the API abstraction should probably only let us
         * make valid calls, but we still need to make invalid calls in our
         * testing to push the API and increase coverage
         *
         */

        final ListicatorEndpoints endpoints = new ListicatorEndpoints(server.getURL());

        final Response response =
                new ApiHttpMessage(HttpVerbs.HEAD, endpoints.forLists()).
                        withBasicAuthentication("admin", "password").
                        send();

        Assert.assertEquals(405, response.getStatusCode());

    }




    @Test
    public void createList(){

        /**
         * Exercise: introduce Payload objects
         *
         * Rather than extracting portions of a message
         * have the ability to create an object representation
         *
         * e.g. perhaps a ListsResponse class which has
         * a collection of List objects?
         *
         * You choose.
         *
         * You might have the API call return the object,
         * in which case how will you assert on the status code?
         *
         * You might choose to have response conversion separate from
         * the API calls. Perhaps an API that returns HTTP responses,
         * and possibly have an ObjectReturningAPI which returns objects?
         *
         * Whatever you do, try to keep it flexible for future use. But the
         * aim here is to introduce payload objects for body and response
         *
         * and also assert on the response status codes.
         *
         * The semantics of the test should not change. We are refactoring.
         *
         */

        // initial answer
//        final Response response = api.getLists();
//        Assert.assertEquals(200, response.getStatusCode());
//        final ListsCollectionResponse lists = new JsonPath(response.body().asString()).getObject(".", ListsCollectionResponse.class);
//        int currentNumberOfLists = lists.lists.size();


//        final Response postresponse = api.postLists("my title");
//        Assert.assertEquals(201, postresponse.getStatusCode());


//        api.getLists();
//        Assert.assertEquals(200, response.getStatusCode());
//        final ListsCollectionResponse newlists = new JsonPath(response.body().asString()).getObject(".", ListsCollectionResponse.class);
//
//
//        int newNumberOfLists = newlists.lists.size();
//        Assert.assertTrue(String.format(
//                    "expected increase from %d to %d", currentNumberOfLists, currentNumberOfLists+1),
//                newNumberOfLists == currentNumberOfLists + 1
//                );


        // refactored to a payload API
        api.setBasicAuthentication("admin", "password");

        final PayloadApi payloadApi = new PayloadApi(api);
        ListsCollectionResponse lists = payloadApi.getLists();

        Assert.assertEquals(200, payloadApi.getLastStatusCode());
        int currentNumberOfLists = lists.lists.size();


        // benefit of a payload API is that every API call with a payload should return a payload object
        ListsCollectionResponse createdLists = payloadApi.postLists("my title");
        Assert.assertEquals(201, payloadApi.getLastStatusCode());
        Assert.assertEquals(1, createdLists.lists.size());

        ListsCollectionResponse newlists = payloadApi.getLists();
        Assert.assertEquals(200, payloadApi.getLastStatusCode());
        int newNumberOfLists = newlists.lists.size();

        Assert.assertTrue(String.format(
                "expected increase from %d to %d", currentNumberOfLists, currentNumberOfLists+1),
                newNumberOfLists == currentNumberOfLists + 1
        );

        /**
         * Exercise: remove the dependence on the RestAssured Response class
         *
         * At the moment we are trying to abstract away our use of RestAssured
         *
         * so that we think in terms of HTTP and API calls
         *
         * But the RestAssured Response object is still in our @Test code
         *
         * Would it be valuable to create a 'logical' HttpResponse object?
         *
         * This could have a map of headers and a statusCode but would be
         * completely within our control and our Tests would use this and our
         * abstractions could return this, rather than relying on the physical
         * RestAssured Response object
         *
         * This might be created by `new HttpResponse(response)`
         *
         * or it could be aa 'transformation class`
         *
         * return RestAssuredToLogicalAbstraction.convertResponse(response);
         *
         * Again - you choose, build what you think is good enough, try it out
         * and refactor it later if you don't like it
         *
         * But the aim is to avoid having RestAssured response objects at our
         * @Test level - these should only exist at the abstraction levels which
         * know about RestAssured
         *
         */


    }

    @Test
    public void createAFullListWithPut(){

        /**
         * Exercise: use and expand your abstractions
         *
         * Expand your abstractions to refactor the following test
         *
         * You could introduce a RandomListBuilder() which would
         * create a list with random details, but also allow you to override
         * the random elements.
         *
         * Or you could choose to leave the test data creation as it is.
         *
         * You might not need Payload creation builders
         * or Random data Generators yet.
         *
         * You decide. But just keep refactoring.
         *
         */


//        int currentNumberOfLists = RestAssured.
//                when().
//                get(rootUrl + "/lists").
//                then().assertThat().
//                statusCode(200).extract().body().path("lists.size");

//        String aGuid = UUID.randomUUID().toString();
//        String aTitle = "My Put Title";
//        String aDescription = "My mini description";
//        String createdDate="2018-12-08-13-50-40";
//        String amendedDate=createdDate;
//
//        String message = String.format("{guid:'%s',title:'%s',description:'%s', createdDate:'%s', amendedDate:'%s'}",
//                    aGuid, aTitle, aDescription, createdDate, amendedDate);

        final ListsCollectionResponse lists = payloadApi.getLists();
        Assert.assertEquals(200, payloadApi.getLastStatusCode());

        int currentNumberOfLists = lists.lists.size();

        ListPayload list = new ListPayload();
        list.guid = UUID.randomUUID().toString();
        list.title = "My Put Title";
        list.description = "My mini description";
        list.createdDate ="2018-12-08-13-50-40";
        list.amendedDate = list.createdDate;

        api.setBasicAuthentication("admin", "password");
        final ListsCollectionResponse putlists = payloadApi.putLists(list);

        Assert.assertEquals(201, payloadApi.getLastStatusCode());


//        RestAssured.
//            given().
//                auth().preemptive().
//                basic("admin", "password").
//                body(message).
//            when().
//                put(rootUrl + "/lists").
//            then().assertThat().
//                statusCode(201);

        int newNumberOfLists = payloadApi.getLists().lists.size();

//        int newNumberOfLists = RestAssured.
//                when().
//                    get(rootUrl + "/lists").
//                then().assertThat().
//                statusCode(200).extract().body().path("lists.size");

        Assert.assertTrue(String.format(
                "expected increase from %d to %d", currentNumberOfLists, currentNumberOfLists+1),
                newNumberOfLists == currentNumberOfLists + 1
        );

    }

    @Test
    public void amendAList(){

        /** Exercise
         *
         * Continue to refactor your code and build up your abstraction layers
         *
         * Remember to keep the semantics of the test so you need to allow
         * assertion on contentType in the response
         *
         */
//        String aGuid = UUID.randomUUID().toString();
//        String messageBody = String.format("{title : 'my oldtitle', guid:'%s'}", aGuid);

        final ListPayload list = new ListPayload();
        list.guid = UUID.randomUUID().toString();
        list.title = "my oldtitle";

        // CREATE THE LIST
//        RestAssured.
//                given().
//                auth().preemptive().
//                basic("admin", "password").
//                body(messageBody).
//                when().
//                post(rootUrl + "/lists").
//                then().assertThat().
//                statusCode(201);

        api.setBasicAuthentication("admin", "password");
        payloadApi.postLists(list);
        Assert.assertEquals(201, payloadApi.getLastStatusCode());





        // GET it to check it was added
//        RestAssured.
//            given().
//                auth().preemptive().
//                basic("admin", "password").
//            when().
//                get(rootUrl + "/lists" + "/" + aGuid).
//            then().assertThat().
//                contentType("application/json").
//                body("title", equalTo("my oldtitle")).
//                statusCode(200);


        final ListPayload createdlist = payloadApi.getList(list.guid);
        Assert.assertEquals("application/json", payloadApi.getLastResponse().getHeader("content-type"));
        Assert.assertEquals(list.title, createdlist.title);
        Assert.assertEquals(list.guid, createdlist.guid);


        // AMEND THE LIST

//        RestAssured.
//            given().
//                auth().preemptive().
//                basic("admin", "password").
//                body("{title : 'my new title'}").
//            when().
//                post(rootUrl + "/lists" + "/" + aGuid).
//            then().assertThat().
//                statusCode(200);

        final ListPayload amendPayload = new ListPayload();
        amendPayload.title = "my new title";
        payloadApi.postList(list.guid, amendPayload);


        // GET it to check it was amended
//        RestAssured.
//            given().
//                auth().preemptive().
//                basic("admin", "password").
//            when().
//                get(rootUrl + "/lists" + "/" + aGuid).
//            then().assertThat().
//                contentType("application/json").
//                body("title", equalTo("my new title")).
//                statusCode(200);


        final ListPayload amendedList = payloadApi.getList(list.guid);
        Assert.assertEquals("application/json", payloadApi.getLastResponse().getHeader("content-type"));
        Assert.assertEquals( "my new title", amendedList.title);
        Assert.assertEquals(list.guid, amendedList.guid);

    }

    @Test
    public void deleteAList(){

        /**
         * Exercise: continue to expand your abstractions and keep the semantics of the test
         *
         */
        String aGuid = UUID.randomUUID().toString();
//        String messageBody = String.format("{title : 'my only title', guid:'%s'}", aGuid);


        final ListPayload list = new ListPayload();
        list.guid = aGuid;
        list.title = "my only title";

        api.setBasicAuthentication("admin", "password");
        payloadApi.postLists(list);
        Assert.assertEquals(201, payloadApi.getLastStatusCode());


        // CREATE THE LIST
//        RestAssured.
//            given().
//                auth().preemptive().
//                basic("admin", "password").
//                body(messageBody).
//            when().
//                post(rootUrl + "/lists").
//            then().assertThat().
//                statusCode(201);

        // GET it to check it was added
//        RestAssured.
//            given().
//                auth().preemptive().
//                basic("admin", "password").
//            when().
//                get(rootUrl + "/lists" + "/" + aGuid).
//            then().assertThat().
//                contentType("application/json").
//                body("title", equalTo("my only title")).
//                statusCode(200);

        final ListPayload createdList = payloadApi.getList(aGuid);

        Assert.assertEquals(200, payloadApi.getLastStatusCode());
        Assert.assertEquals("application/json", payloadApi.getLastResponse().getHeader("content-type"));
        Assert.assertEquals("my only title", createdList.title);

        // Delete THE LIST

//        RestAssured.
//            given().
//                auth().preemptive().
//                basic("admin", "password").
//                body("{title : 'my new title'}").
//            when().
//                delete(rootUrl + "/lists" + "/" + aGuid).
//            then().assertThat().
//                statusCode(204);

        payloadApi.deleteList(aGuid);
        Assert.assertEquals(204, payloadApi.getLastStatusCode());

        // GET it to check it was delete
//        RestAssured.
//            given().
//                auth().preemptive().
//                basic("admin", "password").
//            when().
//                get(rootUrl + "/lists" + "/" + aGuid).
//            then().assertThat().
//                statusCode(404);

        payloadApi.getList(aGuid);
        Assert.assertEquals(404, payloadApi.getLastStatusCode());
    }


    @Test
    public void getListsAsXML(){

        /**
         * Exercise: handle XML and JSON
         *
         * Thus far our API and abstractions have used JSON
         * But we also want to be able to test XML
         *
         * Amend the abstraction to support sending and receiving
         * both XML and JSON as required
         *
         */

        // RestAssured.proxy("localhost", 8080);

        String aListName = UUID.randomUUID().toString();

        // Create a list to make sure something is there
//        RestAssured.
//                given().
//                    auth().preemptive().
//                    basic("admin", "password").
//                    body(String.format("{title : '%s'}", aListName)).
//                when().
//                    post(rootUrl + "/lists").
//                then().assertThat().
//                statusCode(201);

        api.setBasicAuthentication("admin", "password");
        payloadApi.postLists(aListName);
        Assert.assertEquals(201, payloadApi.getLastStatusCode());



        api.setHeader("Accept", "application/xml");

        final ListsCollectionResponse lists = payloadApi.getLists();
        Assert.assertEquals(200, payloadApi.getLastStatusCode());
        Assert.assertEquals("application/xml", payloadApi.getLastResponse().getHeader("content-type"));

//        final Response response = RestAssured.
//            given().
//                auth().preemptive().
//                basic("admin", "password").
//                accept("application/xml").
//            when().
//                get(rootUrl + "/lists").
//            andReturn();
//
//        Assert.assertEquals(200, response.getStatusCode());


        api.clearHeaders();

        boolean found=false;
        for(ListPayload list : lists.lists){
            if(list.title.contentEquals(aListName)){
                found=true;
                break;
            }
        }
        //List<String> listnames = response.xmlPath().getList("lists.list.title");

        Assert.assertTrue(found);


    }



    /*
        Suggested Exercises:

        - does your API abstraction support sending messages through a proxy?
            - if not then add that capability into your API to make debugging easier.
        - add a test to allow you to send XML when the Content-Type header of the request is "application/xml"
        - refactor and tidy up your abstraction layers
        - look through the RestAssuredBasicsTest and copy across the Users tests
        - expand your abstraction layers to cover Users as well as lists
        - can you replace your use of RestAssured with the HTTP sending abstractions?
            - for flexibility it would be useful to be able to have an API backed by RestAssured
              and an api backed by HTTP and possibly switch between them
            - if you used RestAssured for your object serialising and deserialising then this
              would cause you to introduce another marshalling technology
            - see the examples in the 'marshalling' package
        - can you allow authentication via an API key?
        - using an Admin user, delete a created user
        - GET a user and find their API Key, then use this to access the system instead of the username/password
        - amend a user's API key and check that you can still access the system using this key
        - change a user's password

     */
}
