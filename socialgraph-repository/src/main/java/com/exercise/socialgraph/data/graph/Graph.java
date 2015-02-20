package com.exercise.socialgraph.data.graph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import org.apache.commons.lang3.tuple.Pair;

@Guarded
public class Graph<T> {

    private Map<T, List<T>> adjList = new ConcurrentHashMap<>();
    private int E;

    public Graph(@NotNull List<Pair<T, T>> edges) {
        for (Pair<T, T> pair : edges) {
            addEdge(pair.getLeft(), pair.getRight());
        }
    }

    public Graph() {

    }

    public int V() {
        return adjList.size();
    }

    public int E() {
        return E;
    }

    public boolean hasVertex(T u) {
        return adjList.get(u) != null;
    }

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

    public Iterable<T> vertices() {
        return adjList.keySet();
    }

    public Iterable<T> adj(T v) {
        if (adjList.get(v) == null) {
            return Collections.emptyList();
        }
        return adjList.get(v);
    }

}