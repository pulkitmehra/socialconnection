package com.exercise.socialconnection.data.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Predicate;
import net.sf.oval.constraint.NotNull;

/**
 *  Traversing {@link Graph} using Breadth First Search algorithm.
 *
 * @author pulkit.mehra
 * @param <T> the generic type
 * @Since: Feb 23, 2015
 */
public class BFSTraversal<T> implements Traversal<T> {

    /** The graph. */
    protected Graph<T> graph;

    /** The always false. */
    private Predicate<T> alwaysFalse = (t) -> false;

    /**
     * Instantiates a new BFS traversal.
     *
     * @param graph the graph
     */
    public BFSTraversal(@NotNull Graph<T> graph) {
        this.graph = graph;
    }

    /**
     * Traverse.
     *
     * @param graph the graph
     * @param predicate the predicate
     * @return the map
     */
    private Map<T, Holder<T>> traverse(Graph<T> graph, Predicate<T> predicate) {
        Map<T, Holder<T>> map = new HashMap<T, Holder<T>>();
        Queue<T> queue = new LinkedList<T>();
        int connCompCount = 1;
        for (T v : graph.vertices()) {
            connCompCount = traverse(v, connCompCount, map, queue, predicate).connComponent;
        }
        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> find(Predicate<T> predicate) {
        Map<T, Holder<T>> map = new HashMap<T, Holder<T>>();
        Queue<T> queue = new LinkedList<T>();
        int connCompCount = 1;
        for (T v : graph.vertices()) {
            RS<T> rs = traverse(v, connCompCount, map, queue, predicate);
            if (rs.t != null) {
                return Optional.of(rs.t);
            }
        }
        return Optional.empty();
    }

    /**
     * Traverse graph.
     *
     * @param vertex the vertex
     * @param connCompCount the conn comp count
     * @param map the map
     * @param queue the queue
     * @param predicate the predicate
     * @return the rs result set
     */
    private RS<T> traverse(T vertex, int connCompCount, Map<T, Holder<T>> map, Queue<T> queue,
            Predicate<T> predicate) {

        if (map.get(vertex) == null || !map.get(vertex).isMarked) {

            addMetaDataToHolder(vertex, null, connCompCount, map);
            queue.offer(vertex);

            while (!queue.isEmpty()) {
                T pVertex = queue.poll();
                if (predicate.test(pVertex)) {
                    return new RS<T>(pVertex, connCompCount);
                }
                for (T cVertex : graph.adj(pVertex)) {
                    if (map.get(cVertex) == null || !map.get(cVertex).isMarked) {
                        addMetaDataToHolder(cVertex, pVertex, connCompCount, map);
                        queue.offer(cVertex);
                    }
                }
            }
            connCompCount++;
        }
        return new RS<T>(null, connCompCount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, List<T>> getConnectedComponents() {
        Map<Integer, List<T>> ccMap = new HashMap<Integer, List<T>>();

        for (Entry<T, Holder<T>> entry : traverse(graph, alwaysFalse).entrySet()) {
            Holder<T> holder = entry.getValue();
            List<T> list = ccMap.get(holder.connectedComponent);

            if (list == null) {
                list = new LinkedList<T>();
            }
            list.add(entry.getKey());
            ccMap.put(holder.connectedComponent, list);
        }
        return ccMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConnected(@NotNull T u, @NotNull T v) {
        Map<T, Holder<T>> holderMap = traverse(graph, alwaysFalse);
        if (holderMap.get(u) == null || holderMap.get(v) == null) {
            return false;
        }
        return holderMap.get(u).connectedComponent == holderMap.get(v).connectedComponent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<T> pathTo(@NotNull T u, @NotNull T v) {
        if (!isConnected(u, v)) {
            return null;
        }

        Map<T, Holder<T>> innerBFSMap = traversal(u);
        LinkedList<T> stack = new LinkedList<T>();
        while (v != null && !v.equals(u)) {
            stack.push(v);
            v = innerBFSMap.get(v).pVertex;
        }
        stack.push(v);
        return stack;
    }

    /**
     * Traversal.
     *
     * @param u the u
     * @return the map
     */
    private Map<T, Holder<T>> traversal(T u) {
        Map<T, Holder<T>> innerBFSMap = new HashMap<T, Holder<T>>();
        Queue<T> innerBFSQueue = new LinkedList<T>();
        traverse(u, -1, innerBFSMap, innerBFSQueue, alwaysFalse);
        return innerBFSMap;
    }

    /**
     * Adds the meta data to holder.
     *
     * @param vertex the vertex
     * @param pVertex the vertex
     * @param connCompCount the conn comp count
     * @param map the map
     */
    private void addMetaDataToHolder(T vertex, T pVertex, int connCompCount, Map<T, Holder<T>> map) {
        Holder<T> holder = new Holder<T>();
        holder.pVertex = pVertex;
        holder.isMarked = true;
        holder.connectedComponent = connCompCount;
        map.put(vertex, holder);
    }

    /**
     * The Class Holder.
     *
     * @param <T> the generic type
     */
    private static class Holder<T> {

        /** The is marked. */
        boolean isMarked;

        /** The p vertex. */
        T pVertex;

        /** The connected component. */
        int connectedComponent;

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "[" + connectedComponent + ",p(" + pVertex + ")";
        }
    }

    /**
     * The Class Result set.
     *
     * @param <T> the generic type
     */
    private static class RS<T> {

        /** The t. */
        T t;

        /** The conn component. */
        int connComponent;

        /**
         * Instantiates a new rs.
         *
         * @param t the t
         * @param component the component
         */
        public RS(T t, int component) {
            this.t = t;
            this.connComponent = component;
        }

    }

}
