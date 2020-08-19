

# SECTION - HTTP REQUESTS AND RESPONSES

---

# Overview of Section - HTTP Requests and Responses

- HTTP Verbs - GET, POST, DELETE
- Headers
- Responses
    - Status Codes - e.g. 200, 404, 500
- This is the foundation for most web, HTTP, REST testing and automating.

---

# HTTP Request sent from Postman

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

### HTTP GET Request sent from cURL

Command:

~~~~~~~~
curl http://localhost:4567/heartbeat ^
-H "accept: application/xml" ^
--proxy 127.0.0.1:8888
~~~~~~~~

Request:

~~~~~~~~
GET http://localhost:4567/heartbeat HTTP/1.1
User-Agent: curl/7.39.0
Host: localhost:4567
Connection: Keep-Alive
accept: application/xml
~~~~~~~~

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

### Raw HTTP Requests and Responses

- we need to be able to read them
- we will rarely have to create them by hand
- lookup headers you don't know
    - https://en.wikipedia.org/wiki/List_of_HTTP_header_fields
- some fields are for the server some are for the application some are documentation

---

# Basic HTTP Verbs

- [GET](https://tools.ietf.org/html/rfc7231#section-4.3.1)  - retrieve data
- [POST](https://tools.ietf.org/html/rfc7231#section-4.3.3)  amend/create from partial information
- [PUT](https://tools.ietf.org/html/rfc7231#section-4.3.4) - create or replace from full information
- [DELETE](https://tools.ietf.org/html/rfc7231#section-4.3.5) - delete items
- [OPTIONS](https://tools.ietf.org/html/rfc7231#section-4.3.7) - verbs available on this url

---

# References


- [W3c Standard](https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html)
- [IETF standard](https://tools.ietf.org/html/rfc7231)
- [httpstatuses.com](https://httpstatuses.com)
- http://www.restapitutorial.com/lessons/httpmethods.html

---

# HTTP GET Verb

- [GET](https://tools.ietf.org/html/rfc7231#section-4.3.1)  - retrieve data
- GET verbs can be issued by a browser
   - click on link
   - visit a site
- GET `http://compendiumdev.co.uk/apps/api/mock/reflect`
- Important Headers
    - User-Agent - tells server app type
    - Accept - what format response you prefer

Demo

---

# HTTP GET Verb Example

~~~~~~~~
curl http://localhost:4567/heartbeat ^
-H "accept: application/xml" ^
--proxy 127.0.0.1:8888
~~~~~~~~

~~~~~~~~
GET http://localhost:4567/heartbeat HTTP/1.1
User-Agent: curl/7.39.0
Host: localhost:4567
Connection: Keep-Alive
accept: application/xml
~~~~~~~~

---

# User-Agent Header

- Often not sent when accessing an API
- Marks request as coming from a browser

~~~~~~~~
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64)
AppleWebKit/537.36 (KHTML, like Gecko)
Chrome/60.0.3112.90 Safari/537.36
~~~~~~~~

---

# Accept Header

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

# HTTP Status Codes

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

# Common HTTP Status Codes

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

# HTTP Status code references

- https://httpstatuses.com/
- https://moz.com/blog/response-codes-explained-with-pictures
- https://http.cat/
- https://httpstatusdogs.com/

---

# Common HTTP Status codes in response to a GET

- **200** - OK, found the url, returned contents
- **301, 307, 308** - content has moved, new url in `location` header
- **404** - url not found
- **401** - you need to give me authorisation details see `WWW-Authenticate` header
- **403** - url probably exists but you are not allowed to access it

---

# Basic Auth Header

- This application uses Basic Auth Authentication
- `Authorization` Header

e.g. `Authorization: Basic dXNlcjpwYXNzd29yZA==`

`dXNlcjpwYXNzd29yZA==` is base64 encoded "user:password"

see [base64decode.org](https://www.base64decode.org)

- cURL you need to add the header
- Postman & Insomnia use the Authorization and Auth tabs

---

# Create Basic Auth Header in Postman

![Create Basic Auth Header in Postman](images/postman_basic_auth.png)

---

# Create Basic Auth Header in Insomnia

![Create Basic Auth Header in Insomnia](images/insomnia_auth.png)

---

# HTTP POST Verb

- [POST](https://tools.ietf.org/html/rfc7231#section-4.3.3)  amend/create from partial information

- send a 'body' format of content in the 'content-type' header
- usually used to create or amend data
- browser will usually send a POST request when submitting a form

Demo

---

# HTTP POST Verb Send Example

~~~~~~~~
curl -X POST http://localhost:4567/lists ^
-H "accept: application/xml" ^
-H content-type:application/json ^
-H "Authorization: Basic dXNlcjpwYXNzd29yZA==" ^
-d "{title:'a list title'}" ^
--proxy 127.0.0.1:8888
~~~~~~~~

---

# HTTP POST Verb Request Example

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

# HTTP POST Verb Response Example

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

# Common HTTP Status codes in response to a POST

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

# HTTP Message Body Format - JSON

- JSON - JavaScript Object Notation
- an actual Object in JavaScript
- common data transfer and marshalling format for other languages
- https://en.wikipedia.org/wiki/JSON
- http://json.org
- http://countwordsfree.com/jsonviewer
- schema exists for JSON http://json-schema.org/

---

# JSON Example Explained

~~~~~~~~
{
 "lists":
  [
   {
    "guid":"f8134dd6-a573-4cf5-a6c6-9d556118ed0b",
    "title":"a list title",
    "description":"",
    "createdDate":"2017-08-17-13-11-12",
    "amendedDate":"2017-08-17-13-11-12"
   }
  ]
}
~~~~~~~~

- An object, which has an array called "lists".
- the lists array contains an object with fields: `guid`, `title`, `description`, `createdDate`, `amendedDate` - all String fields.

---

# XML Example Explained

~~~~~~~~
<?xml version="1.0" encoding="UTF-8" ?>
<lists>
  <list>
    <guid>f8134dd6-a573-4cf5-a6c6-9d556118ed0b</guid>
    <title>a list title</title>
    <description></description>
    <createdDate>2017-08-17-13-11-12</createdDate>
    <amendedDate>2017-08-17-13-11-12</amendedDate>
  </list>
</lists>
~~~~~~~~

- elements, nested elements
- tags, values

---

# HTTP Message Body Format - XML

- XML - eXtended Markup Language
- HTML is often XML
- another common marshalling format
- can be validated against XML schema
- http://countwordsfree.com/xmlviewer

---

# HTTP DELETE Verb

- [DELETE](https://tools.ietf.org/html/rfc7231#section-4.3.5) - delete items

Demo

---

# HTTP DELETE Send Example

~~~~~~~~
curl -X DELETE http://localhost:4567/lists/{guid} ^
-H "Authorization: Basic YWRtaW46cGFzc3dvcmQ=" ^
--proxy 127.0.0.1:8888
~~~~~~~~

---

# HTTP DELETE Request Example

~~~~~~~~
DELETE http://localhost:4567/lists/{guid} HTTP/1.1
User-Agent: curl/7.39.0
Host: localhost:4567
Accept: */*
Connection: Keep-Alive
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
~~~~~~~~

---

# HTTP DELETE Response Example

~~~~~~~~
HTTP/1.1 204 No Content
Date: Thu, 17 Aug 2017 12:20:35 GMT
Content-Type: application/json
Server: Jetty(9.4.4.v20170414)
~~~~~~~~


---

# Common HTTP Status codes in response to a DELETE

- **200** - OK, did whatever I was supposed to
- **202** - OK, I'll do that later
- **204** - OK, I have no more information to give you
- **404** - I can't post to that url it is not found
- **401** - you need to give me authorisation details see `WWW-Authenticate` header
- **403** - url probably exists but you are not allowed to access it
- **500** - your request made me crash


---

# Postman Collections

- "save as" requests to collections for re-use
- can share collections or export to file

---

# Postman Collections

![Postman Collections](images/postmanCollection.png)

---

# Insomnia Workspaces

- create new Workspace
- create new Request in Workspace
- changes automatically saved to workspace
- can export workspace to files

---

# Insomnia Workspaces

![Insomnia Workspaces](images/insomniaWorkspace.png)

---

# Environment Variables

Postman:

- use environment variables e.g. `{{host}}` instead of `localhost:4567`
- `GET httt://{{host}}/lists`

Insomnia:

- use environment variables e.g. `{'host':'localhost:4567'}`
- just type `host` for auto complete in URL editing

---

# Postman Create Environment

![Postman Create Environment](images/postmanCreateEnvironment.png)


---

# Postman Add Environment Variables

![Postman Add Environment Variables](images/postmanManageEnvironmentVariables.png)

---

# Insomnia Environment Management

![Insomnia Environment Management](images/insomniaEnvironmentManagement.png)

---

# How to test with this information

- Read the standards for the verbs and the status codes.
- Projects often argue about interpretations.
- Some of the standards are exact enough that it is possible to say "I observed X" it does not match the standard - include links and quotes to the standards.

---

# Exercises

- install the REST listicator
    - [http://compendiumdev.co.uk/downloads/apps/restlisticator](http://compendiumdev.co.uk/downloads/apps/restlisticator/v1/rest-list-system.jar)
         - [/v1/rest-list-system.jar]((http://compendiumdev.co.uk/downloads/apps/restlisticator/v1/rest-list-system.jar)) download the `.jar` file
         - [/v1/documentation.html](http://compendiumdev.co.uk/downloads/apps/restlisticator/v1/documentation.html) read the documentation
- in the directory you downloaded it to type:
    - `java -jar rest-list-system.jar`
- try out the above GET, POST, DELETE, OPTIONS and POST using a GUI client e.g. Postman, or Insomnia
- see more exercises in exercise section
- explore the HTTP Client functionality and test the API based on its documentation
- see Exercises section for more exercises

---