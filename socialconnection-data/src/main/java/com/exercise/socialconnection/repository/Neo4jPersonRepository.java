package com.exercise.socialconnection.repository;

import static com.exercise.socialconnection.data.config.ConfigUtil.map;
import static java.lang.String.format;
import static java.util.Optional.of;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import com.exercise.socialconnection.core.model.Person;
import com.exercise.socialconnection.core.repository.DataAccessException;
import com.exercise.socialconnection.core.repository.PersonRepository;

/**
 * Neo4j based implementation of {@link PersonRepository}.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
@Guarded
public class Neo4jPersonRepository implements PersonRepository {

    /** The Constant PARAM_TO_NAME. */
    private static final String PARAM_TO_NAME = "toName";

    /** The Constant PARAM_FROM_NAME. */
    private static final String PARAM_FROM_NAME = "fromName";

    /** The Constant PARAM_NAME. */
    private static final String PARAM_NAME = "name";

    /** The neo4j template. */
    private Neo4jTemplate neo4jTemplate;

    /** The find all cypher. */
    private String findAllCypher;

    /** The find by name cypher. */
    private String findByNameCypher;

    /** The path to cypher. */
    private String pathToCypher;

    /**
     * Instantiates a new neo4j person repository.
     *
     * @param neo4jTemplate the neo4j template
     */
    public Neo4jPersonRepository(@NotNull Neo4jTemplate neo4jTemplate) {
        this.neo4jTemplate = neo4jTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Person> findAll() throws DataAccessException {

        Result<Map<String, Object>> rs = null;
        try {
            rs = neo4jTemplate.query(findAllCypher, Collections.emptyMap());
        }
        catch (Exception e) {
            throw new DataAccessException("Failed to retreive data for type person", e);
        }

        List<Person> persons = new ArrayList<>();

        rs.forEach((t) -> {
            t.values().stream().map((n) -> buildPerson((Node) n)).forEach(persons::add);
        });

        return persons;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Person> findByID(@Min(1) long id) throws DataAccessException {
        Node node = null;
        try {
            node = neo4jTemplate.getNode(id);
        }
        catch (Exception e) {
            return Optional.empty();
        }

        return of(buildPerson(node));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Person> findByName(@NotNull @NotEmpty String name) throws DataAccessException {
        Optional<Node> node = findNodeByName(name);

        if (!node.isPresent()) {
            return Optional.empty();
        }
        return Optional.ofNullable(buildPerson(node.get()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Iterable<Person>> findPath(@NotNull Person fromPerson, @NotNull Person toPerson)
            throws DataAccessException {

        Map<String, Object> params = new HashMap<>();
        params.put(PARAM_FROM_NAME, fromPerson.getName());
        params.put(PARAM_TO_NAME, toPerson.getName());

        Result<Map<String, Object>> rs = null;
        try {
            rs = neo4jTemplate.query(pathToCypher, params);

        }
        catch (Exception e) {
            throw new DataAccessException(format(
                    "Failed to retreive path between two person '%s' and '%s'",
                    fromPerson.getName(), toPerson.getName()), e);
        }

        if (rs != null && !rs.iterator().hasNext()) {
            return Optional.empty();
        }

        Path path = (Path) rs.single().values().stream().findFirst().get();
        Iterable<Node> nodes = path.nodes();
        if (nodes == null || !nodes.iterator().hasNext()) {
            return Optional.empty();
        }

        List<Person> persons = new LinkedList<>();
        for (Node node : nodes) {
            persons.add(buildPerson(node));
        }
        return Optional.of(persons);
    }

    /**
     * Builds the person.
     *
     * @param node the node
     * @return the person
     */
    public Person buildPerson(@NotNull Node node) {
        Person p = new Person();
        p.setName((String) node.getProperty(Person.PROP_NAME));
        p.setId(node.getId());
        return p;
    }

    /**
     * Find Neo4j node by name.
     *
     * @param name the name
     * @return the optional
     * @throws DataAccessException the data access exception
     */
    private Optional<Node> findNodeByName(String name) throws DataAccessException {
        Result<Map<String, Object>> rs = null;
        try {
            rs = neo4jTemplate.query(findByNameCypher, map(PARAM_NAME, name));
        }
        catch (Exception e) {
            throw new DataAccessException(format("Failed to retreive person by name %s", name), e);
        }
        if (!rs.iterator().hasNext()) {
            return Optional.empty();
        }
        return Optional.of((Node) rs.single().values().stream().findFirst().get());
    }

    /**
     * Sets the find all cypher.
     *
     * @param findAllCypher the new find all cypher
     */
    @Required
    public void setFindAllCypher(String findAllCypher) {
        this.findAllCypher = findAllCypher;
    }

    /**
     * Sets the find by name cypher.
     *
     * @param findByNameCypher the new find by name cypher
     */
    @Required
    public void setFindByNameCypher(String findByNameCypher) {
        this.findByNameCypher = findByNameCypher;
    }

    /**
     * Sets the path to cypher.
     *
     * @param pathToCypher the new path to cypher
     */
    @Required
    public void setPathToCypher(String pathToCypher) {
        this.pathToCypher = pathToCypher;
    }

}
