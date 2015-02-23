package com.exercise.socialconnection.data.graph;

import static com.exercise.socialconnection.data.config.DataBuilder.p;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import com.exercise.socialconnection.core.model.Person;
import com.exercise.socialconnection.data.config.DataBuilder;

/**
 * Test for {@link Graph} 
 * 
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
public class GraphTest {

    // (1,Foo) - (2,Bar) - (5,Tim) - (3,Joe) - (4,Moe) - (5,Tim)
    // (6,Kim) - (7,Lee)
    /**
     *  Test graph formation
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGraphFormation() {

        List<Pair<Person, Person>> personEdges = DataBuilder.personEdges();
        Graph<Person> graph = GraphUtil.graph(personEdges);

        assertEquals(graph.E(), personEdges.size());
        assertEquals(graph.V(), toPerson(personEdges).size());

        assertFalse(graph.hasVertex(p(-1, "Unknown")));
        assertThat(graph.adj(p(5, "Tim")), hasItems(equalTo(p(2, "Bar")), equalTo(p(4, "Moe"))));

        assertEquals(StreamSupport.stream(graph.vertices().spliterator(), false).count(),
                toPerson(personEdges).size());
    }

    private static Set<Person> toPerson(List<Pair<Person, Person>> edges) {
        Set<Person> set = new HashSet<>();
        edges.stream().forEach((e) -> {
            set.add(e.getLeft());
            set.add(e.getRight());
        });
        return set;
    }
}
