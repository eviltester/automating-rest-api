package uk.co.compendiumdev.restlisticator.config;

import org.junit.Assert;
import org.junit.Test;
import uk.co.compendiumdev.restlisticator.automating.config.RestListicatorServer;

public class RestListicatorServerTest {

    @Test
    public void canConstructHTTP_root_URL_string(){
        RestListicatorServer server = new RestListicatorServer("localhost",1234);
        Assert.assertEquals("http://localhost:1234", server.getHTTPHost());
    }

    @Test
    public void canConstructDefault(){
        RestListicatorServer server = RestListicatorServer.getDefault();
        Assert.assertTrue(server.getHTTPHost().startsWith("https://"));
        Assert.assertTrue(server.getHTTPHost().endsWith("/listicator"));
    }

    @Test
    public void canConstructToUseHTTPs(){
        RestListicatorServer server = new RestListicatorServer("localhost",1234).setScheme("https");
        Assert.assertEquals("https://localhost:1234", server.getHTTPHost());
    }

    @Test
    public void canExcludePortAndApiPath(){
        RestListicatorServer server = new RestListicatorServer("localhost",1234).setScheme("https").withNoPort().setApiRoot("");
        Assert.assertEquals("https://localhost", server.getHTTPHost());
    }

    @Test
    public void canExcludePort(){
        RestListicatorServer server = new RestListicatorServer("localhost",1234).setScheme("https").withNoPort();
        Assert.assertEquals("https://localhost", server.getHTTPHost());
    }

    @Test
    public void canRemoveApiPath(){
        RestListicatorServer server = RestListicatorServer.getDefault().setScheme("https").setApiRoot("");
        Assert.assertTrue(server.getHTTPHost().startsWith("https://"));
        final String noslash = server.getHTTPHost().replace("https://", "");
        Assert.assertFalse(noslash.contains("/"));
    }

}
