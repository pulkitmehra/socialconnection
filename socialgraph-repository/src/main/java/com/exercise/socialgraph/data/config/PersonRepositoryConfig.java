package com.exercise.socialgraph.data.config;

import static com.exercise.socialgraph.data.config.ConfigUtil.getMissingPropertyMessage;
import static org.springframework.util.Assert.hasText;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import com.exercise.socialgraph.core.repository.PersonRepository;
import com.exercise.socialgraph.data.graph.GraphUtil;
import com.exercise.socialgraph.data.repository.GraphPersonRepository;
import com.exercise.socialgraph.data.repository.Neo4jPersonRepository;

@Configuration
@Import(Neo4jDatabaseConfig.class)
@PropertySource("classpath:socialgraph-person_cyphers.xml")
public class PersonRepositoryConfig {

    private static final String PERSON_CYPHER_PATH_TO = "Person.cypher.pathTo";
    private static final String PERSON_CYPHER_FIND_BY_NAME = "Person.cypher.findByName";
    private static final String PERSON_CYPHER_FIND_ALL = "Person.cypher.findAll";

    @Value("${" + PERSON_CYPHER_FIND_ALL + "}")
    private String findAllPersonCypher;

    @Value("${" + PERSON_CYPHER_FIND_BY_NAME + "}")
    private String findByNamePersonCypher;

    @Value("${" + PERSON_CYPHER_PATH_TO + "}")
    private String findPathToPersonCypher;

    @PostConstruct
    public void afterInitialization() {
        hasText(findAllPersonCypher, getMissingPropertyMessage(PERSON_CYPHER_FIND_ALL));
        hasText(findByNamePersonCypher, getMissingPropertyMessage(PERSON_CYPHER_FIND_BY_NAME));
        hasText(findPathToPersonCypher, getMissingPropertyMessage(PERSON_CYPHER_PATH_TO));
    }

    @Bean
    @Profile("Neo4j")
    public PersonRepository neo4jpersonRepository(Neo4jTemplate neo4jTemplate) {
        Neo4jPersonRepository springPersonRepository = new Neo4jPersonRepository(neo4jTemplate);
        springPersonRepository.setFindAllCypher(findAllPersonCypher);
        springPersonRepository.setFindByNameCypher(findByNamePersonCypher);
        springPersonRepository.setPathToCypher(findPathToPersonCypher);
        return springPersonRepository;
    }

    @Bean
    @Profile("Graph")
    public PersonRepository graphPersonRepository() {
        return new GraphPersonRepository(GraphUtil.graph());
    }

}
