package uk.co.compendiumdev.restlisticator.automating;

import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.compendiumdev.restlisticator.payloads.ListPayload;
import uk.co.compendiumdev.restlisticator.payloads.ListsPayload;


public class ListRetrievalTest {

    RestListicatorApi api = new RestListicatorApi();
    ListPayload listToCreate;
    ListPayload createdListResponse;

    // note to use a proxy add
    // RestAssured.proxy("localhost", 8080);

    @Before
    public void createAList(){

        String title = "this is my list it is yes " + System.currentTimeMillis();

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
    public void canGetLists(){


        ApiResponse apiResponse = new ApiResponse(api.getLists());

        ListsPayload lists = apiResponse.asListsPayload();

        boolean foundIt = false;

        for(ListPayload aList : lists.getLists()){

            if(aList.getTitle().contentEquals(listToCreate.getTitle())){
                Assert.assertEquals("description of my list", aList.getDescription());
                foundIt=true;
                break;
            }
        }

        Assert.assertTrue("Could not find list I created", foundIt);
    }

    @Test
    public void canGetSpecificList(){

        ApiResponse apiResponse = new ApiResponse(api.getList(createdListResponse.getGuid()));

        ListPayload theReturnedList = apiResponse.asListPayload();

        Assert.assertEquals(listToCreate.getTitle(), theReturnedList.getTitle());
        Assert.assertEquals(listToCreate.getDescription(), theReturnedList.getDescription());
        Assert.assertEquals(createdListResponse.getGuid(), theReturnedList.getGuid());
        Assert.assertEquals(createdListResponse.getCreatedDate(), theReturnedList.getCreatedDate());
        Assert.assertEquals(createdListResponse.getAmendedDate(), theReturnedList.getAmendedDate());
    }
}
