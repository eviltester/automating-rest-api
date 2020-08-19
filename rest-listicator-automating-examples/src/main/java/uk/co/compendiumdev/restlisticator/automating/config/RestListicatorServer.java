package uk.co.compendiumdev.restlisticator.automating.config;


public class RestListicatorServer {
    private final String host;
    private final int port;
    private static String HTTPHost="localhost";
    private String scheme="http";
    private final String originalPortFormatTemplate=":%d";
    private String portFormatTemplate=originalPortFormatTemplate;
    private String apiroot="";
    private String pathFormatTemplate="";
    private final String schemeHostTemplate="%s://%s";

    // TODO support configuration from environment variables and properties


    public RestListicatorServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public RestListicatorServer(final String httpHost, final int port, final String apipath) {
        this(httpHost, port);
        setApiRoot(apipath);
    }

    public static RestListicatorServer getDefault() {
        //return new RestListicatorServer(HTTPHost, 4567, "listicator");
        return new RestListicatorServer("rest-list-system.herokuapp.com", 443 ,"listicator").setScheme("https").withNoPort();
    }


    public String getHTTPHost() {

        String template = String.format("%s%s%s", schemeHostTemplate, portFormatTemplate, pathFormatTemplate);

        if(portFormatTemplate.length()>0) {
            return String.format(template, scheme, host, port, apiroot);
        }else{
            return String.format(template, scheme, host, apiroot);
        }

    }

    public RestListicatorServer setScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    public RestListicatorServer withNoPort() {
        portFormatTemplate = "";
        return this;
    }

    public RestListicatorServer setApiRoot(final String apiroot) {
        this.apiroot = apiroot;

        if(apiroot==null || apiroot.length()==0){
            pathFormatTemplate = "";
        }else {
            pathFormatTemplate = "/%s";
        }

        return this;
    }
}
