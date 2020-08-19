# SECTION - AUTOMATING

---

# Overview of Section -Automating

- Automating
- REST Listicator Example Automating code
- Abstraction Layers
- REST Assured
- Resources to learn from

---

# Why Automate?

- repeatability
- speed
- data coverage
- deployment validation
- support exploratory testing

---

# How?

- Postman?
- HTTP Libraries?
- REST Libraries?
- Which language?
- Other tools?

---

# Examples Using Java and REST Assured

- https://github.com/rest-assured/rest-assured
- Java/Groovy library
- HTTP Abstraction
- Marshalling - Serialization/Deserialization
- Assertions

---

# Basic GET Request

~~~~~~~~
@Test
public void canCheckThatServerIsRunning(){

    RestListicatorServer server =
        new RestListicatorServer("localhost",4567);

    RestAssured.
            get(server.getHTTPHost() + "/heartbeat").
            then().assertThat().
            statusCode(200);
}
~~~~~~~~

---

# Payload Objects

~~~~~~~~
@XmlRootElement(name="list")
public class ListPayload {

    private String title;
    private String guid;
    private String description;

    public String getGuid() {
        return guid;
    }
    public void setGuid(String guid) {
        this.guid = guid;
    }
  ...
}
~~~~~~~~

---

# REST Assured

- uses the `content-type` header to (de)serialize to JSON or XML

~~~~~~~~
contentType("application/xml")
~~~~~~~~

~~~~~~~~
contentType("application/json")
~~~~~~~~

---

# Marshalling / Serializing

~~~~~~~~
public Response createList(ApiUser user, ListPayload list) {
   return RestAssured.
          given().
             contentType(contentType).
             accept(accept).
             auth().preemptive().
             basic(user.getUsername(), user.getPassword()).
             body(list).
           when().
               post(server.getHTTPHost() + "/lists").
           andReturn();
}
~~~~~~~~

---

# Code Walkthrough of REST Listicator Automating Examples

- https://github.com/eviltester/rest-listicator-automating-examples
- code built to show refactoring steps e.g. [ListCreationTest](https://github.com/eviltester/rest-listicator-automating-examples/blob/master/src/test/java/uk/co/compendiumdev/restlisticator/automating/ListCreationTest.java)
- refactor to abstraction layers
- payload objects could be public fields but that is more vulnerable to app changes
    - xml & json annotations
- api method naming (`createList`) - would be better as `postList` - why?

---

# Code Walkthrough of REST Listicator Automating Examples

- static api vs instantiated api e.g. `ListicatorAPI` singleton
     - readability vs flexibility
- Abstractions can restrict coverage as well as aid it
    - review abstractions to see what is not, and can not be tested with that abstraction code

---

# Resources to learn from Mark Winteringham

http://www.mwtestconsultancy.co.uk/

Mark Winteringham has some useful study material on REST and automating Web Services.

- https://github.com/mwinteringham/api-framework
    - code in different languages and frameworks demonstrating REST API automated execution
- https://github.com/mwinteringham/restful-booker
    - Test Web API
    - live at https://restful-booker.herokuapp.com/
- https://github.com/mwinteringham/presentations
    - Mark's REST Presentations

---

# Bas Dijkstra & James Willett

## Resources to learn from Bas Dijkstra

- http://www.ontestautomation.com/open-source-workshops/
    - API REST Assured Code and slides
- http://www.ontestautomation.com/category/api-testing/
    - Bas's Blog posts on API Testing


## Resources to learn from James Willett

- https://www.james-willett.com/blog/restassured/
    - blog posts on REST Assured

---

# Resources to learn from Alan Richardson

Github code samples using REST Assured

- https://github.com/eviltester/rest-listicator-automating-examples
- https://github.com/eviltester/tracksrestcasestudy
- https://github.com/eviltester/libraryexamples

Automating and Testing a REST API

- support page (videos) - https://www.compendiumdev.co.uk/page/tracksrestsupport
- book - https://compendiumdev.co.uk/pag/tracksrestapibook

---

# Exercises

- read the resources and learn more about automation and REST Assured
- if you have JDK and Java IDE then download [the source for REST Listicator Automating Examples](https://github.com/eviltester/rest-listicator-automating-examples)
- run the tests
- add more tests to cover the REST Listicator documentation e.g. users, api keys for authentication, post multiple lists, url parameters etc.
- refactor code as you go to build abstraction layers
- rename existing api methods to match verbs rather than logic
- see exercises section for more ideas

---