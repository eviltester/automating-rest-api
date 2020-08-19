package uk.co.compendiumdev.advancedrestautomating.examples.http;

import com.google.gson.Gson;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.compendiumdev.advancedrestautomating.examples.ServerConfig;

import java.io.IOException;
import java.util.List;


/**
 * A simple set of tests to demonstrate Apache HTTP Client diretly with GSON
 * this does not use RestAssured at all
 */
public class GsonMarshallingHttpClientsExampleTest {

    String rootUrl = ServerConfig.DEFAULT_SERVER_ROOT;

    @Before
    public void configForTest(){

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
    }


    private class AListsCollectionResponse{
        public List<AListPayload> lists;
    }

    /**
     * Marshall and Unmarshall to objects with Gson
     * REST Assured is now being used as a 'transport' layer
     */
    @Test
    public void createListWithJSON() throws IOException, AuthenticationException {

        // Create the HTTP Client

        /* configure the default credentials - note this can also be done on a request basis
          - note this doesn't use pre-emptive auth
          */
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials
                = new UsernamePasswordCredentials("admin", "password");
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);


        // configure the proxy - remember to also enable setRoutePlanner in client creation
//        HttpHost proxy = new HttpHost("localhost", 8080, "http");
//        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);


        CloseableHttpClient client = HttpClientBuilder.create().
                setDefaultCredentialsProvider(credentialsProvider).
//                setRoutePlanner(routePlanner).
                build();

        // GET the lists
        HttpResponse getLists = client.execute(new HttpGet(rootUrl + "/lists"));
        Assert.assertEquals(200, getLists.getStatusLine().getStatusCode());

        final AListsCollectionResponse lists = new Gson().fromJson(
                                                EntityUtils.toString(getLists.getEntity()),
                                                    AListsCollectionResponse.class);

        // CREATE A New List
        int currentNumberOfLists = lists.lists.size();

        final AListPayload payloadObject = new AListPayload("my list title");
        final String payload = new Gson().toJson(payloadObject);



        HttpPost postrequest = new HttpPost(rootUrl + "/lists");

        /* NOTE: I could add the credentials on a request level and this uses
         pre-emptive auth
         */
        // postrequest.addHeader(new BasicScheme().authenticate(credentials, postrequest, null));

        postrequest.addHeader(HttpHeaders.ACCEPT, "application/json");

        // set the body of the request
        postrequest.setEntity(new StringEntity(payload));


        HttpResponse response = client.execute(postrequest);

        Assert.assertEquals(201, response.getStatusLine().getStatusCode());

        Header[] headers = response.getHeaders(HttpHeaders.LOCATION);

        payloadObject.guid  = headers[0].getValue().replace("/lists/","");



        // GET amended Lists
        HttpResponse getAmendedLists = client.execute(new HttpGet(rootUrl + "/lists"));

        final AListsCollectionResponse amendedLists = new Gson().fromJson(
                                                EntityUtils.toString(getAmendedLists.getEntity()),
                                                AListsCollectionResponse.class);

        Assert.assertTrue(String.format(
                "expected increase from %d to %d", currentNumberOfLists, currentNumberOfLists+1),
                amendedLists.lists.size() == currentNumberOfLists + 1
        );

        Assert.assertTrue(foundDesiredList(payloadObject, amendedLists));

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

        - re-create some of the RestAssured tests using Gson and HttpClient directly
        - refactor the HTTP client into local methods to make sending requests and creating connections easier

     */
}
