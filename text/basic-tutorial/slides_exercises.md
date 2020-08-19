# Section - Exercises

There are exercises dotted throughout the slides but this section has the long form exercises that you can work through at your own pace in the exercise sections and after the tutorial when you want to continue working.

The exercises are ordered to basically match the tutorial structure, but feel free to do then in any order - skip the early exercises if you are more experienced. There is enough here for people of any level of experience.

---

# Exercises for SECTION - HTTP BASICS

---

# Exercise: View Browser Requests in Dev Tools Network Tab

Aims:

- Learn to use the Dev Tool in Web Browsers to view traffic.
- View the HTTP requests and HTTP responses
- Look up any headers or information that you don't understand via web search
- Suggested sites: Twitter, Instagram, Gmail, Ebay
- Identify more sites

_Use this as an ongoing exercise during personal and work day to learn dev tools and HTTP_

---

# Exercises for SECTION - WEB API BASICS

---

# Exercise: Install an HTTP GUI Client

Aims: install and use an HTTP REST GUI Client

Install either:

- Postman [GetPostman.com](https://www.getpostman.com/)
- Insomnia [insomnia.rest](https://insomnia.rest/)

Call a 'mock' webservice

- GET http://compendiumdev.co.uk/apps/api/mock/heartbeat
- GET https://swapi.co/api/people/1
- GET http://jsonplaceholder.typicode.com/users/1/todos

see [swapi.co](https://swapi.co), [jsonplaceholder.typicode.com](http://jsonplaceholder.typicode.com)

---

# Exercise: Use Swapi.co Web Application which uses API

Aims: explore a public API example

- https://swapi.co
- make a request from the GUI, use network tab to view requests
- read the https://swapi.co/documentation
- use an HTTP client to issue requests e.g. Postman, cURL, Insomnia.rest
- try to generate different HTTP Status codes: 200, 400, 404, 405
- experiment with different HTTP Verbs: GET, POST, OPTIONS, HEAD

---

# Exercise: Explore HTTP Verbs with JsonPlaceHolder MOCK Web Service

Web service does not 'persist' data, but will reflect back changes in response e.g. a POST will not amend data on server, but the response will show you what would have happened if it had.

Using: [jsonplaceholder.typicode.com](http://jsonplaceholder.typicode.com)

- Experiment with the Resources, and Routes. (see next slide for more hints)

---

# Exercise: Use JsonPlaceHolder MOCK Web Service for `GET` requests

- GET all posts `/posts`
- GET a single post `/posts/1`
- GET comments for a post `/posts/1/comments`
- GET comments for a post using Query string `/comments?postid=1`

- Experiment with the Resources, and Routes listed on [jsonplaceholder.typicode.com](http://jsonplaceholder.typicode.com)

For all parameterised queries - try others e.g. amend `1` to something else

---

# Exercise: Use JsonPlaceHolder MOCK Web Service for `POST`, `PUT`, `DELETE` requests

- use POST to create a post `/posts` (hint, use GET to find format, don't add an 'id')
- use POST to amend a post `/posts` (hint, add an `id`)
- DELETE a single post `/posts/1`
- create a post with PUT `/posts/1`

Any bugs when you use the above?

Experiment - any bugs?

---

# Exercise: Use examples file for JsonPlaceHolder

- Insomnia.rest Workspace
    - import `/exercises/insomnia/workspaces` the 'jsonplaceholder...' file

- Postman Collection
    - import `/exercises/postman/collections` the 'jsonplaceholder...' file
    - import shared collection from
    - https://www.getpostman.com/collections/2c7270ffbd075f03e33d

---

# Exercises for SECTION - HTTP REQUESTS AND RESPONSES

---

# Exercise: Install Test Java REST API - REST Listicator

- [http://compendiumdev.co.uk/downloads/apps/restlisticator](http://compendiumdev.co.uk/downloads/apps/restlisticator/v1/rest-list-system.jar)
    - [/v1/rest-list-system.jar]((http://compendiumdev.co.uk/downloads/apps/restlisticator/v1/rest-list-system.jar))
    - [/v1/documentation.html](http://compendiumdev.co.uk/downloads/apps/restlisticator/v1/documentation.html)

- read the documentation
- download the `.jar` file

---

# Exercise: Run Test Java REST API - REST Listicator

- in the directory you downloaded it to type:
    - `java -jar rest-list-system.jar`

~~~~~~~~
>java -jar rest-list-system.jar
User : superadmin - ea382383-e888-4e7c-b569-5c0fdc6b91d2
User : admin - 96244a45-e0c5-40e7-ac3f-2351ab5f7947
User : user - 08e7f6f5-c111-401b-9366-bd86fc755d1b
SLF4J:Failed to load class"org.slf4j.impl.StaticLoggerBinder"
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder
for further details.
Running on 4567
~~~~~~~~

---

# Exercise: Check app is running

- Open http://localhost:4567 in browser

Should see the documentation

- GET localhost:4567/heartbeat

Should return 200

---

# Exercise: Read the documentation and issue GET requests

- GET the list of users
- GET the list of Lists
- Change `Accept` header to GET list in XML and in JSON
- What happens if you GET `/logs`? Is that response correct?

<!--

404 always returns an HTML payload - which it shouldn't really, it should probably return an 'error' payload in the Accept content type that was asked for.
-->

---

# Exercise: Use parameters to filter User list

- GET '/users' lists some filters that can be applied to username
- Search for partial match on "r"
- Search for users with "admin" in their username
- Search for users with an absolute name e.g. `user`

---

# Exercise: Read the HTTP standard documentation to learn about verbs

HTTP Verbs are described in the [W3c Standard](https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html) and [IETF standard](https://tools.ietf.org/html/rfc7231)

Standard

- [GET](https://tools.ietf.org/html/rfc7231#section-4.3.1)
- [POST](https://tools.ietf.org/html/rfc7231#section-4.3.3)
- [DELETE](https://tools.ietf.org/html/rfc7231#section-4.3.5)

When you test or use the system - does your observation match the standards?

---

# Exercise: Basic Auth Header

- Postman 'Authorization' tab
- Insomnia 'Auth' tab

- GET `/users/admin` when authenticated as `user:password`
- GET `/users/admin` when authenticated as `admin:password`

Did you see a difference?

Some functions require admin privileges later.

---

# Exercise: Use POST to Create a List

- default `user` password is `password`
- minimum information for a list is `title` e.g. `{title:"this is a title"}`
- Basic Auth required for a `POST` request to `/lists`

How do you know it created the list?

---

# Exercise: Use DELETE to Delete a list

- DELETE a 'list' that you created using the `guid`

_hint: `user` cannot delete_

---

# Exercise: Use POST to Amend a List

- default `user` password is `password`
- minimum information for a list is `title` e.g. `{title:"this is a title"}`
- Basic Auth required for a `POST` request to `/lists/{guid}`

How do you know it amended the list?

Can you amend all the fields? `title`, `description`, `guid`, `createdDate`, `amendedDate`

---

# Exercise: Status Codes

Send requests to deliberately trigger the following status codes:

- `200 OK`
- `201 Created`
- `204 No Content`
- `400 Bad Request`
- `401 Unauthorized`
- `403 Forbidden`
- `404 Not Found`
- `405 Method Not Allowed`
- `409 Conflict`

Bonus points for a `5xx` error e.g. `500`

---

# Exercise: Explore the documentation and test the basics

- read the documentation
- create more lists
- amend lists
- delete lists
- switch between users
- create and amend users
- test the GET, POST, DELETE
- test the access permissions

---

# Exercise: Explore Postman

- create a collection
- use environment variables e.g. `{{host}}` instead of `localhost:4567`
- share a collection
- download the `exercise_data_files.zip` from workshop page
    - import the shared collection for RestListicator
    - import the shared collection for JsonPlaceHolder

---

# Exercise: Explore Insomnia

- create a workspace
- use environment variables e.g. `{'host':'localhost:4567'}`
- download the `exercise_data_files.zip` from workshop page
    - import the shared collection for RestListicator
    - import the shared collection for JsonPlaceHolder


---

# Exercises for SECTION - MORE HTTP REQUESTS AND RESPONSES

---

# Exercise: Use OPTIONS to explore capabilities of `/lists`

- [OPTIONS](https://tools.ietf.org/html/rfc7231#section-4.3.7)

_hint: check the headers_

---

# Exercise: PUT

- Use `PUT` to create a new 'list'
- Use `PUT` to amend a list
- Does `PUT` work with multiple lists?
- Does `PUT` work with users?
- Test the `PUT` end points with different payloads

---

# Exercises for SECTION - REST API BASICS

---

# Exercises: HEAD

- [HEAD](https://tools.ietf.org/html/rfc7231#section-4.3.2)
   - does `HEAD` implementation match the standard?
   - compare `HEAD` to `GET` is it the same information?
   - which parts of the API respond to `HEAD`?
   - does the documentation match the implementation?

---

# Exercises: PATCH

- [PATCH](https://tools.ietf.org/html/rfc5789) is implemented just like `post`
   - does it comply with the [JSON Merge Patch](https://tools.ietf.org/html/rfc7396) standard?
- The implementation does not comply with the [JavaScript Object Notation (JSON) Patch](https://tools.ietf.org/html/rfc6902) standard
   - read the JSON Patch standard
- The implementation does not comply wiht the [XML Patch Operations standard](https://tools.ietf.org/html/rfc5261)
   - read the XML Patch Standard

---

# Exercise: Create a bunch of lists using Postman Collection Runner

- create a new collection with single request to create a list e.g.
    - POST http://localhost:4567
    - Basic HTTP Authentication for `admin:password`
    - body `{title:"{{title}}"}`

Then create csv file:

~~~~~~~~
title
a title
another title
~~~~~~~~

User the `Collection \ Runner` to run the collection with the data file

---

# Exercises for SECTION - TESTING WEB SERVICES

---

# Exercise: Think through the Testing of the REST API

- Risks? Architecture?, Database?, Capacity (load, multiuser)?
- Functionality? are requirements met?
- Data? what data do you have to explore in the requests?
- Request Capabilities? e,g, verbs, formats, headers, etc.
- ?

---

# Exercise: Start REST Listicator Web Service with Bugs Enabled

`java -jar rest-list-system.jar -bugfixes=false`

- The system has been coded with some known bugs
- these are all fixed by default.
- start with `-bugfixes=false` to have known bugs
- See if you can find them

---

# Exercises for SECTION - PROXIES

---

# Exercise: Use Insomnia through a Proxy

- Start proxy
- Identify Listening Port of proxy
- Application \ Preferences
- [x] Enable Proxy
- type proxy details into `HTTP Proxy` field e.g. `localhost:8888`
- use Insomnia and see requests in Proxy
- replay requests from Proxy

---

# Exercise: Use Postman through a System Proxy

- on Windows - Charles or Fiddler
- on Mac - Charles
- start proxy
- start Postman
- in Postman `File \ Settings \ Proxy` ensure 'User System Proxy' is 'on'

---

# Exercise: Use Postman through a non-system proxy

For full details see [blog post](http://blog.eviltester.com/2016/12/how-to-configure-postman-native-client.html)

- Mac (type it all on one line):

~~~~~~~~
open /Applications/Postman.app --args 
--proxy-server=localhost:8888
~~~~~~~~

- Windows:

~~~~~~~~
cd C:\Users\Alan\AppData\Local\Postman\app-4.9.3\
postman.exe --proxy-server=localhost:8888
~~~~~~~~

---

# Exercise: Create/Amend Data with Proxy Fuzzers

- use the proxy to create lists test data
- use the proxy to create users
- experiment with other requests
- experiment with different proxies and fuzzer payloads


---

# Exercises for SECTION - AUTOMATING - review resources

- read the resources and learn more about automating and REST Assured

---

# Exercises for SECTION - AUTOMATING - run the tests

- download the code from [GitHub for REST Listicator Automating Examples](https://github.com/eviltester/rest-listicator-automating-examples)
- you need a JAVA SDK to run the tests
- load the code into IntelliJ
- run the tests

---

# Exercises for SECTION - AUTOMATING - review the code

- read and understand the tests and abstraction layer
- review the tests for coverage
   - what is missing?
- what does the abstraction layer prevent you from doing?

---

# Exercises for SECTION - AUTOMATING - expand the tests

- add more tests to cover the REST Listicator documentation
    - e.g. users, api keys for authentication, post multiple lists, url parameters etc.
- refactor code as you go to build abstraction layers
- rename existing api methods to match verbs rather than logic

---

# Section - TEST THE APIS

- run through the exercises you haven't done
- explore the REST Listicator with further test ideas
- explore the capabilities of the tools - Postman, Insomnia, Proxies
- see next section for more exercise ideas

---

# Exercises for SECTION - HOUR OF POWER Exercise suggestions

- use the REST Listicator in buggy mode
- compare results with REST Listicator in non-buggy mode
- can you test against the documentation?
- can you create users?
- can you create multiple users?
- can you create multiple lists?
- can you amend lists?

---

# More Exercises for SECTION - HOUR OF POWER Exercise suggestions

- can you delete lists?
- can you filter lists?
- can you filter fields on `/list` and `/user`?
- can you amend a user?
- can you amend your own user details?
- explore tool capabilities
- feed through proxies

---

# Additional and Advanced Exercises

---

# Exercise: Explore RESTful Booker

RESTful Booker written by Mark Winteringham

- Read the Documentation
    - [restful-booker.herokuapp.com](https://restful-booker.herokuapp.com/)
- Test/Explore the application using Postman/Insomnia/Proxies
- Read the Code
    - [github.com/mwinteringham/restful-booker](https://github.com/mwinteringham/restful-booker)

---

# Exercise: Install and use cURL

- download using the [wizard](https://curl.haxx.se/dlwiz/)
- read the [docs](https://curl.haxx.se/docs/)
- repeat JsonPlaceHolder exercises using cURL
- export cURL from Postman and Insomnia
- use cURL to GET normal web pages

---

# Exercise: Use cURL Requests in Postman and Insomnia

Postman:

- click import, then "Paste Raw Text" then paste in a cURL command

Insomnia:

- copy and paste a cURL command into the URL field

---

# Public WebServices to experiment with

Most public APIs are going to be `GET` requests.

Lists of APIs:

- [any-api.com](https://any-api.com/)
- [github.com/toddmotto/public-apis](https://github.com/toddmotto/public-apis)

---

# Mock Web Servers to use for experimenting

- Read the docs for the mock web server
- Send through requests and call the server, does it work as expected?

https://jsonplaceholder.typicode.com/

---

# Experiment with Swapi.co

- http://swapi.co : `GET`, `HEAD` and `OPTIONS` only

---

# Performance Testing

- How would you performance test the API?
- JMeter? Gatling?
- Can you performance test the web service?

- **Make sure you use an isolated/local environment to test on**

---

# Suggested Applications to Test

- [Tracks](http://www.getontracks.org)
    - Download a VM from [Turnkey](https://www.turnkeylinux.org/tracks) and test the Tracks API
