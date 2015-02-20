package com.exercise.socialgraph.web.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.exercise.socialgraph.core.model.Person;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@XmlRootElement(name = "Persons")
@JsonTypeName("Persons")
public class PersonList {

    @XmlElement(name = "Person")
    @JsonProperty("Person")
    private List<Person> person;

    public List<Person> getPersons() {
        if (person == null) {
            person = new ArrayList<Person>();
        }
        return person;
    }

}
