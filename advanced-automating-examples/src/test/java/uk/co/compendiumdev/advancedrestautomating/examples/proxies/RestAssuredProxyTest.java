package uk.co.compendiumdev.advancedrestautomating.examples.proxies;

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
public class RestAssuredProxyTest {

    String rootUrl = ServerConfig.DEFAULT_SERVER_ROOT;

    @Before
    public void setupProxy() {

        /*
            Exercise - enable the line below when your proxy is running
            change the port if necessary based on your proxy settings.

            Then run the test. You should see all the requests in your proxy.
         */

        //RestAssured.proxy("localhost", 8080);

        /*
            Exercise - add an @Before to setup a proxy in RestAssuredBasicsTest
            and run the full set of test methods in that class
         */
        /*
            Exercise - experiment in the proxy by replaying requests
            or fuzzing requests
         */
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


}
