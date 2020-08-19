---

# SECTION - PAYLOAD OBJECTS

---

## What are Payload Objects?

- Java Classes which represent payload sent or response received
- We can convert the received JSON or XML into Java Objects to Process them
    - Marshalling/Unmarshalling
    - Serialising/De-serialising
- This is a **fundamental Abstraction layer** used for automating API systems

---

## Why Payload Objects?

But we already have the ability to perform queries on the responses in RESTAssured to get the details we need:

e.g.

~~~~~~~~
body("shopping.category.find { it.@type == 'groceries' }.item"
    ,hasItems("Chocolate", "Coffee"));
~~~~~~~~

_from official REST Assured Documentation_

~~~~~~~~
then().
    body("users[0].username",
            equalTo("superadmin")).
    and().body("users[1].username",
            equalTo("admin"));
~~~~~~~~

_from `RestAssuredBasicsTest.java`_

---

## Over-reliance on REST Assured and Groovy

- complicated query syntax
- need to learn Groovy GPath [groovy-lang.org/processing-xml.html#_gpath](http://groovy-lang.org/processing-xml.html#_gpath)
- hard to debug
- hard to maintain

_NOTE: personal opinion_

- I would rather convert to Java Objects and write simple Java code

---

## REST Assured Marshalling with POJO

- REST Assured uses GSON and JAXB to marshall/unmarshall objects
- create normal POJO
- JSON requires no annotations
- XML requires JAXB annotations

~~~~~~~~
@XmlRootElement(name="list")
private static class AListPayload{

    public String guid;
    public String title;
    public String description;
    public String owner;
    public String createdDate;
    public String amendedDate;
}
~~~~~~~~

[github.com/rest-assured/rest-assured/wiki/Usage#object-mapping](https://github.com/rest-assured/rest-assured/wiki/Usage#object-mapping)

---

## REST Assured Marshalling with POJO

~~~~~~~~
AListPayload desiredList = new AListPayload();
desiredList.title = "my list " + System.currentTimeMillis();

final Response response = RestAssured.
        given().
            auth().preemptive().
            basic("admin", "password").
        body(desiredList).
        when().
            post(rootUrl + "/lists").andReturn();

Assert.assertEquals(201, response.getStatusCode());
~~~~~~~~

see `RestAssuredMarshallingTest.java` method `simpleCreateListWithJSON`

---

## REST Assured UnMarshalling with POJO

~~~~~~~~
    @XmlRootElement(name = "lists")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class AListsCollectionResponse{
        @XmlElement(name="list")
        public List<AListPayload> lists;
    }
~~~~~~~~

~~~~~~~~
final AListsCollectionResponse lists = 
        RestAssured.
            when().
        get(rootUrl + "/lists").
            as(AListsCollectionResponse.class);

int currentNumberOfLists = lists.lists.size();
~~~~~~~~

see `RestAssuredMarshallingTest.java`

---

## Exercises - Payload Object Abstractions

- Using `RestAssuredMarshallingTest`
- Run tests and make sure you understand them
- It might help to run them through a proxy
- see exercises at the bottom of the class file
