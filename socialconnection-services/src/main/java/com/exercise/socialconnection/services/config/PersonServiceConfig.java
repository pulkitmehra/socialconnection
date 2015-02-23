package com.exercise.socialconnection.services.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.exercise.socialconnection.core.repository.PersonRepository;
import com.exercise.socialconnection.data.config.PersonRepositoryConfig;
import com.exercise.socialconnection.services.PersonService;
import com.exercise.socialconnection.services.PersonServiceImpl;

/**
 * Spring Java configuration for {@link PersonService}.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
@Configuration
@Import(PersonRepositoryConfig.class)
public class PersonServiceConfig {

    /**
     * Person service.
     *
     * @param personRepository the person repository
     * @return the person service
     */
    @Bean
    public PersonService personService(PersonRepository personRepository) {
        return new PersonServiceImpl(personRepository);
    }

}
