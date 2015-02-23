package com.exercise.socialconnection.repository;

import static com.exercise.socialconnection.data.config.DataBuilder.p;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.exercise.socialconnection.core.model.Person;
import com.exercise.socialconnection.core.repository.DataAccessException;
import com.exercise.socialconnection.core.repository.PersonRepository;
import com.exercise.socialconnection.data.EmbeddedDatabase;
import com.exercise.socialconnection.data.config.DataBuilder;
import com.exercise.socialconnection.data.config.PersonRepositoryConfig;

/**
 * Test for {@link Neo4jPersonRepository}
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Neo4jPersonRepoistoryTest.Config.class })
@Transactional
@ActiveProfiles("Neo4j")
public class Neo4jPersonRepoistoryTest {

    @Autowired
    private PersonRepository neo4jpersonRepository;

    @Autowired
    private DataBuilder dataBuilder;

    @Autowired
    private GraphDatabaseService graphDatabaseService;

    @Autowired
    private Neo4jTemplate neo4jTemplate;

    private List<Person> persons;
    private Map<String, Long> personNameMap;

    @Before
    public void setUp() {
        Assert.assertNotNull(graphDatabaseService);
        Assert.assertNotNull(neo4jTemplate);
        assertNotNull(neo4jpersonRepository);
        assertNotNull(dataBuilder);
        dataBuilder.cleanDB();
        persons = dataBuilder.addPersonsToDB();
        assertNotNull(persons);

        personNameMap = DataBuilder.mapByName(persons);

    }

    @Test
    public void testFindAll() throws DataAccessException {
        List<Person> allPersons = neo4jpersonRepository.findAll();
        Assert.assertEquals(allPersons.size(), 7);
        assertThat(allPersons, hasItems(persons.get(0), persons.get(1)));
    }

    @Test
    public void testFindAllEmptyList() throws DataAccessException {
        dataBuilder.cleanDB();
        List<Person> allPersons = neo4jpersonRepository.findAll();
        Assert.assertEquals(allPersons.size(), 0);
    }

    @Test
    public void testFindByName() throws DataAccessException {
        Optional<Person> person = neo4jpersonRepository.findByName("Foo");
        Assert.assertTrue(person.isPresent());
        Assert.assertEquals(person.get().getId(), personNameMap.get("Foo").longValue());
    }

    @Test
    public void testFindByUnknownName() throws DataAccessException {
        Optional<Person> person = neo4jpersonRepository.findByName("UNKNOWN-NAME");
        Assert.assertFalse(person.isPresent());
    }

    @Test
    public void testFindByID() throws DataAccessException {
        Optional<Person> person = neo4jpersonRepository.findByID(persons.get(0).getId());
        Assert.assertTrue(person.isPresent());
        Assert.assertEquals(person.get().getName(), persons.get(0).getName());
    }

    @Test
    public void testFindByUnknownID() throws DataAccessException {
        Optional<Person> person = neo4jpersonRepository.findByID(-1);
        Assert.assertFalse(person.isPresent());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testfindPath() throws DataAccessException {
        Optional<Iterable<Person>> path = neo4jpersonRepository.findPath(p(1, "Foo"), p(5, "Tim"));
        Assert.assertTrue(path.isPresent());
        long size = StreamSupport.stream(path.get().spliterator(), false).count();

        assertEquals(3, size);

        List<String> names = StreamSupport.stream(path.get().spliterator(), false)
                .map(Person::getName).collect(toList());

        assertThat(names, hasItems(is("Foo"), is("Bar"), is("Tim")));
    }

    @Test
    public void testfindPathWithUnknownPerson() throws DataAccessException {
        Optional<Iterable<Person>> path = neo4jpersonRepository.findPath(p(1, "Foo"),
                p(-1, "Unknown"));
        Assert.assertFalse(path.isPresent());
    }

    @EmbeddedDatabase
    @Import(PersonRepositoryConfig.class)
    static class Config {

        @Bean
        public DataBuilder dataBuilder(Neo4jTemplate neo4jTemplate) {
            return new DataBuilder(neo4jTemplate);
        }

        @Bean
        public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
        }

    }
}
