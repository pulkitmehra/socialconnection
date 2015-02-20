package com.exercise.socialgraph.services;

import static com.exercise.socialgraph.services.PersonDataBuilder.p;
import static com.exercise.socialgraph.services.PersonDataBuilder.persons;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.exercise.socialgraph.core.model.Person;
import com.exercise.socialgraph.core.repository.DataAccessException;
import com.exercise.socialgraph.core.repository.PersonRepository;
import com.exercise.socialgraph.core.services.PersonService;
import com.exercise.socialgraph.core.services.PersonServiceException;
import com.exercise.socialgraph.core.services.UnknownPersonException;

public class PersonServiceImplTest {

	private static final String KIM = "Kim";
	private static final String UNKNOWN = "Unknown";
	private static final String BAR = "Bar";
	private static final String FOO = "Foo";
	private static final String TIM = "Tim";

	@Mock
	private PersonRepository personRepositoryMck;

	private PersonService personService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		personService = new PersonServiceImpl(personRepositoryMck);
	}

	@Test
	public void testGetPersons() throws Exception {
		when(personRepositoryMck.findAll()).thenReturn(persons());
		List<Person> persons = personService.getPersons();
		assertNotNull(persons);
		assertEquals(7, persons.size());

	}

	@Test(expected = PersonServiceException.class)
	public void testGetPersonsServiceException() throws Exception {
		when(personRepositoryMck.findAll()).thenThrow(
				new DataAccessException(StringUtils.EMPTY));
		personService.getPersons();

		verify(personRepositoryMck).findAll();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetPathToPerson() throws Exception {

		Person foo = p(1, FOO);
		Person bar = p(2, BAR);
		Person tim = p(5, TIM);

		when(personRepositoryMck.findByName(FOO)).thenReturn(Optional.of(foo));

		when(personRepositoryMck.findByName(TIM)).thenReturn(Optional.of(tim));

		List<Person> persons = new ArrayList<Person>();
		persons.add(foo);
		persons.add(bar);
		persons.add(tim);

		when(personRepositoryMck.findPath(foo, tim)).thenReturn(
				Optional.of(persons));

		List<Person> pathToPerson = personService.getPathToPerson(FOO, TIM);

		assertNotNull(pathToPerson);
		assertThat(pathToPerson,
				hasItems(equalTo(foo), equalTo(bar), equalTo(tim)));

		verify(personRepositoryMck).findByName(FOO);
		verify(personRepositoryMck).findByName(TIM);
		verify(personRepositoryMck).findPath(foo, tim);
	}

	@Test
	public void testCountPathToPerson() throws Exception {

		Person foo = p(1, FOO);
		Person bar = p(2, BAR);
		Person tim = p(5, TIM);

		when(personRepositoryMck.findByName(FOO)).thenReturn(Optional.of(foo));
		when(personRepositoryMck.findByName(TIM)).thenReturn(Optional.of(tim));

		List<Person> persons = new ArrayList<Person>();
		persons.add(foo);
		persons.add(bar);
		persons.add(tim);

		when(personRepositoryMck.findPath(foo, tim)).thenReturn(
				Optional.of(persons));

		int hops = personService.countPathToPerson(FOO, TIM);

		assertTrue(hops == 1);

		verify(personRepositoryMck).findByName(FOO);
		verify(personRepositoryMck).findByName(TIM);
		verify(personRepositoryMck).findPath(foo, tim);
	}

	@Test
	public void testCountPathToPersonZeroHops() throws Exception {

		Person foo = p(1, FOO);
		Person bar = p(2, BAR);

		when(personRepositoryMck.findByName(FOO)).thenReturn(Optional.of(foo));
		when(personRepositoryMck.findByName(BAR)).thenReturn(Optional.of(bar));

		List<Person> persons = new ArrayList<Person>();
		persons.add(foo);
		persons.add(bar);

		when(personRepositoryMck.findPath(foo, bar)).thenReturn(
				Optional.of(persons));

		int hops = personService.countPathToPerson(FOO, BAR);
		assertTrue(hops == 0);

		verify(personRepositoryMck).findByName(FOO);
		verify(personRepositoryMck).findByName(BAR);
		verify(personRepositoryMck).findPath(foo, bar);
	}

	@Test
	public void testCountPathToPersonNotConnected() throws Exception {

		Person foo = p(1, FOO);
		Person kim = p(6, KIM);

		when(personRepositoryMck.findByName(FOO)).thenReturn(Optional.of(foo));
		when(personRepositoryMck.findByName(KIM)).thenReturn(Optional.of(kim));
		when(personRepositoryMck.findPath(foo, kim)).thenReturn(
				Optional.empty());

		int hops = personService.countPathToPerson(FOO, KIM);
		assertTrue(hops == -1);

		verify(personRepositoryMck).findByName(FOO);
		verify(personRepositoryMck).findByName(KIM);
		verify(personRepositoryMck).findPath(foo, kim);
	}

	@Test(expected = UnknownPersonException.class)
	public void testGetPathToUnknownPerson() throws Exception {

		Person foo = p(1, FOO);

		when(personRepositoryMck.findByName(FOO)).thenReturn(Optional.of(foo));
		when(personRepositoryMck.findByName(UNKNOWN)).thenReturn(
				Optional.empty());

		personService.getPathToPerson(FOO, UNKNOWN);
	}

	@Test(expected = PersonServiceException.class)
	public void testGetPathToServiceException() throws Exception {
		when(personRepositoryMck.findByName(FOO)).thenThrow(
				new DataAccessException(StringUtils.EMPTY));
		personService.getPathToPerson(FOO, BAR);
	}

}
