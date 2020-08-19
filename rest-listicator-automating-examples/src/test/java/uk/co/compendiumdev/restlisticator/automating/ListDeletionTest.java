package uk.co.compendiumdev.restlisticator.automating;

import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.compendiumdev.restlisticator.payloads.ListPayload;


public class ListDeletionTest {

    RestListicatorApi api = new RestListicatorApi();
    ListPayload listToCreate;
    ListPayload createdListResponse;

    // note to use a proxy add
    // RestAssured.proxy("localhost", 8080);

    @Before
    public void createAList(){

        String title = "this is my list to delete it is yes " + System.currentTimeMillis();

        listToCreate = ListPayload.builder().
                with().
                title(title).
                description("description of my list").
                build();

        api.sendContentAsXML();
        api.acceptXML();

        Response response = api.createList(ApiUser.getDefaultAdminUser(), listToCreate);

        ApiResponse apiResponse = new ApiResponse(response);

        Assert.assertEquals(201, apiResponse.getStatusCode());

        // store created details for use in test code below
        createdListResponse = apiResponse.asListsPayload().getLists().get(0);
    }

    @Test
    public void canDeleteAList(){

        ApiResponse apiResponse = new ApiResponse(
                                        api.deleteList(
                                                ApiUser.getDefaultAdminUser(),
                                                createdListResponse.getGuid()
                                        ));

        Assert.assertEquals(204,apiResponse.getStatusCode());

        ApiResponse failToGet = new ApiResponse(
                                    api.getList(
                                            createdListResponse.getGuid()
                                    ));

        Assert.assertEquals(404,failToGet.getStatusCode());
    }
}
