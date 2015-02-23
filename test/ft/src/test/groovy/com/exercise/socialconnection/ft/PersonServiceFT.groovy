package com.exercise.socialconnection.ft;

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification
import com.exercise.socialconnection.core.model.Person
import com.exercise.socialconnection.data.config.DataBuilder
import com.exercise.socialconnection.services.PersonService
import com.exercise.socialconnection.services.UnknownPersonException
import com.exercise.socialconnection.services.config.PersonServiceConfig



/**
 * Functional test case for PersonService
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
@Slf4j
@ContextConfiguration(classes = config.class)
@Transactional
@ActiveProfiles('Neo4j')
@TestPropertySource('classpath:database.properties')
public class PersonServiceFT extends Specification{

    @Autowired
    PersonService personService;

    @Autowired
    DataBuilder dataBuilder;

    def "Get all person in social connection"(){
        List<Person> persons

        given:
        "Persons are already added to the social connection graph"
        persons = dataBuilder.addPersonsToDB()

        when:
        "Caller request list of all the persons in the graph"
        persons = personService.getPersons()

        then:
        "Caller should get the list of persons"
        assert persons == old(persons)
    }

    def "Get list of person connecting two given person name"(String fromPersonName,String toPersonName, Integer result){
        List<Person> persons

        given:
        "Persons are already added to the social connection graph"
        persons = dataBuilder.addPersonsToDB()

        when:
        "Caller request path from #fromPersonName to #toPersonName"
        persons = personService.getPathToPerson(fromPersonName,toPersonName)

        then:
        "Caller should get the list of persons between them or empty list if none exists"
        assert persons.size == result

        where:
        fromPersonName | toPersonName | result
        'Foo'          | 'Tim'        |   3
        'Kim'          | 'Lee'        |   2
    }

    def "Get count of person connecting two given person name"(String fromPersonName,String toPersonName, Integer result){
        Integer count = null

        given:
        "Persons are already added to the social connection graph"
        dataBuilder.addPersonsToDB()

        when:
        "Caller request count from #fromPersonName to #toPersonName"
        count = personService.getPathToPersonCount(fromPersonName,toPersonName)

        then:
        "Caller should get the count of persons between two or -1 if connection does not exist"
        assert count == result

        where:
        fromPersonName | toPersonName | result
        'Foo'          | 'Tim'        |   1
        'Kim'          | 'Lee'        |   0
        'Kim'          | 'Foo'        |  -1
    }

    def "Throw UnknownPersonException if unknown person's name is passed to API"(){

        given:
        "Persons are already added to the social connection graph"
        dataBuilder.addPersonsToDB()

        when:
        "Caller request list of persons path between given unkown person's names"
        personService.getPathToPerson('Unknown1','Foo')

        then:
        "API complains using UnknownPersonException"
        thrown(UnknownPersonException)

        when:
        "Caller request count of persons between given unkown person's names"
        personService.getPathToPersonCount('Unknown1','Foo')

        then:
        "API complains using UnknownPersonException"
        thrown(UnknownPersonException)
    }



    @Configuration
    @Import(PersonServiceConfig.class)
    static class config{
        @Bean
        public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
        }
    }
}