package uk.co.compendiumdev.restlisticator.payloads;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="list")
public class ListPayload {
    private String title;
    private String guid;
    private String description;
    private String createdDate;
    private String amendedDate;

    public String getGuid() {
        return guid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getAmendedDate() {
        return amendedDate;
    }

    public static ListPayloadBuilder builder() {
        return new ListPayloadBuilder();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setAmendedDate(String amendedDate) {
        this.amendedDate = amendedDate;
    }
}
