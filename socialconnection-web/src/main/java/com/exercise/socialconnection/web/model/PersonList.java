package com.exercise.socialconnection.web.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.exercise.socialconnection.core.model.Person;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * The Class PersonList.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
@XmlRootElement(name = "Persons")
@JsonTypeName("Persons")
public class PersonList {

    /** The person. */
    @XmlElement
    private List<Person> person;

    /**
     * Gets the persons.
     *
     * @return the persons
     */
    public List<Person> getPersons() {
        if (person == null) {
            person = new ArrayList<Person>();
        }
        return person;
    }

}
