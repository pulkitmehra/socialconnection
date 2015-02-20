package com.exercise.socialgraph.data.repository;

import static java.util.stream.Collectors.toList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import com.exercise.socialgraph.core.model.Person;
import com.exercise.socialgraph.core.repository.DataAccessException;
import com.exercise.socialgraph.core.repository.PersonRepository;
import com.exercise.socialgraph.data.graph.Graph;
import com.exercise.socialgraph.data.graph.GraphUtil;

@Guarded
public class GraphPersonRepository implements PersonRepository {

    private Graph<Person> personGraph;

    public GraphPersonRepository(@NotNull Graph<Person> personGraph) {
        this.personGraph = personGraph;
    }

    @Override
    public List<Person> findAll() throws DataAccessException {
        return StreamSupport.stream(personGraph.vertices().spliterator(), false).collect(toList());
    }

    @Override
    public Optional<Person> findByID(@Min(1) long id) throws DataAccessException {
        return GraphUtil.bfs(personGraph).find((p) -> (p.getId() == id));
    }

    @Override
    public Optional<Person> findByName(@NotNull @NotEmpty String name) throws DataAccessException {
        return GraphUtil.bfs(personGraph).find((p) -> (name.equals(p.getName())));
    }

    @Override
    public Optional<Iterable<Person>> findPath(@NotNull Person fromPerson, @NotNull Person toPerson)
            throws DataAccessException {
        return Optional.ofNullable(GraphUtil.bfs(personGraph).pathTo(fromPerson, toPerson));
    }

}
