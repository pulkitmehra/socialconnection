package com.exercise.socialgraph.web.adapters;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.exercise.socialgraph.core.services.PersonService;
import com.exercise.socialgraph.core.services.PersonServiceException;
import com.exercise.socialgraph.core.services.UnknownPersonException;
import com.exercise.socialgraph.web.model.PersonList;

@Guarded
@Controller
@RequestMapping(value = "/person")
public class SpringPersonWebAdapter implements PersonWebAdapter {

    private PersonService personService;

    public SpringPersonWebAdapter(@NotNull PersonService personService) {
        this.personService = personService;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = { "application/json", "application/xml" })
    public @ResponseBody PersonList getPersons() throws PersonServiceException {
        PersonList personList = new PersonList();
        personList.getPersons().addAll(personService.getPersons());
        return personList;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = { "fromName", "toName" }, produces = {
            "application/json", "application/xml" })
    public @ResponseBody PersonList getPathToPerson(@RequestParam("fromName") String fromName,
            @RequestParam("fromName") String toName) throws PersonServiceException,
            UnknownPersonException {
        PersonList personList = new PersonList();
        personList.getPersons().addAll(personService.getPathToPerson(fromName, toName));
        return personList;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = { "fromName", "toName" }, produces = {
            "application/json", "application/xml" })
    public int countPathToPerson(@RequestParam("fromName") String fromName,
            @RequestParam("fromName") String toName) throws PersonServiceException,
            UnknownPersonException {
        return personService.countPathToPerson(fromName, toName);
    }

}
