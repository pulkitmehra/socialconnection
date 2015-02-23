package com.exercise.socialconnection.services;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import org.springframework.transaction.annotation.Transactional;
import com.exercise.socialconnection.core.model.Person;
import com.exercise.socialconnection.core.repository.DataAccessException;
import com.exercise.socialconnection.core.repository.PersonRepository;
import com.exercise.socialconnection.data.config.PersonRepositoryConfig;
import com.exercise.socialconnection.repository.GraphPersonRepository;
import com.exercise.socialconnection.repository.Neo4jPersonRepository;

/**
 * Implementation class of {@link PersonService}.
 * The underlying data store can be 
 * <ol>
 *      <li> Neo4j based  {@link Neo4jPersonRepository}
 *      <li> Graph based  {@link GraphPersonRepository}
 *  </ol>
 *  
 * Data store is picked based on Spring profiles see {@link PersonRepositoryConfig}
 * 
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
@Guarded
public class PersonServiceImpl implements PersonService {

    /** The person repository. */
    private PersonRepository personRepository;

    /**
     * Instantiates a new person service implementation.
     *
     * @param personRepository the person repository
     */
    public PersonServiceImpl(@NotNull PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Person> getPersons() throws PersonServiceException {
        try {
            return personRepository.findAll();
        }
        catch (DataAccessException e) {
            throw new PersonServiceException("Failed to retreive persons from repository", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Person> getPathToPerson(@NotNull @NotEmpty String fromName,
            @NotNull @NotEmpty String toName) throws PersonServiceException, UnknownPersonException {

        Optional<Iterable<Person>> path = getPath(fromName, toName);
        return path.isPresent() ? StreamSupport.stream(path.get().spliterator(), false).collect(
                toList()) : Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public int getPathToPersonCount(@NotNull @NotEmpty String fromName,
            @NotNull @NotEmpty String toName) throws PersonServiceException, UnknownPersonException {
        Optional<Iterable<Person>> path = getPath(fromName, toName);

        if (!path.isPresent()) {
            return -1;
        }

        List<Person> list = StreamSupport.stream(path.get().spliterator(), false).collect(toList());

        return list.size() - 2;
    }

    /**
     * Gets the path.
     *
     * @param fromName the from name
     * @param toName the to name
     * @return the path
     * @throws PersonServiceException the person service exception
     * @throws UnknownPersonException the unknown person exception
     */
    private Optional<Iterable<Person>> getPath(String fromName, String toName)
            throws PersonServiceException, UnknownPersonException {
        Optional<Iterable<Person>> path = null;
        try {
            path = personRepository.findPath(getPersonByName(fromName), getPersonByName(toName));
        }
        catch (DataAccessException e) {
            throw new PersonServiceException(format(
                    "Failed to retreive path between person '%s' to '%s' from repository",
                    fromName, toName), e);
        }
        return path;
    }

    /**
     * Gets the person by name.
     *
     * @param name the name
     * @return the person by name
     * @throws PersonServiceException the person service exception
     * @throws UnknownPersonException the unknown person exception
     */
    private Person getPersonByName(final String name) throws PersonServiceException,
            UnknownPersonException {
        Optional<Person> person = null;

        try {
            person = personRepository.findByName(name);
        }
        catch (DataAccessException e) {
            throw new PersonServiceException(format(
                    "Failed to retreive person by name '%s' from repository", name), e);
        }

        if (!person.isPresent()) {
            throw new UnknownPersonException(String.format(
                    "Person with name '%s' does not exist in the system", name));
        }

        return person.get();
    }
}
