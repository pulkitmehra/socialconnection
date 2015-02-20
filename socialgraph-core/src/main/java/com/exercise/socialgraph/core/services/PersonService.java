package com.exercise.socialgraph.core.services;

import java.util.List;

import com.exercise.socialgraph.core.model.Person;

public interface PersonService {

	List<Person> getPersons() throws PersonServiceException;

	List<Person> getPathToPerson(String fromName, String toName)
			throws PersonServiceException, UnknownPersonException;

	int countPathToPerson(String fromName, String toName)
			throws PersonServiceException, UnknownPersonException;

}
