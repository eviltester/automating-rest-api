package uk.co.compendiumdev.restlisticator.payloads;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "lists")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListsPayload {

    @XmlElement(name="list")
    private List<ListPayload> lists;

    public List<ListPayload> getLists() {
        return lists;
    }

    public void setLists(List<ListPayload> lists) {
        this.lists = lists;
    }
}
