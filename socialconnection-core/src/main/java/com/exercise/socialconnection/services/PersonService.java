package com.exercise.socialconnection.services;

import java.util.List;
import com.exercise.socialconnection.core.model.Person;

/**
 * Interface PersonService.
 * It encapsulates all {@link Person} related operation.
 * Caller should use this facade for all person operations.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
public interface PersonService {

    /**
     * Gets the persons or empty list if none exist
     *
     * @return the persons {@link List}
     * @throws PersonServiceException the person service exception if any underlying error occurred
     */
    List<Person> getPersons() throws PersonServiceException;

    /**
     * Gets List of persons between two given names or empty {@link List} if path does not exist.
     *
     * @param fromName the from name
     * @param toName the to name
     * @return the path to person
     * @throws PersonServiceException the person service exception if any underlying error occurred
     * @throws UnknownPersonException if the name passed does not exist in the system
     * @throws IllegalArgumentException if fromName and toName is null or empty
     * @See {@link PersonService#getPathToPersonCount(String, String)}
     */
    List<Person> getPathToPerson(String fromName, String toName) throws PersonServiceException,
            UnknownPersonException;

    /**
     * Gets count of persons between two given names or -1 if path does not exist.
     *
     * @param fromName the from name
     * @param toName the to name
     * @return the path to person count
     * @throws PersonServiceException the person service exception if any underlying error occurred
     * @throws UnknownPersonException if the name passed does not exist in the system
     * @throws IllegalArgumentException if fromName and toName is null or empty
     * @see PersonService#getPathToPerson(String, String) 
     */
    int getPathToPersonCount(String fromName, String toName) throws PersonServiceException,
            UnknownPersonException;

}
