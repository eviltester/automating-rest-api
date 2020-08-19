---

# SECTION - ABSTRACTIONS

---

## Dijkstra - On Abstractions

> The purpose of abstraction is not to be vague, but to create a new semantic level in which one can be absolutely precise

- Edsger Dijkstra (as part of his ACM Turing Lecture on 1972: the Humble Programmer
- [cs.utexas.edu/~EWD/transcriptions/EWD03xx/EWD340.html](https://www.cs.utexas.edu/~EWD/transcriptions/EWD03xx/EWD340.html)

---

## Any Abstractions Here?

~~~~~~~~
    @Test
    public void options(){
        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
            when().
                options("http:4567/listicator/lists").
            then().
            assertThat().
                statusCode(200).
                header("Allow",
                "GET, POST, PUT");
    }
~~~~~~~~

---

## Abstractions Present Were

- wrapper around HTTP messaging
- Assertion abstractions
- Given/When/Then

No Abstractions for:

- our Domain
- our API
- our Environments

---

## Writing at the REST Assured Semantic Level

We want to:

- write at the level of the test
- our domain
- our business language
- our physical model

To make it:

- easy to maintain
- code completion to help writing
- easy to read and understand

---

## REST Assured

- is a very capable library
- has a lot of advanced functionality ([see docs](https://github.com/rest-assured/rest-assured/wiki/Usage))
- has added features for 'reuse' e.g. reusing validation rules, static configuration
- is very easy to start using
- great for Automating Tactically
- can impact your ability to create effective abstractions if you use all its features at the `@Test` level

Strategically we might want a mix of libraries and abstractions.


---

## What Abstractions might we want?

- What might we want?
- What have you used before?

_Discuss_

---

## Possible Abstractions

- Request Payloads
- Response Payloads
- Environment/Server classes
- User - representation, actions, workflows
- Listicator Api
    - e.g. `getLists()`, `getList(aGUID)`,
- HTTP Abstractions - remove dependency on RESTAssured
- DSL for making testing easier?

---

## Example Abstraction - Constants

- Constants for server urls
- Variable names

~~~~~~~~
public class ServerConfig {

    public static final String DEFAULT_SERVER_ROOT = 
            "http://localhost:4567/listicator";

    // public version on heroku - currently different version
    //public static final String DEFAULT_SERVER_ROOT 
    //        = "http://rest-list-system.herokuapp.com";

}
~~~~~~~~

- _This is as simple as you can get, but needs code changes to configure_
- _would probably want to wrap in a class with constructor to set by environment defaults etc._

---

## Example Abstraction - Request & Response Payloads

Class to serialise/deserialise responses and request payloads.

~~~~~~~~
package uk.co.compendiumdev.restlisticator.payloads;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="list")
public class ListPayload {
    private String title;
    private String guid;
    private String description;

    public String getGuid() { return guid;}
    public String getTitle() { return title; }
    public String getDescription() {return description; }
    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description)
             {this.description = description;}
    public void setGuid(String guid) {this.guid = guid;}
}
~~~~~~~~

---

### Why Request and Response Payloads?

- easily create/parse responses
- support json/xml
- no more String concatenation etc.
- multiple payload objects for different config
- seperate locial domain from physical messages

---

### Payload Builders

- normal 'builder' pattern to make payload creation easier

~~~~~~~~
public class ListPayloadBuilder {
    private ListPayload listPayload;

    public static ListPayloadBuilder create(){
        return new ListPayloadBuilder();
    }
    public ListPayloadBuilder(){ listPayload = 
                  new ListPayload(); }

    public ListPayload build() {return listPayload;}
    public ListPayloadBuilder with() { return this; }
    public ListPayloadBuilder and() { return this; }

    public ListPayloadBuilder title(String title) {
        listPayload.setTitle(title);
        return this;
    }
    // etc.    
}
~~~~~~~~

---

## Example Abstraction - Environment/Server classes

- make it easy to configure environment to run tests against

~~~~~~~~
public class RestListicatorServer {
    private final String host;
    private final int port;
    private String apiroot="";
    private scheme = "http";

    public RestListicatorServer(
                            final String httpHost, 
                            final int port, 
                            final String apipath) {
        this.host = host;
        this.port = port;
        apiroot = apipath;
    }

    public String getHTTPHost() {
        return String.format("%s://%s:%d/%s", 
                    scheme, host, port, apiroot);
    }
}
~~~~~~~~

---

## Example Abstraction - User - representation, actions, workflows

- a very high level abstraction to model the user and actions a user can make
- would delegate off to an API abstraction to do the work
~~~~~~~~
User user = new User("admin","password");
user.createList("my new list");
~~~~~~~~

---

## Example Abstraction - Listicator Api

- e.g. `getLists()`, `getList(aGUID)` make it easier to send to API

~~~~~~~~
public class RestListicatorApi {
    // ...

    public RestListicatorApi(RestListicatorServer server){
        this.server = server;
        this.contentType = CONTENT_IS_JSON;
        this.accept = CONTENT_IS_JSON;
    }

    public Response getLists() {
        return RestAssured.
                given().
                    contentType(contentType).
                    accept(accept).
                when().
                    get(server.getHTTPHost() + "/lists").
                andReturn();
    }
}
~~~~~~~~

---

## Example Abstraction - HTTP Abstractions - remove dependency on RESTAssured

- seen previously in "Alternative HTTP" section

---

## Example Abstraction - DSL for making testing easier

e.g. from RestMud Test Automating, make it easier to write test code

~~~~~~~~
dsl.walkthroughStep("\n### The Bomb Puzzle\n");

successfully(walkthrough("", "score", ""));
successfullyVisitRoom("2", walkthrough(
            "I will solve the bomb puzzle", "go", "n"));
successfully(walkthrough("", "examine", "smallroundthing"));
successfully(walkthrough("", "take", "smallroundthing"));
successfully(walkthrough(
    "I could just wait, but I'll get points if I defuse it", 
        "defuse", "smallroundthing"));
ResultOutput result = 
        successfully(walkthrough("", "score", ""));
int score = result.users.get(0).score.intValue();
Assert.assertEquals(100,score);
~~~~~~~~

---

## Abstractions are there too...

- make it easy to write code, harness code completion
- make it easy to maintain, single responsibility
- write at the appropriate level of abstraction
    - easy to read and understand
    - easy to expand
- avoid clutter (too many methods on one class)
- etc.

> The purpose of abstraction is not to be vague, but to create a new semantic level in which one can be absolutely precise
- Edsger Dijkstra

---

## Example Abstractions - REST Listicator Automating

- Server abstractions
- Request Payload Abstractions
- Request Payload Factory Abstractions
- Response Payload Abstractions
- JSON & XML parsing with Payload Abstractions
- Static API wrappers around a dynamic API abstraction
- Basic RestAssured Usage

[github.com/eviltester/rest-listicator-automating-examples](https://github.com/eviltester/rest-listicator-automating-examples)

---

## Exercises - Expand the Abstractions Test

_remember_

- keep existing code working
- refactor in small steps
- this is a 'real world exercise' - it is not 'easy'
- there are no 'right' answers
- there are no 'final' answers - keep refactoring
- if it works, its probably "good enough"

---

## Exercises - Expand the Abstractions Test

- create a `RestListicatorServer` abstraction
   - to avoid hardcoding `"http://localhost:4567"`
- create an End Points abstraction
   - to avoid hardcoding `"\heartbeat"`, etc.
- separate API calls from Assertions
- perhaps create Http level abstractions as well as API abstractions
- use Payload Objects instead of hardcoded message strings
- perhaps create payload builder objects
- perhaps add random data generators

For 'an answer' see `package`

- `uk.co.compendiumdev.advancedrestautomating.exercises.abstractions;` in the `code-answers` folder
---

## Exercises - Improve the custom HTTP Abstraction

- headers on response is current a map that is accessible by anything
   - create a 'getHeader' method on response which is not case sensitive e.g. `getHeader("location")`, `getHeader("Location")` should both return the "Location" header details if present

---

## Exercises - Create an Abstraction for the RestListicator API

- Create a `RestListicatorApi` class
- it should have methods like:
    - `getLists()` which returns a `ListCollectionResponse`
    - `getList(guid)` which returns a `ListPayload` for a specific list
    - `amendList(aList)` which takes a `ListPayload` and returns a `ListPayload` representing the changed list
    - the `RestListicatorApi` should store the last response and allow the user to retrieve it e.g. `getLastResponse()` would return an ` HttpResponse` object to allow checking status codes etc.