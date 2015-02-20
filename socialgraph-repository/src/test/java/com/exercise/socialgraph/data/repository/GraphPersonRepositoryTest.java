package com.exercise.socialgraph.data.repository;

import static com.exercise.socialgraph.data.DataBuilder.p;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.exercise.socialgraph.core.model.Person;
import com.exercise.socialgraph.core.repository.DataAccessException;
import com.exercise.socialgraph.core.repository.PersonRepository;
import com.exercise.socialgraph.data.DataBuilder;
import com.exercise.socialgraph.data.config.PersonRepositoryConfig;
import com.exercise.socialgraph.data.graph.GraphUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersonRepositoryConfig.class })
@ActiveProfiles("Graph")
public class GraphPersonRepositoryTest {

	private PersonRepository personRepository;

	@Autowired
	private PersonRepository personRepositoryViaSpring;

	@Before
	public void setUp() {
		personRepository = new GraphPersonRepository(
				GraphUtil.graph(DataBuilder.personEdges()));
	}

	@Test
	public void shouldInjectDependency() {
		assertNotNull(personRepositoryViaSpring);
	}

	@Test
	public void testFindAll() throws DataAccessException {
		List<Person> persons = personRepository.findAll();
		assertEquals(persons.size(), 7);
	}

	@Test
	public void testFindById() throws DataAccessException {
		Optional<Person> person = personRepository.findByID(1L);
		assertTrue(person.isPresent());
		assertEquals(person.get().getName(), "Foo");
	}

	@Test
	public void testFindByInvalidId() throws DataAccessException {
		Optional<Person> person = personRepository.findByID(-1L);
		assertFalse(person.isPresent());
	}

	@Test
	public void testFindByName() throws DataAccessException {
		Optional<Person> person = personRepository.findByName("Foo");
		assertTrue(person.isPresent());
		assertEquals(person.get().getId(), 1L);
	}

	@Test
	public void testFindByInvalidName() throws DataAccessException {
		Optional<Person> person = personRepository.findByName("UNKNOWN");
		assertFalse(person.isPresent());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testfindPath() throws DataAccessException {
		Optional<Iterable<Person>> path = personRepository.findPath(
				p(1, "Foo"), p(5, "Tim"));
		assertTrue(path.isPresent());
		assertEquals(StreamSupport.stream(path.get().spliterator(), false)
				.count(), 3);
		List<String> names = StreamSupport
				.stream(path.get().spliterator(), false).map(Person::getName)
				.collect(toList());

		assertThat(names, hasItems(is("Foo"), is("Bar"), is("Tim")));

	}

	@Test
	public void testfindPathUnknownPerson() throws DataAccessException {
		Optional<Iterable<Person>> path = personRepository.findPath(
				p(1, "Foo"), p(-1, "UNKNOWN"));
		assertFalse(path.isPresent());
	}

}
