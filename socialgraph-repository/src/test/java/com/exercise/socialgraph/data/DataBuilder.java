package com.exercise.socialgraph.data;

import static com.exercise.socialgraph.core.model.Person.PROP_NAME;
import static com.exercise.socialgraph.core.model.Person.TYPE;
import static com.exercise.socialgraph.core.model.Relation.KNOWS;
import static com.exercise.socialgraph.data.config.ConfigUtil.list;
import static com.exercise.socialgraph.data.config.ConfigUtil.map;
import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.oval.constraint.NotNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.neo4j.graphdb.Node;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.support.node.Neo4jHelper;
import com.exercise.socialgraph.core.model.Person;
import com.exercise.socialgraph.core.model.Relation;

public class DataBuilder {

    private Neo4jTemplate neo4jTemplate;

    public DataBuilder(Neo4jTemplate neo4jTemplate) {
        this.neo4jTemplate = neo4jTemplate;
    }

    public void cleanDB() {
        Neo4jHelper.cleanDb(neo4jTemplate);
    }

    public List<Person> addPersonsToDB() {
        List<Person> persons = persons();
        persons.stream().forEach((p) -> addNodeToDB(p));
        Map<String, Long> nameMap = mapByName(persons);

        // (Foo) - (Bar) - (Tim)
        addRel(getByID(nameMap.get("Foo")), getByID(nameMap.get("Bar")), KNOWS);
        addRel(getByID(nameMap.get("Bar")), getByID(nameMap.get("Tim")), KNOWS);

        // (Joe) - (Moe) - (Tim)
        addRel(getByID(nameMap.get("Joe")), getByID(nameMap.get("Moe")), KNOWS);
        addRel(getByID(nameMap.get("Moe")), getByID(nameMap.get("Tim")), KNOWS);

        // (Kim) - (Lee)
        addRel(getByID(nameMap.get("Kim")), getByID(nameMap.get("Lee")), KNOWS);

        return persons;
    }

    private Node getByID(@NotNull long id) {
        return neo4jTemplate.getNode(id);
    }

    private void addRel(Node fromPerson, Node toPerson, Relation relType) {
        neo4jTemplate.createRelationshipBetween(fromPerson, toPerson, KNOWS.name(), emptyMap());
    }

    private void addNodeToDB(Person p) {
        Node node = neo4jTemplate.createNode(map(PROP_NAME, p.getName()), list(TYPE.name()));
        p.setId(node.getId());
    }

    public static Map<String, Long> mapByName(List<Person> persons) {
        return persons.stream().collect(toMap(Person::getName, Person::getId));
    }

    public static List<Person> persons() {
        List<Person> persons = new ArrayList<Person>();
        persons.add(new Person(1, "Foo"));
        persons.add(new Person(2, "Bar"));
        persons.add(new Person(3, "Joe"));
        persons.add(new Person(4, "Moe"));
        persons.add(new Person(5, "Tim"));
        persons.add(new Person(6, "Kim"));
        persons.add(new Person(7, "Lee"));
        return persons;
    }

    public static List<Pair<Person, Person>> personEdges() {
        Map<String, Person> map = persons().stream().collect(toMap(Person::getName, p -> p));

        List<Pair<Person, Person>> edges = new ArrayList<>();

        // (Foo) - (Bar) - (Tim) -(Joe) - (Moe) - (Tim)
        edges.add(ImmutablePair.of(map.get("Foo"), map.get("Bar")));
        edges.add(ImmutablePair.of(map.get("Bar"), map.get("Tim")));
        edges.add(ImmutablePair.of(map.get("Joe"), map.get("Moe")));
        edges.add(ImmutablePair.of(map.get("Moe"), map.get("Tim")));

        // (Kim) - (Lee)
        edges.add(ImmutablePair.of(map.get("Kim"), map.get("Lee")));

        return edges;
    }

    public static Person p(long id, String name) {
        return new Person(id, name);
    }
}
