package com.exercise.socialgraph.data.graph;

import static com.exercise.socialgraph.data.DataBuilder.p;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import java.util.List;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;
import com.exercise.socialgraph.core.model.Person;
import com.exercise.socialgraph.data.DataBuilder;

//(1,Foo) - (2,Bar) - (5,Tim) | (3,Joe) - (4,Moe) - (5,Tim)
// (6,Kim) - (7,Lee)
@SuppressWarnings("unchecked")
public class TraversalTest {

    @Test
    public void testBFSTraversalConnectedComponents() {
        List<Pair<Person, Person>> personEdges = DataBuilder.personEdges();
        Traversal<Person> bfsTraveral = GraphUtil.bfs(personEdges);

        assertEquals(bfsTraveral.getConnectedComponents().size(), 2);
        assertNotNull(bfsTraveral.getConnectedComponents().get(1));
        assertThat(
                bfsTraveral.getConnectedComponents().get(1),
                hasItems(equalTo(p(1, "Foo")), equalTo(p(2, "Bar")), equalTo(p(3, "Joe")),
                        equalTo(p(4, "Moe")), equalTo(p(5, "Tim"))));

        assertThat(bfsTraveral.getConnectedComponents().get(2),
                hasItems(equalTo(p(6, "Kim")), equalTo(p(7, "Lee"))));
    }

    @Test
    public void testIsConnected() {
        List<Pair<Person, Person>> personEdges = DataBuilder.personEdges();
        Traversal<Person> bfsTraveral = GraphUtil.bfs(personEdges);

        Assert.assertTrue(bfsTraveral.isConnected(p(1, "Foo"), p(5, "Tim")));
        Assert.assertFalse(bfsTraveral.isConnected(p(1, "Foo"), p(6, "Kim")));

        Assert.assertFalse(bfsTraveral.isConnected(p(-1, "Sue"), p(6, "Kim")));
    }

    // (1,Foo) - (2,Bar) - (5,Tim) | (3,Joe) - (4,Moe) - (5,Tim)
    // (6,Kim) - (7,Lee)
    @Test
    public void testPathTo() {
        List<Pair<Person, Person>> personEdges = DataBuilder.personEdges();
        Traversal<Person> bfsTraveral = GraphUtil.bfs(personEdges);

        Iterable<Person> path = bfsTraveral.pathTo(p(1, "Foo"), p(5, "Tim"));
        assertThat(path, hasItems(equalTo(p(1, "Foo")), equalTo(p(2, "Bar")), equalTo(p(5, "Tim"))));

        path = bfsTraveral.pathTo(p(1, "Foo"), p(2, "Bar"));
        Assert.assertEquals(StreamSupport.stream(path.spliterator(), false).count(), 2);
    }

    @Test
    public void testPathToNotAvailable() {
        List<Pair<Person, Person>> personEdges = DataBuilder.personEdges();
        Traversal<Person> bfsTraveral = GraphUtil.bfs(personEdges);

        Iterable<Person> path = bfsTraveral.pathTo(p(1, "Foo"), p(6, "Kim"));
        Assert.assertNull(path);

    }

}
