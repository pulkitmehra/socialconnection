package com.exercise.socialconnection.data.graph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import org.apache.commons.lang3.tuple.Pair;

/**
 *  An adjacency list based Graph data structure.
 *  Note:
 *  <ol>
 *      <li> Here <T> must override equals and hash code method. 
 *      <li> Graph does not support duplicate elements
 *  </ol>
 *
 * @author pulkit.mehra
 * @param <T> the generic type
 * @Since: Feb 23, 2015
 */
@Guarded
public class Graph<T> {

    /** The adj list. */
    private Map<T, List<T>> adjList = new ConcurrentHashMap<>();

    /** The edges */
    private int E;

    /**
     * Instantiates a new graph.
     *
     * @param edges the edges
     */
    public Graph(@NotNull List<Pair<T, T>> edges) {
        for (Pair<T, T> pair : edges) {
            addEdge(pair.getLeft(), pair.getRight());
        }
    }

    /**
     * Instantiates a new graph.
     */
    public Graph() {

    }

    /**
     * Vertices of graph
     *
     * @return the int
     */
    public int V() {
        return adjList.size();
    }

    /**
     * Edges of graph
     *
     * @return the int
     */
    public int E() {
        return E;
    }

    /**
     * Checks for vertex.
     *
     * @param u the u
     * @return true, if successful
     */
    public boolean hasVertex(T u) {
        return adjList.get(u) != null;
    }

    /**
     * Adds the edge.
     *
     * @param u the u
     * @param v the v
     */
    public void addEdge(T u, T v) {
        if (adjList.get(u) == null) {
            adjList.put(u, new LinkedList<T>());
        }
        if (adjList.get(v) == null) {
            adjList.put(v, new LinkedList<T>());
        }
        adjList.get(u).add(v);
        adjList.get(v).add(u);
        E++;
    }

    /**
     * Vertices.
     *
     * @return the iterable
     */
    public Iterable<T> vertices() {
        return adjList.keySet();
    }

    /**
     * Adjacent nodes to a given vertices
     *
     * @param v the v
     * @return the iterable
     */
    public Iterable<T> adj(T v) {
        if (adjList.get(v) == null) {
            return Collections.emptyList();
        }
        return adjList.get(v);
    }

}