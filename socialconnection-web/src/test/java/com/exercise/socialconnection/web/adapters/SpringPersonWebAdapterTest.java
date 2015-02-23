package com.exercise.socialconnection.web.adapters;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.exercise.socialconnection.data.config.DataBuilder;
import com.exercise.socialconnection.services.PersonService;
import com.exercise.socialconnection.web.model.InfoResponse;
import com.exercise.socialconnection.web.model.PersonList;

/**
 * Test case for {@link SpringPersonWebAdapter}
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
public class SpringPersonWebAdapterTest {

    private static final String FOO = "Foo";
    private static final String JOE = "Joe";

    @Mock
    private PersonService personService;
    private PersonWebAdapter personWebAdapter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        personWebAdapter = new SpringPersonWebAdapter(personService);
    }

    @Test
    public void testGetPersons() throws Exception {
        when(personService.getPersons()).thenReturn(DataBuilder.persons());
        PersonList persons = personWebAdapter.getPersons();
        assertNotNull(persons.getPersons());
        assertEquals(7, persons.getPersons().size());

        verify(personService).getPersons();
    }

    @Test
    public void testGetPathToPerson() throws Exception {
        when(personService.getPathToPerson(FOO, JOE)).thenReturn(
                DataBuilder.persons().stream().limit(3).collect(toList()));
        PersonList persons = personWebAdapter.getPathToPerson(FOO, JOE);
        assertNotNull(persons.getPersons());
        assertEquals(3, persons.getPersons().size());

        verify(personService).getPathToPerson(FOO, JOE);
    }

    @Test
    public void testGetPathToPersonCount() throws Exception {
        when(personService.getPathToPersonCount(FOO, JOE)).thenReturn(3);
        InfoResponse count = personWebAdapter.getPathToPersonCount(FOO, JOE);
        assertNotNull(count);
        assertEquals(3, count.getValue().intValue());

        verify(personService).getPathToPersonCount(FOO, JOE);
    }

}
