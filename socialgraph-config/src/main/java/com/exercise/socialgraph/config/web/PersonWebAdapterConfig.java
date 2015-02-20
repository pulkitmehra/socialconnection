package com.exercise.socialgraph.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.exercise.socialgraph.core.services.PersonService;
import com.exercise.socialgraph.web.adapters.PersonWebAdapter;
import com.exercise.socialgraph.web.adapters.SpringPersonWebAdapter;

@Configuration
public class PersonWebAdapterConfig {

    @Bean
    public PersonWebAdapter personWebAdapter(PersonService personService) {
        return new SpringPersonWebAdapter(personService);
    }
}
