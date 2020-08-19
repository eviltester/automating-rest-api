---

# SECTION - BASICS

---

## Overview - Browser - Web Application

![Browser to Web Application](images/basicwebapp_orig.png)

---

## Example - A Web Application

- Diagram showing browser making GET, POST requests to Server

![A Web Application](images/googlesearch_orig.png)

---

## What is HTTP?

- Verbs - GET, POST, DELETE, PUT, HEAD, OPTIONS, PATCH
- URL (URI)
- Headers - cookies, accept formats, user agent, content of message, authentication, etc.
- Data contained in message body - Form, JSON, XML

---

## Example HTTP Request

use browser to GET http://compendiumdev.co.uk/apps/api/mock/reflect

Formatted for readability - headers are normally on one line.

~~~~~~~~
GET http://compendiumdev.co.uk/apps/api/mock/reflect HTTP/1.1
Host: compendiumdev.co.uk
Connection: keep-alive
Cache-Control: max-age=0
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64)
            AppleWebKit/537.36 (KHTML, like Gecko)
            Chrome/60.0.3112.90 Safari/537.36
Upgrade-Insecure-Requests: 1
Accept: text/html,application/xhtml+xml,
        application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
Accept-Encoding: gzip, deflate
Accept-Language: en-US,en;q=0.8
~~~~~~~~

---

## HTTP Requests - Human or System

- Human
    - user types URL into browser search bar (GET)
    - user submits form (POST)
- System
    -System automatically polls server for new content via JavaScript
        - [AJAX](https://en.wikipedia.org/wiki/Ajax_(programming)) (Asynchronous JavaScript and XML)
            -  [XHR](https://en.wikipedia.org/wiki/XMLHttpRequest) (XML HTTP Request)
        - GET / POST
        - often returns JSON

---

## View Browser Requests in Dev Tools Network Tab

![View Browser Requests in Dev Tools Network Tab](images/chrome_dev_tools_network_xhr.png)

---

## What is a Web API?

- Web Service
    - [w3.org/TR/ws-gloss](https://www.w3.org/TR/ws-gloss)
- API
    - [wikipedia.org/wiki/Application_programming_interface](https://en.wikipedia.org/wiki/Application_programming_interface)

Web Application with an interface designed for use by other software.

---

## Why an API?

- Other systems to access
- Customisation
- Mobile Apps often use API
    - bag a SNES classic

---

## JavaScript Using API Via AJAX/XHR

- AJAX/XHR requests have security protocols for same domain
- [JSONP](https://en.wikipedia.org/wiki/JSONP) for cross domain access
- Very often API is used under covers, e.g. a serverside script/app on same domain uses an API on server side rather than client side

---

## We Need Tools

Because Web Service designed for software, we need tools to access them.

---

## Tools

- [cURL](https://curl.haxx.se)
    - command line based
    - API examples often shown in cURL
    - recommended that you learn this eventually
    - [download](https://curl.haxx.se/dlwiz/)
- GUI Clients
    - [Postman](https://www.getpostman.com/)
    - [Insomnia](https://insomnia.rest/)

---

## What is a Web Service / Web Application?

- A web hosted HTTP accessed application without a GUI

---

## What is an API?

- Application Programming Interface designed for use by software

Note: error messages need to be human readable - useful to be machine parsable when automating

---

## Overview of Section - HTTP Requests and Responses

- HTTP Verbs - GET, POST, DELETE
- Headers
- Responses
    - Status Codes - e.g. 200, 404, 500
- This is the foundation for most web, HTTP, REST testing and automating.

---

## HTTP GET Request sent from Postman

~~~~~~~~
GET http://localhost:4567/heartbeat HTTP/1.1
cache-control: no-cache
Postman-Token: ddf30bfe-b7e2-4d3c-b478-1103a5a174e5
User-Agent: PostmanRuntime/6.2.5
Accept: */*
Host: localhost:4567
accept-encoding: gzip, deflate
Connection: keep-alive
~~~~~~~~

- important stuff: Verb (GET), Http version (1.1), User-Agent, Accept, Host, endpoint

---

### HTTP Response to Postman GET /heartbeat request

~~~~~~~~
HTTP/1.1 200 OK
Date: Thu, 17 Aug 2017 10:34:32 GMT
Content-Type: application/json
Transfer-Encoding: chunked
Server: Jetty(9.4.4.v20170414)
~~~~~~~~

- cURL response was same but content-type was `application/xml`
- important stuff: Status Code (200 OK), Http version (1.1), Date, Content-Type

---

### Automating with REST Assured

- https://github.com/rest-assured/rest-assured
- Java/Groovy library
- HTTP Abstraction
- Assertions Abstraction
- Given When Then Abstraction
- JSON Path and XML Path for adhoc assertions and extract
- Marshalling - Serialization/Deserialization

---

### Add REST Assured to pom.xml

~~~~~~~
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>3.2.0</version>
    <scope>test</scope>
</dependency>
~~~~~~~~

---

### GET request using REST Assured

~~~~~~~~
    String rootUrl = "http://localhost:4567/listicator";

    @Test
    public void canCheckThatServerIsRunning(){

        RestAssured.
            get(rootUrl + "/heartbeat").
            then().assertThat().
                statusCode(200);
    }
~~~~~~~~

see `RestAssuredBasicsTest.java`

---

## Basic HTTP Verbs

- [GET](https://tools.ietf.org/html/rfc7231#section-4.3.1)  - retrieve data
- [POST](https://tools.ietf.org/html/rfc7231#section-4.3.3)  amend/create from partial information
- [PUT](https://tools.ietf.org/html/rfc7231#section-4.3.4) - create or replace from full information
- [DELETE](https://tools.ietf.org/html/rfc7231#section-4.3.5) - delete items
- [OPTIONS](https://tools.ietf.org/html/rfc7231#section-4.3.7) - verbs available on this url

---

## References


- [W3c Standard](https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html)
- [IETF standard](https://tools.ietf.org/html/rfc7231)
- [httpstatuses.com](https://httpstatuses.com)
- http://www.restapitutorial.com/lessons/httpmethods.html

---

## User-Agent Header

- Often not sent when accessing an API
- Marks request as coming from a browser

~~~~~~~~
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64)
AppleWebKit/537.36 (KHTML, like Gecko)
Chrome/60.0.3112.90 Safari/537.36
~~~~~~~~

---

## Sending User-Agent Header with REST Assured

~~~~~~~~
@Test
public void canSetHeaders(){

    RestAssured.
        given().
            header("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64)").
        get(rootUrl + "/heartbeat").
        then().assertThat().
            statusCode(200);
}
~~~~~~~~

see `RestAssuredBasicsTest.java`

---

## Accept Header

- Defines the payload types that the receiver will accept
- If this was an API call it would likely return XML

~~~~~~~~
Accept: text/html,application/xhtml+xml,application/xml;
q=0.9,image/webp,image/apng,*/*;q=0.8
~~~~~~~~

Common values:

- `text/html`
- `application/json`
- `application/xml`

---

## Sending Accept Header with REST Assured

~~~~~~~~
  final Response response =
            RestAssured.
                given().
                    auth().preemptive().
                    basic("admin", "password").
                    accept("application/json").
                when().
                get(rootUrl + "/users")
                    .andReturn();
~~~~~~~~

see `RestAssuredBasicsTest.java` method `getUsersAsJSON`

---

## HTTP Status Codes

- 1xx Informational
    - 100 Continue
- 2xx Success
    - e.g. 200 OK
- 3xx Redirection
    - e.g. 301 Moved Permanently
- 4xx Client Error
    - e.g. 404 Not Found
- 5xx Server Error
    - e.g. 500 Internal Server Error


---

## Common HTTP Status Codes

| **Status Code**            | **Status Code** |
|----------------------------|-----------------|
|  200 OK | 405 Method Not Allowed    |
| 201 Created                            |   409 Conflict              |
| 301 Moved Permanently | 500 Internal Server Error |
| 307 Temporary Redirect | 501 Not Implemented |
| 400 Bad Request | 502 Bad Gateway |
| 401 Unauthorized| 503 Service Unavailable |
| 403 Forbidden | 504 Gateway Timeout |
| 404 Not Found | |

---

## HTTP Status code references

- https://httpstatuses.com/
- https://moz.com/blog/response-codes-explained-with-pictures
- https://http.cat/
- https://httpstatusdogs.com/

---

## Common HTTP Status codes in response to a GET

- **200** - OK, found the url, returned contents
- **301, 307, 308** - content has moved, new url in `location` header
- **404** - url not found
- **401** - you need to give me authorisation details see `WWW-Authenticate` header
- **403** - url probably exists but you are not allowed to access it

---

## Basic Auth Header

- This application uses Basic Auth Authentication
- `Authorization` Header

e.g. `Authorization: Basic dXNlcjpwYXNzd29yZA==`

`dXNlcjpwYXNzd29yZA==` is base64 encoded "user:password"

see [base64decode.org](https://www.base64decode.org)

- cURL you need to add the header
- Postman & Insomnia use the Authorization and Auth tabs

---

## Basic Auth with REST Assured

~~~~~~~~
    RestAssured.
    given().
        auth().preemptive().
        basic("admin", "password").
        accept("application/json").
    when().
    get(rootUrl + "/users")
~~~~~~~~

see `RestAssuredBasicsTest.java` method `getUsersAsJSON`

---

### How else might we authenticate API calls?

Discuss

---

## HTTP POST Verb

- [POST](https://tools.ietf.org/html/rfc7231#section-4.3.3)  amend/create from partial information

- send a 'body' format of content in the 'content-type' header
- usually used to create or amend data
- browser will usually send a POST request when submitting a form

---

## HTTP POST Verb Request Example

~~~~~~~~
POST http://localhost:4567/lists HTTP/1.1
User-Agent: curl/7.39.0
Host: localhost:4567
Connection: Keep-Alive
accept: application/json
content-type: application/json
Authorization: Basic dXNlcjpwYXNzd29yZA==
Content-Length: 22

{title:'a list title'}

~~~~~~~~

---

## HTTP POST Verb Response Example

~~~~~~~~
HTTP/1.1 201 Created
Date: Thu, 17 Aug 2017 12:11:12 GMT
Content-Type: application/json
Location: /lists/f8134dd6-a573-4cf5-a6c6-9d556118ed0b
Server: Jetty(9.4.4.v20170414)
Content-Length: 171

{"lists":[{
"guid":"f8134dd6-a573-4cf5-a6c6-9d556118ed0b",
"title":"a list title",
"description":"",
"createdDate":"2017-08-17-13-11-12",
"amendedDate":"2017-08-17-13-11-12"}]}
~~~~~~~~

---

## HTTP POST Request with REST Assured

~~~~~~~~
    RestAssured.
        given().
            auth().preemptive().
            basic("admin", "password").
            body("{title : 'my title'}").
        when().
            post(rootUrl + "/lists").
        then().assertThat().
            statusCode(201);
~~~~~~~~

---

## Common HTTP Status codes in response to a POST

- **200** - OK, did whatever I was supposed to
- **201** - OK created new items
- **202** - OK, I'll do that later
- **204** - OK, I have no more information to give you
- **400** - what? that request made no sense
- **404** - I can't post to that url it is not found
- **401** - need authorisation see `WWW-Authenticate` header
- **403** - url probably exists but you are not allowed to access it
- **409** - can't do that, already exists
- **500** - your request made me crash

---

## HTTP Message Body Format - JSON

- JSON - JavaScript Object Notation
- an actual Object in JavaScript
- common data transfer and marshalling format for other languages
- https://en.wikipedia.org/wiki/JSON
- http://json.org
- http://countwordsfree.com/jsonviewer
- schema exists for JSON http://json-schema.org/

---

## JSON Example Explained

~~~~~~~~
{
    "users": [
        {
            "username": "superadmin"
        },
        {
            "username": "admin"
        },
        {
            "username": "user"
        }
    ]
}
~~~~~~~~

- An object, which has an array called "users".
- the users array contains an object with field: `username`.

---

## Basic JSON Parsing in REST Assured

~~~~~~~~
    @Test
    public void getUsersWithJSON(){

        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
                accept("application/json").
            when().
                get(rootUrl + "/users").
            then().
                body("users[0].username",
                        equalTo("superadmin")).
                and().body("users[1].username",
                        equalTo("admin"));
    }
~~~~~~~~

see `RestAssuredBasicsTest.java` method `getUsersWithJSON`

---

## REST Assured can also parse response with JSON Path

~~~~~~~~
final Response response =
RestAssured.
given().
    auth().preemptive().
    basic("admin", "password").
    accept("application/json").
when().get(rootUrl + "/users")
    .andReturn();
~~~~~~~~

~~~~~~~~
List<String> usernames = 
    response.jsonPath().getList("users.username");
Assert.assertTrue(usernames.contains("superadmin"));
Assert.assertEquals("superadmin",
    response.jsonPath().getString("users[0].username"));
~~~~~~~~

---

## XML Example Explained

~~~~~~~~
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<users>
  <users>
    <user>
      <username>superadmin</username>
    </user>
    <user>
      <username>admin</username>
    </user>
    <user>
      <username>user</username>
    </user>
  </users>
</users>
~~~~~~~~

- elements, nested elements
- tags, values

---

## HTTP Message Body Format - XML

- XML - eXtended Markup Language
- HTML is often XML
- another common marshalling format
- can be validated against XML schema
- http://countwordsfree.com/xmlviewer

---

## Basic XML Parsing in REST Assured

~~~~~~~~
@Test
public void getUsersWithXML(){

    RestAssured.
        given().
            auth().preemptive().
            basic("admin", "password").
            accept("application/xml").
        when().
            get(rootUrl + "/users").
        then().
            body("users.users.user[0].username",
                    equalTo("superadmin")).
            and().body("users.users.user[1].username",
            equalTo("admin"));
}
~~~~~~~~

see `RestAssuredBasicsTest.java` method `getUsersWithXML`

---

## REST Assured can also parse response with XML Path

~~~~~~~~
final Response response =
RestAssured.
given().
    auth().preemptive().
    basic("admin", "password").
    accept("application/xml").
when().get(rootUrl + "/users")
    .andReturn();
~~~~~~~~

~~~~~~~~
List<String> usernames = response.xmlPath().
    getList("users.users.user.username");
Assert.assertTrue(usernames.contains("superadmin"));
Assert.assertEquals("superadmin",
    response.xmlPath().getString(
        "users.users.user[0].username"));
~~~~~~~~

---

## HTTP DELETE Verb

- [DELETE](https://tools.ietf.org/html/rfc7231#section-4.3.5) - delete items

---

## HTTP DELETE Request Example

~~~~~~~~
DELETE http://localhost:4567/lists/{guid} HTTP/1.1
User-Agent: curl/7.39.0
Host: localhost:4567
Accept: */*
Connection: Keep-Alive
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
~~~~~~~~

---

## HTTP DELETE Response Example

~~~~~~~~
HTTP/1.1 204 No Content
Date: Thu, 17 Aug 2017 12:20:35 GMT
Content-Type: application/json
Server: Jetty(9.4.4.v20170414)
~~~~~~~~

---

## HTTP Delete using REST Assured

~~~~~~~~
    RestAssured.
        given().
            auth().preemptive().
            basic("admin", "password").
            body("{title : 'my new title'}").
        when().
            delete(rootUrl + "/lists" + "/" + aGuid).
        then().assertThat().
            statusCode(204);
~~~~~~~~

---

## Common HTTP Status codes in response to a DELETE

- **200** - OK, did whatever I was supposed to
- **202** - OK, I'll do that later
- **204** - OK, I have no more information to give you
- **404** - I can't post to that url it is not found
- **401** - you need to give me authorisation details see `WWW-Authenticate` header
- **403** - url probably exists but you are not allowed to access it
- **500** - your request made me crash

---

## URI - Universal Resource Identifier

`scheme:[//[user[:password]@]host[:port]][/path][?query][#fragment]`

- `http://compendiumdev.co.uk/apps/api/mock/reflect`
    - scheme = `http`
    - host = `compendiumdev.co.uk`
    - path = `apps/api/mock/reflect`

[wikipedia.org/wiki/Uniform_Resource_Identifier](https://en.wikipedia.org/wiki/Uniform_Resource_Identifier)

A URL is a URI

---

## URI vs URL vs URN

- URI - Universal Resource Identifier
    - 'generic' representation - might not include the 'scheme'
    - `http://compendiumdev.co.uk/apps/api/mock/reflect`
    - `compendiumdev.co.uk/apps/api/mock/reflect`
    - `/apps/api/mock/reflect`
- URL - Universal Resource Locator
    - `http://compendiumdev.co.uk/apps/api/mock/reflect`
    - defines how to locate the identified resource
- URN - [Universal Resource Name](https://en.wikipedia.org/wiki/Uniform_Resource_Name)
    - not often used - uses scheme `urn`

---

## Scheme(s)

- http
- https
- ftp
- mailto
- file

---

## Query Strings

~~~~~~~~
GET /lists/{guid}?without=title,description
GET http://localhost:4567/lists/f13?without=title,description
~~~~~~~~

Query String:

~~~~~~~~
?without=title,description
~~~~~~~~

- starts with `?`
- params separated with `&`

---

## More About Query Strings

~~~~~~~~
GET /lists/{guid}?without=title,description
~~~~~~~~

- usually `name=value` pairs separate by '&'
    - convention since anything after the `?` is the Query string
    - app then parses as required
- can be used with any verb
- `GET` request - all params are send as query strings

https://en.wikipedia.org/wiki/Query_string

---

## HTTP Standards?

- rfc7231 [(HTTP/1.1): Semantics and Content](https://tools.ietf.org/html/rfc7231)
- rfc7230 [(HTTP/1.1): Message Syntax and Routing](https://tools.ietf.org/html/rfc7230)


---

## HTTP PUT Verb

- [PUT](https://tools.ietf.org/html/rfc7231#section-4.3.4) - create or replace from full information

Full information means it should be idempotent - send it again and get exactly the same request

---

## HTTP PUT Request Example

~~~~~~~~
PUT http://localhost:4567/lists HTTP/1.1
User-Agent: curl/7.39.0
Host: localhost:4567
Accept: */*
Connection: Keep-Alive
Authorization: Basic dXNlcjpwYXNzd29yZA==
Content-Length: 180
Content-Type: application/json

{"title":"title added with put",
"description":"list description",
"guid": "guidcreatedwithput201708171440",
"createdDate": "2017-08-17-14-40-34",
"amendedDate": "2017-08-17-14-40-34"}
~~~~~~~~

---

## HTTP PUT Response Example

~~~~~~~~
HTTP/1.1 201 Created
Date: Thu, 17 Aug 2017 13:41:46 GMT
Content-Type: application/json
Server: Jetty(9.4.4.v20170414)
Content-Length: 0
~~~~~~~~

---

## HTTP PUT REST Assured Example

~~~~~~~~
String aGuid = UUID.randomUUID().toString();
String aTitle = "My Put Title";
String aDescription = "My mini description";
String createdDate="2018-12-08-13-50-40";
String amendedDate=createdDate;

String message = String.format(
    "{guid:'%s',title:'%s',description:'%s'," + 
    " createdDate:'%s', amendedDate:'%s'}",
    aGuid, aTitle, aDescription, createdDate, amendedDate);

RestAssured.
    given().
        auth().preemptive().
        basic("admin", "password").
        body(message).
    when().
        put(rootUrl + "/lists").
    then().assertThat().
        statusCode(201);
~~~~~~~~

---

## HTTP OPTIONS Verb

- [OPTIONS](https://tools.ietf.org/html/rfc7231#section-4.3.7) - verbs available on this url
- returns an `Allow` header describing the allowed HTTP Verbs

---

## HTTP OPTIONS Request Example

~~~~~~~~
OPTIONS http://localhost:4567/lists HTTP/1.1
User-Agent: curl/7.39.0
Host: localhost:4567
Accept: */*
Connection: Keep-Alive
~~~~~~~~

---

## HTTP OPTIONS Response Example

~~~~~~~~
HTTP/1.1 200 OK
Date: Thu, 17 Aug 2017 12:24:39 GMT
Allow: GET, POST, PUT
Content-Type: text/html;charset=utf-8
Server: Jetty(9.4.4.v20170414)
Content-Length: 0
~~~~~~~~

---

## HTTP Options REST Assured Example

~~~~~~~~
    @Test
    public void options(){
        RestAssured.
            given().
                auth().preemptive().
                basic("admin", "password").
            when().
                options(rootUrl + "/lists").
            then().
            assertThat().
                statusCode(200).
                header("Allow",
                "GET, POST, PUT");
    }
~~~~~~~~

---

## Common HTTP Status codes in response to a OPTIONS

- **200** - OK, did whatever I was supposed to
- **404** - I can't post to that url it is not found

---

## HTTP OPTIONS Verb - Example swapi.co

e.g. swapi.co

OPTIONS - https://swapi.co/api/people/1/

~~~~~~~~
{
    "name": "People Instance",
    "description": "",
    "renders": [
        "application/json",
        "text/html",
        "application/json"
    ],
    "parses": [
        "application/json",
        "application/x-www-form-urlencoded",
        "multipart/form-data"
    ]
}
~~~~~~~~

---

## Verb - Head

- [HEAD](https://tools.ietf.org/html/rfc7231#section-4.3.2)
- same as GET but does not return a 'body'
- can be useful for checking 'existence' of an endpoint or entity

---

## HEAD REST Assured Example

~~~~~~~~
    RestAssured.
        given().
            auth().preemptive().
            basic("admin", "password").
        when().
            head(rootUrl + "/lists").
        then().
            assertThat().
            statusCode(405);
~~~~~~~~

---

## Verb - Patch

- [PATCH](https://tools.ietf.org/html/rfc5789) - An 'Update' method which provides a set of changes
- Contentious [see](http://williamdurand.fr/2014/02/14/please-do-not-patch-like-an-idiot/)
- Proposed standard for [JSON Merge Patch format](https://tools.ietf.org/html/rfc7396)
- Promosed standard for [XML Patch Using XPath](https://tools.ietf.org/html/rfc5261)

Most web services just use `POST` or `PUT`

