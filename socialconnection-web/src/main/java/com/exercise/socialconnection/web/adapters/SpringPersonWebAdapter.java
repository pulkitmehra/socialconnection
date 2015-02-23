package com.exercise.socialconnection.web.adapters;

import static java.lang.Integer.valueOf;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.exercise.socialconnection.services.PersonService;
import com.exercise.socialconnection.services.PersonServiceException;
import com.exercise.socialconnection.services.UnknownPersonException;
import com.exercise.socialconnection.web.model.InfoResponse;
import com.exercise.socialconnection.web.model.PersonList;

/**
 * Implementation class of {@link PersonWebAdapter}.
 * It is Spring based MVC controller
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
@Guarded
@Controller
@RequestMapping(value = "/person")
public class SpringPersonWebAdapter implements PersonWebAdapter {

    /** The person service. */
    private PersonService personService;

    /**
     * Instantiates a new spring person web adapter.
     *
     * @param personService the person service
     */
    @Autowired
    public SpringPersonWebAdapter(@NotNull PersonService personService) {
        this.personService = personService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    public @ResponseBody PersonList getPersons() throws PersonServiceException {
        PersonList personList = new PersonList();
        personList.getPersons().addAll(personService.getPersons());
        return personList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(method = RequestMethod.GET, params = { "fromName", "toName" }, produces = {
            "application/json", "application/xml" })
    public @ResponseBody PersonList getPathToPerson(@RequestParam("fromName") String fromName,
            @RequestParam("toName") String toName) throws PersonServiceException,
            UnknownPersonException {
        PersonList personList = new PersonList();
        personList.getPersons().addAll(personService.getPathToPerson(fromName, toName));
        return personList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "/count", method = RequestMethod.GET, params = { "fromName", "toName" }, produces = {
            "application/json", "application/xml" })
    public @ResponseBody InfoResponse getPathToPersonCount(
            @RequestParam("fromName") String fromName, @RequestParam("toName") String toName)
            throws PersonServiceException, UnknownPersonException {
        return new InfoResponse(valueOf(personService.getPathToPersonCount(fromName, toName)));
    }

}
