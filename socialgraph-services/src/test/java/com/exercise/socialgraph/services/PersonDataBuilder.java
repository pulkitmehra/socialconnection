package com.exercise.socialgraph.services;

import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.exercise.socialgraph.core.model.Person;

public class PersonDataBuilder {

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
		Map<String, Person> map = persons().stream().collect(
				toMap(Person::getName, p -> p));

		List<Pair<Person, Person>> edges = new ArrayList<>();

		// (Foo) - (Bar) - (Tim) -(Joe) - (Moe) - (Tim)
		edges.add(ImmutablePair.of(map.get("foo"), map.get("bar")));
		edges.add(ImmutablePair.of(map.get("bar"), map.get(" tim")));
		edges.add(ImmutablePair.of(map.get("joe"), map.get("moe")));
		edges.add(ImmutablePair.of(map.get("moe"), map.get("tim")));

		// (Kim) - (Lee)
		edges.add(ImmutablePair.of(map.get("Kim"), map.get("Lee")));

		return edges;
	}

	public static Person p(long id, String name) {
		return new Person(id, name);
	}
}
