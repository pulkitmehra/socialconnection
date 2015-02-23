package com.exercise.socialconnection.data.graph;

import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

/**
 *  Class contains utility functions to encapsulate Graph operations.
 *  
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
public class GraphUtil {

    /**
     * Instantiates a {@link Graph}.
     *
     * @param <T> the generic type
     * @param pairs the pairs of edges
     * @return the graph
     */
    public static <T> Graph<T> graph(List<Pair<T, T>> pairs) {
        return new Graph<T>(pairs);
    }

    /**
     * Instantiates an empty {@link Graph}
     *
     * @param <T> the generic type
     * @return the graph
     */
    public static <T> Graph<T> graph() {
        return new Graph<T>();
    }

    /**
     * Instantiates Breadth First Search based traversal class.
     * It is used for searching in graph.
     *
     * @param <T> the generic type
     * @param graph the {@link Graph}
     * @return the traversal
     */
    public static <T> Traversal<T> bfs(Graph<T> graph) {
        return new BFSTraversal<T>(graph);
    }

    /**
     * Instantiates Breadth First Search traversal, based on {@link Graph}
     * generated by given {@link List} of {@link Pair} of edges.
     *
     * @param <T> the generic type
     * @param pairs the pairs
     * @return the traversal
     */
    public static <T> Traversal<T> bfs(List<Pair<T, T>> pairs) {
        return bfs(graph(pairs));
    }

}
