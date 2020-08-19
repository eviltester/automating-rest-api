package uk.co.compendiumdev.advancedrestautomating.examples.marshalling;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.compendiumdev.advancedrestautomating.examples.ServerConfig;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

/**
 * A simple example of using JAXB directly instead of RestAssured for processing the returned XML
 * against the RestListicator system
 *
 * You can see that RestAssured actually does quite a bit behing the scenes
 */
public class RestAssuredXmlPathMarshallingTest {

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


     @Test
     public void createListWithXMLUsingJaxBAndXmlPath(){

         final Response response = RestAssured.
                 given().
                    accept("application/xml").
                 when().
                    get(rootUrl + "/lists").andReturn();


        // use XMLPath directly to convert the string to an object
         AListsCollectionResponse lists = new XmlPath(response.body().asString()).getObject(".", AListsCollectionResponse.class);

         int currentNumberOfLists = lists.lists.size();


         AListPayload desiredList = new AListPayload("my list title");

         // need to use Jaxb directly to convert object to xml
         StringWriter payloadWriter = new StringWriter();
         JAXB.marshal(desiredList, payloadWriter);
         String payload = payloadWriter.toString();

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

         AListsCollectionResponse amendedLists = new XmlPath(amendedListsResponse.body().asString()).getObject(".", AListsCollectionResponse.class);


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
