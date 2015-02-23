package com.exercise.socialconnection.core.model;

/**
 * Model class representing a person.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
public class Person {

    public static final String PROP_NAME = "name";
    public static final Type TYPE = Type.PERSON;

    /** The id. */
    private long id;

    /** The name. */
    private String name;

    /**
     * Instantiates a new person.
     */
    public Person() {
    }

    /**
     * Instantiates a new person.
     *
     * @param id the id
     * @param name the name
     */
    public Person(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Instantiates a new person.
     *
     * @param name the name
     */
    public Person(String name) {
        this.name = name;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public Type getType() {
        return TYPE;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "(" + id + "," + name + ")";
    }
}
