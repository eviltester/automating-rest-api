package uk.co.compendiumdev.restlisticator.payloads;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="list")
public class AListPayload {
    public String title;
    public String guid;
    public String description;
    public String createdDate;
    public String amendedDate;
}
