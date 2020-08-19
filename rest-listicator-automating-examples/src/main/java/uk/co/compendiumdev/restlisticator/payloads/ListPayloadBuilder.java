package uk.co.compendiumdev.restlisticator.payloads;

public class ListPayloadBuilder {
    private ListPayload listPayload;

    public static ListPayloadBuilder create(){
        return new ListPayloadBuilder();
    }

    public ListPayloadBuilder(){
        listPayload = new ListPayload();
    }

    public ListPayloadBuilder with() {
        return this;
    }

    public ListPayloadBuilder title(String title) {
        listPayload.setTitle(title);
        return this;
    }

    public ListPayloadBuilder description(String description) {
        listPayload.setDescription(description);
        return this;
    }

    public ListPayloadBuilder and() {
        return this;
    }

    public ListPayloadBuilder guid(String guid) {
        listPayload.setGuid(guid);
        return this;
    }

    public ListPayload build() {
        return listPayload;
    }

    public ListPayloadBuilder createdDate(String createdDate) {
        listPayload.setCreatedDate(createdDate);
        return this;
    }

    public ListPayloadBuilder amendedDate(String amendedDate) {
        listPayload.setAmendedDate(amendedDate);
        return this;
    }
}
