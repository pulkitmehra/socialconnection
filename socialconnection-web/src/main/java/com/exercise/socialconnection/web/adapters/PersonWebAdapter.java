package com.exercise.socialconnection.web.adapters;

import java.util.List;
import com.exercise.socialconnection.core.model.Person;
import com.exercise.socialconnection.services.PersonServiceException;
import com.exercise.socialconnection.services.UnknownPersonException;
import com.exercise.socialconnection.web.model.InfoResponse;
import com.exercise.socialconnection.web.model.PersonList;

/**
 * The Interface PersonWebAdapter.
 * It is an interface to receive {@link Person} entity REST calls.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
public interface PersonWebAdapter {

    /**
     * Gets {@link List} of  persons or empty {@link List} if none found
     *
     * @return the persons
     * @throws PersonServiceException the person service exception if any underlying error occurred
     */
    PersonList getPersons() throws PersonServiceException;

    /**
     * Gets List of person path between two given names.
     *
     * @param fromName the from name
     * @param toName the to name
     * @return the path to person
     * @throws UnknownPersonException if the name passed does not exist in the system
     * @throws IllegalArgumentException if fromName and toName is null or empty
     */
    PersonList getPathToPerson(String fromName, String toName) throws PersonServiceException,
            UnknownPersonException;;

    /**
     * Gets path to person count.
     *
     * @param fromName the from name
     * @param toName the to name
     * @return the path to person count
     * @throws UnknownPersonException if the name passed does not exist in the system
     * @throws IllegalArgumentException if fromName and toName is null or empty
     */
    InfoResponse getPathToPersonCount(String fromName, String toName)
            throws PersonServiceException, UnknownPersonException;

}
