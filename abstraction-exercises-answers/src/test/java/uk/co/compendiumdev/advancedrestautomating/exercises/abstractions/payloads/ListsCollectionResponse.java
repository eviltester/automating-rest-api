package uk.co.compendiumdev.advancedrestautomating.exercises.abstractions.payloads;

import uk.co.compendiumdev.advancedrestautomating.exercises.abstractions.payloads.ListPayload;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "lists")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListsCollectionResponse {

        @XmlElement(name="list")
        public List<ListPayload> lists;

        public ListsCollectionResponse(){
                lists = new ArrayList<>();
        }
}
