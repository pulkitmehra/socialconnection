package com.exercise.socialgraph.data.graph;

import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class GraphUtil {

    public static <T> Graph<T> graph(List<Pair<T, T>> pairs) {
        return new Graph<T>(pairs);
    }

    public static <T> Graph<T> graph() {
        return new Graph<T>();
    }

    public static <T> Traversal<T> bfs(Graph<T> graph) {
        return new BFSTraversal<T>(graph);
    }

    public static <T> Traversal<T> bfs(List<Pair<T, T>> pairs) {
        return bfs(graph(pairs));
    }

}
