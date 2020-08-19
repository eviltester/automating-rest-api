---

# SECTION - ALTERNATIVE MARSHALLING STRATEGIES

---

## REST Assured is a wrapper

- REST Assured is a wrapper around Jaxb and GSON
- We could use alternative marshalling/unmarshalling if we wanted
- Might avoid reliance on REST Assured

---

## REST Assured JsonPath and Jaxb

Converting to an object - uses Gson under the covers

~~~~~~~~
AListsCollectionResponse lists = 
    new JsonPath(getLists.body().asString()).
        getObject(".", AListsCollectionResponse.class);
~~~~~~~~

Converting from an object uses Groovy `JsonBuilder`

~~~~~~~~
final String payload = 
    new JsonBuilder(payloadObject).toString();
~~~~~~~~

- see `RestAssuredJsonPathMarshallingTest`

---

## We could use Gson directly

Converting to an Object

~~~~~~~~
AListsCollectionResponse lists = 
        new Gson().fromJson(
            getLists.body().asString(),
            AListsCollectionResponse.class);
~~~~~~~~

Coverting Object to Json

~~~~~~~~
AListPayload payloadObject = 
    new AListPayload("my list title");
String payload = new Gson().toJson(payloadObject);
~~~~~~~~

Benefit - Gson is widely used and documented

---

## Rest Assured using XmlPath and Jaxb

Convert XML to an Object

~~~~~~~~
AListsCollectionResponse lists = 
    new XmlPath(response.body().asString()).
        getObject(".", AListsCollectionResponse.class);
~~~~~~~~

Convert Object to XML using Jaxb

~~~~~~~~
AListPayload desiredList = new AListPayload("my list title");
StringWriter payloadWriter = new StringWriter();
JAXB.marshal(desiredList, payloadWriter);
String payload = payloadWriter.toString();
~~~~~~~~

- Jaxb is widely known, but requires annotations on objects

---

## Pure Jaxb implementation

I had to remove the BOM (Byte order mark) to allow Jaxb to process the message

~~~~~~~~
String fixOutput = response.body().asString()
        .replace("\uFEFF", "");

AListsCollectionResponse lists = 
    JAXB.unmarshal(new StringReader(fixOutput),
                    AListsCollectionResponse.class);
~~~~~~~~

No change to converting object to XML

---

## Xstream Implementation

- XStream is from thoughtworks
- requires no annotations to class - configured from within code
- http://x-stream.github.io/

~~~~~~~~
xstream = new XStream();

xstream.allowTypesByWildcard(new String[] {
        "uk.co.compendiumdev.advancedrestautomating.**"
});

xstream.alias("lists", AListsCollectionResponse.class);
xstream.addImplicitCollection(AListsCollectionResponse.class,
            "lists");
xstream.alias("list", AListPayload.class);
~~~~~~~~

---

## Xstream Implmentation for Conversion

Convert from XML to Object

~~~~~~~~
AListsCollectionResponse lists = (AListsCollectionResponse) 
    xstream.fromXML(response.body().asString());
~~~~~~~~

Convert from Object to XML

~~~~~~~~
AListPayload desiredList = new AListPayload("my list title");

// xstream does not add the XML header by default
String header = "<?xml version=\"1.0\" encoding" + 
                "=\"UTF-8\" standalone=\"yes\"?>\n";
String payload = header + xstream.toXML(desiredList);
~~~~~~~~

- Xstream is lightweight if you need to deploy a jar
- configurable within code so POJOs without annotations

---

## Why?

If we ever want to split our code into:

- HTTP Abstractions
- Domain Abstractions

Then we may be able to avoid RestAssured 'bleeding' into the other areas.

---

# Demos using the marshalling package

- `RestAssuredMarshallingTest`
    - uses RestAssured to convert to/from objects
- `RestAssuredJsonPathMarshallingTest`
    - uses `JsonPath` and `JsonBuilder` directly
- `GsonMarshallingTest`
    - uses `Gson` directly
- `RestAssuredXmlPathMarshallingTest`
    - uses `XmlPath` and `Jaxb` directly
- `JaxbXmlMarshallingTest`
    - uses `Jaxb` directly
- `XstreamXmlMarshallingTest`
    - uses `Xstream` directly

