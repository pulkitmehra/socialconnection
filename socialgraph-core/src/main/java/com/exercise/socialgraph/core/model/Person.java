package com.exercise.socialgraph.core.model;

import java.util.Set;

public class Person {

	public static final String PROP_NAME = "name";
	public static final Type TYPE = Type.PERSON;

	private long id;
	private String name;
	private Set<Person> connectedPersons;

	public Person() {
	}

	public Person(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Person(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return TYPE;
	}

	public Set<Person> getConnectedPersons() {
		return connectedPersons;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + id + "," + name + ")";
	}
}
