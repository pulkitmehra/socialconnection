package com.exercise.socialconnection.repository;

import static java.util.stream.Collectors.toList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import com.exercise.socialconnection.core.model.Person;
import com.exercise.socialconnection.core.repository.DataAccessException;
import com.exercise.socialconnection.core.repository.PersonRepository;
import com.exercise.socialconnection.data.graph.Graph;
import com.exercise.socialconnection.data.graph.GraphUtil;

/**
 * {@link Graph} backed implementation of {@link PersonRepository}.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
@Guarded
public class GraphPersonRepository implements PersonRepository {

    /** The person graph. */
    private Graph<Person> personGraph;

    /**
     * Instantiates a new graph person repository.
     *
     * @param personGraph the person graph
     */
    public GraphPersonRepository(@NotNull Graph<Person> personGraph) {
        this.personGraph = personGraph;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Person> findAll() throws DataAccessException {
        return StreamSupport.stream(personGraph.vertices().spliterator(), false).collect(toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Person> findByID(@Min(1) long id) throws DataAccessException {
        return GraphUtil.bfs(personGraph).find((p) -> (p.getId() == id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Person> findByName(@NotNull @NotEmpty String name) throws DataAccessException {
        return GraphUtil.bfs(personGraph).find((p) -> (name.equals(p.getName())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Iterable<Person>> findPath(@NotNull Person fromPerson, @NotNull Person toPerson)
            throws DataAccessException {
        return Optional.ofNullable(GraphUtil.bfs(personGraph).pathTo(fromPerson, toPerson));
    }

}
