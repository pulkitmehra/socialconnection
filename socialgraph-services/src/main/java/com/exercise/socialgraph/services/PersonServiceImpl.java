package com.exercise.socialgraph.services;

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

import com.exercise.socialgraph.core.model.Person;
import com.exercise.socialgraph.core.repository.DataAccessException;
import com.exercise.socialgraph.core.repository.PersonRepository;
import com.exercise.socialgraph.core.services.PersonService;
import com.exercise.socialgraph.core.services.PersonServiceException;
import com.exercise.socialgraph.core.services.UnknownPersonException;

@Guarded
public class PersonServiceImpl implements PersonService {

	private PersonRepository personRepository;

	public PersonServiceImpl(@NotNull PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Person> getPersons() throws PersonServiceException {
		try {
			return personRepository.findAll();
		} catch (DataAccessException e) {
			throw new PersonServiceException(
					"Failed to retreive persons from repository", e);
		}
	}

	@Override
	public List<Person> getPathToPerson(@NotNull @NotEmpty String fromName,
			@NotNull @NotEmpty String toName) throws PersonServiceException,
			UnknownPersonException {

		Optional<Iterable<Person>> path = getPath(fromName, toName);
		return path.isPresent() ? StreamSupport.stream(
				path.get().spliterator(), false).collect(toList())
				: Collections.emptyList();
	}

	@Override
	public int countPathToPerson(String fromName, String toName)
			throws PersonServiceException, UnknownPersonException {
		Optional<Iterable<Person>> path = getPath(fromName, toName);

		if (!path.isPresent()) {
			return -1;
		}

		List<Person> list = StreamSupport.stream(path.get().spliterator(),
				false).collect(toList());

		return list.size() - 2;
	}

	private Optional<Iterable<Person>> getPath(String fromName, String toName)
			throws PersonServiceException, UnknownPersonException {
		Optional<Iterable<Person>> path = null;
		try {
			path = personRepository.findPath(getPersonByName(fromName),
					getPersonByName(toName));
		} catch (DataAccessException e) {
			throw new PersonServiceException(
					format("Failed to retreive path between person '%s' to '%s' from repository",
							fromName, toName), e);
		}
		return path;
	}

	private Person getPersonByName(final String name)
			throws PersonServiceException, UnknownPersonException {
		Optional<Person> person = null;

		try {
			person = personRepository.findByName(name);
		} catch (DataAccessException e) {
			throw new PersonServiceException(format(
					"Failed to retreive person by name '%s' from repository",
					name), e);
		}

		if (!person.isPresent()) {
			throw new UnknownPersonException(String.format(
					"Person with name '%s' does not exist in the system", name));
		}

		return person.get();
	}
}
