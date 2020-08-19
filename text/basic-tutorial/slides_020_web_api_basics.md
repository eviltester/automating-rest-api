

# SECTION - WEB API BASICS

---

# Overview of Section - WEB API Basics

- What is a Web API?
- Ajax/XHR and API
- Why Web HTTP API?
- Exercises using http://swapi.co

---

# What is a Web API?

- Web Service
    - [w3.org/TR/ws-gloss](https://www.w3.org/TR/ws-gloss)
- API
    - [wikipedia.org/wiki/Application_programming_interface](https://en.wikipedia.org/wiki/Application_programming_interface)

Web Application with an interface designed for use by other software.

---

# Why an API?

- Other systems to access
- Customisation
- Mobile Apps often use API
    - bag a SNES classic

---

# Exercise: A Web Application which uses API

- https://swapi.co
    - make a request from the GUI
    - Use network tab

---

# JavaScript Using API Via AJAX/XHR

- AJAX/XHR requests have security protocols for same domain
- [JSONP](https://en.wikipedia.org/wiki/JSONP) for cross domain access
- Very often API is used under covers, e.g. a serverside script/app on same domain uses an API on server side rather than client side

---

# We Need Tools

Because Web Service designed for software, we need tools to access them.

---

# Tools

- [cURL](https://curl.haxx.se)
    - command line based
    - API examples often shown in cURL
    - recommended that you learn this eventually
    - [download](https://curl.haxx.se/dlwiz/)
- GUI Clients
    - [Postman](https://www.getpostman.com/)
    - [Insomnia](https://insomnia.rest/)

---

# Demo cURL

~~~~~~~~
curl http://localhost:4567/heartbeat -i
curl -X GET http://localhost:4567/users
curl http://localhost:4567/lists -H "accept: application/xml"
~~~~~~~~

Can be complicated but useful for emergencies, scripting, bug reporting.

_Hint: can use Postman or Insomnia to generate cURL code but different continuation characters on different operating systems: `^` Windows and `\` on Mac/Linux also `"` and `'` differences._

---

# Demo Postman

- Postman make GET request
- Postman console
- Postman set basic auth
- Postman add a header
- Postman Collections
- Postman Environment Variables


---

# Demo Insomnia

- Insomnia make GET request
- Insomnia Timeline
- Insomnia set basic auth
- Insomnia add a header
- Insomnia Workspace
- Insomnia Environment Variables

---

# Exercise: Install Tools for accessing HTTP API Web Services

Install either:

- Postman [GetPostman.com](https://www.getpostman.com/)
- Insomnia [insomnia.rest](https://insomnia.rest/)

---

# Exercise: Call a webservice using tools

- GET https://swapi.co/api/people/1

'MOCK' Web Services

- GET http://compendiumdev.co.uk/apps/api/mock/heartbeat
- GET http://jsonplaceholder.typicode.com/users/1/todos

see exercises section for more

---

# What is a Web Service / Web Application?

- A web hosted HTTP accessed application without a GUI

---

# What is an API?

- Application Programming Interface designed for use by software

Note: error messages need to be human readable

---

# Why test interactively and not just automate?

- observe traffic
- create varied requests
- experiment fast
- setup data
- send 'invalid' requests
- exploratory testing of API
- test while API still 'flexible'
- Interactive CRUD testing - CREATE, READ, UPDATE, DELETE

---

