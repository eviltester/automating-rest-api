
# SECTION - MORE HTTP REQUESTS AND RESPONSES

---

# Overview of Section - More HTTP Requests and Responses

- HTTP Verbs - PUT, OPTIONS
- URI vs URL
- HTTP Standards
- cURL

---

# URI - Universal Resource Identifier

`scheme:[//[user[:password]@]host[:port]][/path][?query][#fragment]`

- `http://compendiumdev.co.uk/apps/api/mock/reflect`
    - scheme = `http`
    - host = `compendiumdev.co.uk`
    - path = `apps/api/mock/reflect`

[wikipedia.org/wiki/Uniform_Resource_Identifier](https://en.wikipedia.org/wiki/Uniform_Resource_Identifier)

A URL is a URI

---

# URI vs URL vs URN

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

# Scheme(s)

- http
- https
- ftp
- mailto
- file

---

# Query Strings

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

# More About Query Strings

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

# HTTP Standards?

- rfc7231 [(HTTP/1.1): Semantics and Content](https://tools.ietf.org/html/rfc7231)
- rfc7230 [(HTTP/1.1): Message Syntax and Routing](https://tools.ietf.org/html/rfc7230)


---

# HTTP PUT Verb

- [PUT](https://tools.ietf.org/html/rfc7231#section-4.3.4) - create or replace from full information

Full information means it should be idempotent - send it again and get exactly the same request

Demo

---

# HTTP PUT Send Example

~~~~~~~~
curl -X PUT http://localhost:4567/lists ^
-H "Authorization: Basic dXNlcjpwYXNzd29yZA==" ^
--proxy 127.0.0.1:8888 ^
-d @createlistwithput.txt
~~~~~~~~

where `createlistwithput.txt` file contains

~~~~~~~~
{"title":"title added with put",
"description":"list description",
"guid": "guidcreatedwithput201708171440",
"createdDate": "2017-08-17-14-40-34",
"amendedDate": "2017-08-17-14-40-34"}
~~~~~~~~

---

# HTTP PUT Request Example

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

# HTTP PUT Response Example

~~~~~~~~
HTTP/1.1 201 Created
Date: Thu, 17 Aug 2017 13:41:46 GMT
Content-Type: application/json
Server: Jetty(9.4.4.v20170414)
Content-Length: 0
~~~~~~~~

---

# HTTP OPTIONS Verb

- [OPTIONS](https://tools.ietf.org/html/rfc7231#section-4.3.7) - verbs available on this url
- returns an `Allow` header describing the allowed HTTP Verbs

---

# HTTP OPTIONS Send Example

~~~~~~~~
curl -X OPTIONS http://localhost:4567/lists ^
--proxy 127.0.0.1:8888
~~~~~~~~

Demo

---

# HTTP OPTIONS Request Example

~~~~~~~~
OPTIONS http://localhost:4567/lists HTTP/1.1
User-Agent: curl/7.39.0
Host: localhost:4567
Accept: */*
Connection: Keep-Alive
~~~~~~~~

---

# HTTP OPTIONS Response Example

~~~~~~~~
HTTP/1.1 200 OK
Date: Thu, 17 Aug 2017 12:24:39 GMT
Allow: GET, POST, PUT
Content-Type: text/html;charset=utf-8
Server: Jetty(9.4.4.v20170414)
Content-Length: 0
~~~~~~~~

---

# Common HTTP Status codes in response to a OPTIONS

- **200** - OK, did whatever I was supposed to
- **404** - I can't post to that url it is not found

---

# HTTP OPTIONS Verb - Example swapi.co

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

# Exercises

- try out the above OPTIONS and PUT using a GUI client e.g. Postman, or Insomnia
- try with different body content e.g. xml vs json
- try requesting `application/xml` instead of `application/json`
- explore the HTTP Client functionality and test the API based on its documentation
- see Exercises section for more exercises

---

