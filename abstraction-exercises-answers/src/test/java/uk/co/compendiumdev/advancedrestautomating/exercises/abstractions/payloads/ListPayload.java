package uk.co.compendiumdev.advancedrestautomating.exercises.abstractions.payloads;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "list")
public class ListPayload {

    public String guid;
    public String title;
    public String description;
    public String owner;
    public String createdDate;
    public String amendedDate;

    public ListPayload(){
    }

    public ListPayload(String aTitle){
        this.title = aTitle;
    }

    public ListPayload(final String guid,
                        final String title,
                        final String description,
                        final String createdDate,
                        final String amendedDate) {
        this.guid=guid;
        this.title=title;
        this.description=description;
        this.createdDate=createdDate;
        this.amendedDate=amendedDate;
    }


    // need to add getters in order to use JsonBuilder to convert object to JSON
    public String getGuid(){
        return this.guid;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public String getOwner(){
        return this.owner;
    }

    public String getCreatedDate(){
        return this.createdDate;
    }

    public String getAmendedDate(){
        return this.amendedDate;
    }

}
