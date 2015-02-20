package com.exercise.socialgraph.core.repository;

import java.util.List;
import java.util.Optional;

import com.exercise.socialgraph.core.model.Person;

public interface PersonRepository {

	List<Person> findAll() throws DataAccessException;

	Optional<Person> findByID(long id) throws DataAccessException;

	Optional<Person> findByName(String name) throws DataAccessException;

	Optional<Iterable<Person>> findPath(Person formPerson, Person toPerson)
			throws DataAccessException;

}
