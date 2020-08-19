package uk.co.compendiumdev.advancedrestautomating.examples.basic;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.compendiumdev.advancedrestautomating.examples.ServerConfig;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * A simple set of tests to demonstrate RESTAssured
 * against the RestListicator system
 */
public class RestAssuredBasicsTest {

    //String rootUrl = "http://localhost:4567/listicator";
    String rootUrl = ServerConfig.DEFAULT_SERVER_ROOT;

    @Before
    public void configForTest(){

    }

    @Test
    public void canCheckThatServerIsRunning(){

        RestAssured.
            get(rootUrl + "/heartbeat").
                then().assertThat().
            statusCode(200);
    }

    @Test
    public void canSetHeaders(){

        RestAssured.
            given().
                header("User-Agent"," Mozilla/5.0 (Windows NT 10.0; Win64; x64)").
            get(rootUrl + "/heartbeat").
            then().assertThat().
                statusCode(200);
    }

    @Test
    public void options(){
        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
            when().
                options(rootUrl + "/lists").
            then().
            assertThat().
                statusCode(200).
                header("Allow",
                "GET, POST, PUT");
    }

    @Test
    public void headOnListNotAllowed(){

        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
            when().
                head(rootUrl + "/lists").
            then().
                assertThat().
                statusCode(405);
    }

    @Test
    public void createList(){

        int currentNumberOfLists = RestAssured.
                when().
                get(rootUrl + "/lists").
                then().assertThat().
                statusCode(200).extract().body().path("lists.size");

        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
                body("{title : 'my title'}").
            when().
                post(rootUrl + "/lists").
            then().assertThat().
                statusCode(201);

        int newNumberOfLists = RestAssured.
                when().
                get(rootUrl + "/lists").
                then().assertThat().
                statusCode(200).extract().body().path("lists.size");

        Assert.assertTrue(String.format(
                    "expected increase from %d to %d", currentNumberOfLists, currentNumberOfLists+1),
                newNumberOfLists == currentNumberOfLists + 1
                );

    }

    @Test
    public void createAFullListWithPut(){

        int currentNumberOfLists = RestAssured.
                when().
                get(rootUrl + "/lists").
                then().assertThat().
                statusCode(200).extract().body().path("lists.size");

        String aGuid = UUID.randomUUID().toString();
        String aTitle = "My Put Title";
        String aDescription = "My mini description";
        String createdDate="2018-12-08-13-50-40";
        String amendedDate=createdDate;

        String message = String.format("{guid:'%s',title:'%s',description:'%s', createdDate:'%s', amendedDate:'%s'}",
                    aGuid, aTitle, aDescription, createdDate, amendedDate);

        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
                body(message).
            when().
                put(rootUrl + "/lists").
            then().assertThat().
                statusCode(201);

        int newNumberOfLists = RestAssured.
                when().
                    get(rootUrl + "/lists").
                then().assertThat().
                statusCode(200).extract().body().path("lists.size");

        Assert.assertTrue(String.format(
                "expected increase from %d to %d", currentNumberOfLists, currentNumberOfLists+1),
                newNumberOfLists == currentNumberOfLists + 1
        );

    }

    @Test
    public void amendAList(){

        String aGuid = UUID.randomUUID().toString();
        String messageBody = String.format("{title : 'my oldtitle', guid:'%s'}", aGuid);

        // CREATE THE LIST
        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
                body(messageBody).
            when().
                post(rootUrl + "/lists").
            then().assertThat().
                statusCode(201);

        // GET it to check it was added
        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
            when().
                get(rootUrl + "/lists" + "/" + aGuid).
            then().assertThat().
                contentType("application/json").
                body("title", equalTo("my oldtitle")).
                statusCode(200);


        // AMEND THE LIST

        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
                body("{title : 'my new title'}").
            when().
                post(rootUrl + "/lists" + "/" + aGuid).
            then().assertThat().
                statusCode(200);

        // GET it to check it was amended
        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
            when().
                get(rootUrl + "/lists" + "/" + aGuid).
            then().assertThat().
                contentType("application/json").
                body("title", equalTo("my new title")).
                statusCode(200);

    }

    @Test
    public void deleteAList(){

        String aGuid = UUID.randomUUID().toString();
        String messageBody = String.format("{title : 'my only title', guid:'%s'}", aGuid);

        // CREATE THE LIST
        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
                body(messageBody).
            when().
                post(rootUrl + "/lists").
            then().assertThat().
                statusCode(201);

        // GET it to check it was added
        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
            when().
                get(rootUrl + "/lists" + "/" + aGuid).
            then().assertThat().
                contentType("application/json").
                body("title", equalTo("my only title")).
                statusCode(200);


        // Delete THE LIST

        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
                body("{title : 'my new title'}").
            when().
                delete(rootUrl + "/lists" + "/" + aGuid).
            then().assertThat().
                statusCode(204);

        // GET it to check it was delete
        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
            when().
                get(rootUrl + "/lists" + "/" + aGuid).
            then().assertThat().
                statusCode(404);

    }


    @Test
    public void getUsersAsXML(){

        final Response response = RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
                accept("application/xml").
            when().
                get(rootUrl + "/users").
            andReturn();

        Assert.assertEquals(200, response.getStatusCode());

        System.out.println(
                response
                        .getBody().prettyPrint());

        List<String> usernames = response.xmlPath().getList("users.users.user.username");

        Assert.assertTrue(usernames.contains("superadmin"));
        Assert.assertTrue(usernames.contains("admin"));
        Assert.assertTrue(usernames.contains("user"));


        Assert.assertEquals("superadmin",
                response.xmlPath().getString("users.users.user[0].username"));



    }

    @Test
    public void getUsersAsJSON(){

        final Response response =
                RestAssured.
                given().
                    auth().preemptive().
                    basic("admin", "password").
                    accept("application/json").
                when().
                get(rootUrl + "/users")
                    .andReturn();

        Assert.assertEquals(200, response.getStatusCode());

        System.out.println(
            response
                .getBody().prettyPrint());

        List<String> usernames = response.jsonPath().getList("users.username");

        Assert.assertTrue(usernames.contains("superadmin"));
        Assert.assertTrue(usernames.contains("admin"));
        Assert.assertTrue(usernames.contains("user"));

        Assert.assertEquals("superadmin",
                response.jsonPath().getString("users[0].username"));



    }

    @Test
    public void getUsersWithJSON(){

        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
                accept("application/json").
            when().
                get(rootUrl + "/users").
            then().
                body("users[0].username",
                        equalTo("superadmin")).
                and().body("users[1].username",
                        equalTo("admin"));
    }

    @Test
    public void getUsersWithXML(){

        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
                accept("application/xml").
            when().
                get(rootUrl + "/users").
            then().
                body("users.users.user[0].username",
                        equalTo("superadmin")).
                and().body("users.users.user[1].username",
                equalTo("admin"));
    }


    /*
        Suggested Exercises:

        - using an Admin user, create a new users
        - using an Admin user, delete a created user
        - GET a user and find their API Key, then use this to access the system instead of the username/password
        - amend a user's API key and check that you can still access the system using this key
        - change a user's password

     */
}
