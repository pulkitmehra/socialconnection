package com.exercise.socialgraph.web.adapters;

import com.exercise.socialgraph.core.services.PersonServiceException;
import com.exercise.socialgraph.core.services.UnknownPersonException;
import com.exercise.socialgraph.web.model.PersonList;

public interface PersonWebAdapter {

    PersonList getPersons() throws PersonServiceException;

    PersonList getPathToPerson(String fromName, String toName) throws PersonServiceException,
            UnknownPersonException;;

    int countPathToPerson(String fromName, String toName) throws PersonServiceException,
            UnknownPersonException;

}
