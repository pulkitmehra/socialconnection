package com.exercise.socialconnection.core.repository;

import java.util.List;
import java.util.Optional;
import com.exercise.socialconnection.core.model.Person;

/**
 * The Interface PersonRepository.
 * It has all data related operations for entity {@link Person}.
 * 
 * It has two subclass implementation
 * <ol>
 *  <li>An implementation which encapsulates Neo4j graph database
 *  <li>An implementation of Graph itself
 * </ol> 
 * 
 * The subclass initialization are decided by Configuration.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
public interface PersonRepository {

    /**
     * Find all person from data store.
     *
     * @return the list of person
     * @throws DataAccessException the data access exception in case of any data error
     */
    List<Person> findAll() throws DataAccessException;

    /**
     * Find person by ID from data store.
     *
     * @param id the id
     * @return the optional
     * @throws DataAccessException the data access exception in case of any data error
     * @throws IllegalArgumentException if id is less than 1
     */
    Optional<Person> findByID(long id) throws DataAccessException;

    /**
     * Find person by name.
     *
     * @param name the name of person
     * @return the person or {@link Optional} empty
     * @throws DataAccessException the data access exception in case of any data error
     * @throws IllegalArgumentException if name is null or empty
     */
    Optional<Person> findByName(String name) throws DataAccessException;

    /**
     * Find path between two given person.
     *
     * @param formPerson the person as starting point
     * @param toPerson traverse till person
     * @return the {@link Iterable} of person or {@link Optional} empty
     * @throws DataAccessException the data access exception in case of any data error.
     *  @throws IllegalArgumentException if fromName or toName person is null.
     */
    Optional<Iterable<Person>> findPath(Person formPerson, Person toPerson)
            throws DataAccessException;

}
