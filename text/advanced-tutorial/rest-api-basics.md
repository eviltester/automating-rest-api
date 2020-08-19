---

# SECTION - REST API BASICS

---

# Overview of Section - REST API BASICS

- What is a REST API?
- CRUD and REST
- HTTP Verbs - HEAD, PATCH
- Authentication and Authorisation
- Postman collection runner

---

# What is a REST API?

- HTTP API - generic, anything goes
- REST API
    - the HTTP Verbs mean something specific e.g. should not Delete with a POST request
    - URI are 'nouns' and describe entities

---

# REST Standards?

[Representational State Transfer](https://en.wikipedia.org/wiki/Representational_state_transfer)

- Loose standards
- Lots of disagreement on teams and online
- DISSERTATION: "Architectural Styles and the Design of Network-based Software Architectures" by [Roy Fielding](http://www.ics.uci.edu/~fielding/)
   - [ics.uci.edu/~fielding/pubs/dissertation/top.htm](https://www.ics.uci.edu/~fielding/pubs/dissertation/top.htm)

---

# Guidance

- Idempotent - same request, same result (on server, not necessarily in response)
- Stateless - server does not need to maintain state of client requests between requests e.g.
    - request 1: select these files,
    - request 2: delete files selected in previous request
- Cacheable - on the server side e.g. GET can be cacheable until entities in GET are updated
- Does it comply with HTTP Standard Guidance?

---

# CRUD

- Verbs are not as simple as Create, Read, Update, Delete

| CRUD Action | Verb |
|------|--------|
| Create | POST, PUT |
| Read | GET |
| Update | POST, PUT, PATCH |
| Delete |  DELETE |

---

# Endpoints vs URL

Very often when discussing REST APIs we talk about 'endpoints'.

Basically the 'path' part of the URL.

The following are the same Endpoint

- `/lists`
- `/lists?title="title"`

---

# Payloads vs Body

A Payload is the content of the body of the HTTP request.

- XML and JSON
- Tends not to be Form encoded
- Request defined by `content-type` header
- Response requested in `accept` header
- usually unmarshalled into an object in the application

---

# Requesting Formats

| Header        | Means |
|---------------|-------|
|Accept: application/json |	Please return JSON |
|Accept: application/xml | Please return XML |
|Content-Type: application/json	| This payload is JSON |
|Content-Type: application/xml | This payload is XML |

- XML might also be : `text/xml`
- The server might not support a particular format it might default to JSON or XML and ignore the header

---

# Authentication

If you make a request to a server and receive a 401 then you are not authenticated.

`WWW-Authenticate` header should challenge you with the authentication required.

- Generally avoid header sending by known authenticating information in request.
- Common bug is `WWW-Authenticate` not sent back in response.

---

# Common Authentication Approaches

- [Basic Auth Header](http://tools.ietf.org/html/7617)
    - `Authorization: Basic Ym9iOmRvYmJz`
    - base 64 encoded `username:password`
- Cookies
    - when 'login' server sends back a 'session cookie'
    - send 'session cookie' in future requests
- Custom Headers
    - API `secret codes`
    - e.g. `X-API-AUTH: thisismysecretapicode`

---

# Common Authentication Approaches

- URL authentication
    - `https://username:password@www.example.com/`
    - deprecated
    - used to be very common when automating web GUIs

Recommended reading [developer.mozilla.org/en-US/docs/Web/HTTP/Authentication](https://developer.mozilla.org/en-US/docs/Web/HTTP/Authentication)

---

# Authentication vs Authorization

Authentication

- Are you authenticated?
- Does the system know who you are?
- Are your auth details correct?

Authorization

- you are authenticated
- do you have permission to access this endpoint?

---

# Real World vs Standards

Teams debate this all the time.

- Login? [stackoverflow.com/questions/13916620](https://stackoverflow.com/questions/13916620/rest-api-login-pattern)
- Put vs Post [stackoverflow.com/questions/630453](https://stackoverflow.com/questions/630453/put-vs-post-in-rest)
- see discussions on [restcookbook.com](http://restcookbook.com)

As a Tester:

- Refer to HTTP standards
    - headers, idempotency, response recommendations

Expect 'discussions' and 'debates' on a team.

---

# How to test with this information

- Read the standards for the verbs and the status codes.
- Projects often argue about interpretations.
- Some of the standards are exact enough that it is possible to say "I observed X" it does not match the standard - include links and quotes to the standards.

