package com.exercise.socialgraph.config.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.exercise.socialgraph.core.repository.PersonRepository;
import com.exercise.socialgraph.core.services.PersonService;
import com.exercise.socialgraph.data.config.PersonRepositoryConfig;
import com.exercise.socialgraph.services.PersonServiceImpl;

@Configuration
@Import(PersonRepositoryConfig.class)
public class PersonServiceConfig {

	@Bean
	public PersonService personService(PersonRepository personRepository) {
		return new PersonServiceImpl(personRepository);
	}

}
