package com.exercise.socialgraph.data.graph;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public interface Traversal<T> {

    Map<Integer, List<T>> getConnectedComponents();

    Iterable<T> pathTo(T u, T v);

    boolean isConnected(T u, T v);

    Optional<T> find(Predicate<T> predicate);
}
