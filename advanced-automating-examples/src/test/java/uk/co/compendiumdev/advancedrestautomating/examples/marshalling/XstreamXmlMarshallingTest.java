package uk.co.compendiumdev.advancedrestautomating.examples.marshalling;

import com.thoughtworks.xstream.XStream;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.compendiumdev.advancedrestautomating.examples.ServerConfig;

import java.util.List;

/**
 * A simple example of using JAXB directly instead of RestAssured for processing the returned XML
 * against the RestListicator system
 *
 * You can see that RestAssured actually does quite a bit behing the scenes
 */
public class XstreamXmlMarshallingTest {

    String rootUrl = ServerConfig.DEFAULT_SERVER_ROOT;

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

    private static class AListsCollectionResponse{
        public List<AListPayload> lists;
    }

    XStream xstream;

    @Before
    public void configForTest(){

        xstream = new XStream();

        xstream.allowTypesByWildcard(new String[] {
                "uk.co.compendiumdev.advancedrestautomating.**"
        });


        //xstream.alias("user", UserPayload.class);
        //xstream.alias("users", UserListPayload.class);
        xstream.alias("lists", AListsCollectionResponse.class);
        xstream.addImplicitCollection(AListsCollectionResponse.class, "lists");
        xstream.alias("list", AListPayload.class);
//        xstream.alias("toggles", ListOfFeatureTogglesPayload.class);
//        xstream.addImplicitCollection(ListOfFeatureTogglesPayload.class, "toggles");
//        xstream.alias("toggle", FeatureTogglePayload.class);


//        RestAssured.proxy("localhost", 8080);
    }






     @Test
     public void createListWithXML(){

         final Response response = RestAssured.
                 given().
                    accept("application/xml").
                 when().
                    get(rootUrl + "/lists").andReturn();

         AListsCollectionResponse lists = (AListsCollectionResponse) xstream.fromXML(response.body().asString());


         int currentNumberOfLists = lists.lists.size();

         AListPayload desiredList = new AListPayload("my list title");

         // xstream does not add the header by default (as far as I am aware)
         String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n";
         String payload = header + xstream.toXML(desiredList);

         final Response createdResponse = RestAssured.
                given().
                    auth().preemptive().
                    basic("admin", "password").
                    contentType("application/xml").
                 accept("application/xml").
                body(payload).
         when().
         post(rootUrl + "/lists").andReturn();

         Assert.assertEquals(201, createdResponse.getStatusCode());

         // find the GUID that was assigned
         desiredList.guid  = createdResponse.getHeader("Location").replace("/lists/","");




         // Now get the lists again

         final Response amendedListsResponse = RestAssured.
                 given().
                 accept("application/xml").
                 when().
                 get(rootUrl + "/lists").andReturn();

         final AListsCollectionResponse amendedLists =
                 (AListsCollectionResponse) xstream.fromXML(amendedListsResponse.body().asString());


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


}
