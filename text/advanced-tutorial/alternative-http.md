---

# SECTION - ALTERNATIVE HTTP STRATEGIES

---

## REST Assured provides HTTP abstractions

~~~~~~~~
final Response response = 
    RestAssured.
        given().
            accept("application/xml").
        when().
            get(rootUrl + "/lists").andReturn();
~~~~~~~~

- `Response` is a REST Assured HTTP response
- `accept` creates and HTTP header
- `get` issues an `HTTP` `GET` request

---

## REST Assured wraps Apache httpclient

- REST Assured usess Apache HTTP Client
- we could use HTTP Client directly
- if we wanted to avoid external libraries we could use `java.net` directly
    - but we would have to write more code

Why?

- more control
- fewer dependencies
- avoid mixing HTTP with `@Test` code
- separation of Abstraction Layers

---

## Apache HTTPClient

Create an HTTP Client

~~~~~~~~
CredentialsProvider credentialsProvider = 
                    new BasicCredentialsProvider();
UsernamePasswordCredentials credentials
     = new UsernamePasswordCredentials("admin", "password");
credentialsProvider.
        setCredentials(AuthScope.ANY, credentials);

CloseableHttpClient client = HttpClientBuilder.create().
        setDefaultCredentialsProvider(credentialsProvider).
        build();
~~~~~~~~

see `GsonMarshallingHttpClientsExampleTest`

---

## Use Client to issue requests

~~~~~~~~
HttpResponse getLists = 
        client.execute(new HttpGet(rootUrl + "/lists"));
Assert.assertEquals(200, 
        getLists.getStatusLine().getStatusCode());
~~~~~~~~

Get Body

~~~~~~~~
EntityUtils.toString(getLists.getEntity()
~~~~~~~~

see `GsonMarshallingHttpClientsExampleTest`

---

## Converting Body Json to object using GSON

~~~~~~~~
HttpResponse getLists = 
        client.execute(new HttpGet(rootUrl + "/lists"));
Assert.assertEquals(200, 
        getLists.getStatusLine().getStatusCode());

final AListsCollectionResponse lists = 
        new Gson().fromJson(
            EntityUtils.toString(getLists.getEntity()),
                AListsCollectionResponse.class);
~~~~~~~~

see `GsonMarshallingHttpClientsExampleTest`

---

## Example Post Request with authentication

~~~~~~~~
HttpPost postrequest = new HttpPost(rootUrl + "/lists");

/* this uses pre-emptive auth */
postrequest.addHeader(
    new BasicScheme().
        authenticate(credentials, postrequest, null));

postrequest.addHeader(HttpHeaders.ACCEPT, 
                     "application/json");

// set the body of the request
postrequest.setEntity(new StringEntity(payload));

HttpResponse response = client.execute(postrequest);
~~~~~~~~

see `GsonMarshallingHttpClientsExampleTest`

---

## Example getting headers from response

~~~~~~~~
Header[] headers = response.getHeaders(HttpHeaders.LOCATION);
payloadObject.guid  =
     headers[0].getValue().replace("/lists/","");
~~~~~~~~

see `GsonMarshallingHttpClientsExampleTest`

---

## Demo - Apache HTTPClient

see `GsonMarshallingHttpClientsExampleTest`

---

## Custom HTTP Abstractions

- it can be more work to create a Custom HTTP Abstraction
- benefits:
   - no additional dependencies
   - can wrap other HTTP libraries
   - no 'library' classes in tests, HTTP Domain objects instead
   - allows swapping between HTTP or REST Libraries without impacting test code

---

## Example

`package uk.co.compendiumdev.http`

- a crude HTTP library uses `Java.net` classes
- `HttpMessageSender` a wrapper which can send requests
- `CanSendHttpRequests` interface for `getLastRequest` and `send` and `getLastResponse`
- `HttpRequestSender` - actually sends requests (using `java.net`)
- `HttpRequestDetails` represents a logical HTTP Request
- `HttpResponseDetails` represents a logical HTTP Response

'logical' classes can be used in Test, then physical libraries can be replaced or switched between, provided a wrapper around the libary is created which implements `CanSendHttpRequests`

---

## Example Usage

~~~~~~~~
HttpMessageSender sender = 
        new HttpMessageSender(
                ServerConfig.DEFAULT_SERVER_ROOT);

sender.setHeader(sender.HEADER_ACCEPT, "application/json");
sender.setBasicAuth("admin", "password");

// GET the lists
HttpResponseDetails getLists = 
                sender.get(rootUrl + "/lists");
Assert.assertEquals(200, getLists.statusCode);

final AListsCollectionResponse lists =
         new Gson().fromJson(
                getLists.body,
                AListsCollectionResponse.class);

int currentNumberOfLists = lists.lists.size();
~~~~~~~~

---

## Example Usage

~~~~~~~~
final AListPayload payloadObject = 
                   new AListPayload("my list title");
final String payload = new Gson().toJson(payloadObject);
final HttpResponseDetails response = 
               sender.post(rootUrl + "/lists", payload);
Assert.assertEquals(201, response.statusCode);

String header = response.getHeaders().get("Location");

payloadObject.guid  = header.replace("/lists/","");
~~~~~~~~
---

## Demo - Custom Http Client using java.net

see `GsonMarshallingCustomHttpExampleTest`

---

## Discuss Pros and Cons of Http Level Abstractions

Discuss

---

## Exercise

- Create a constructor for `HttpMessageSender` which takes a `CanSendHttpRequests` to allow switching between HTTP transport libraries
- Create an implementation of `CanSendHttpRequests` which uses RestAssured as the implementor rather than java.net
- Create an implementation of `CanSendHttpRequests` which uses Apache `HttpClient` as the implementor rather than java.net

