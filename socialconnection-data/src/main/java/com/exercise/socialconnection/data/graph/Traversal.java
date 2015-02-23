package com.exercise.socialconnection.data.graph;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Traversing {@link Graph} to find nodes and paths.
 *
 * @author pulkit.mehra
 * @param <T> the generic type
 * @see BFSTraversal
 * @Since: Feb 23, 2015
 */
public interface Traversal<T> {

    /**
     * Gets the connected components in {@link Graph}
     *
     * @return the connected components
     */
    Map<Integer, List<T>> getConnectedComponents();

    /**
     * Path between two nodes in a {@link Graph}
     *
     * @param u the u
     * @param v the v
     * @return the iterable
     */
    Iterable<T> pathTo(T u, T v);

    /**
     * Checks if two nodes in {@link Graph} are connected.
     *
     * @param u the u
     * @param v the v
     * @return true, if is connected
     */
    boolean isConnected(T u, T v);

    /**
     * Find a {@link Graph} node matching given {@link Predicate}
     *
     * @param predicate the predicate
     * @return the optional
     */
    Optional<T> find(Predicate<T> predicate);
}
