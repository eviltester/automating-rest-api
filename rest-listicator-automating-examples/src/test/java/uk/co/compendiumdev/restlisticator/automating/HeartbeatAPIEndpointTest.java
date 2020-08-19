package uk.co.compendiumdev.restlisticator.automating;

import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.Test;
import uk.co.compendiumdev.restlisticator.automating.config.RestListicatorServer;


public class HeartbeatAPIEndpointTest {

    @Test
    public void canCheckThatServerIsRunning(){

        RestListicatorServer server = RestListicatorServer.getDefault();
        //RestListicatorServer server = new RestListicatorServer("localhost",4567).setApiRoot("listicator");
        //RestAssured.proxy("localhost", 8888);

        RestAssured.
                get(server.getHTTPHost() + "/heartbeat").
                then().assertThat().
                statusCode(200);
    }


    @Test
    public void apiAbstractionToCheckThatServerIsRunning(){

        RestListicatorApi api = new RestListicatorApi();

        ApiResponse response = new ApiResponse(api.getHeartbeat());

        Assert.assertEquals(200, response.getStatusCode());
    }

}
