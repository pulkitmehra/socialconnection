package com.exercise.socialconnection.data.config;

import static com.exercise.socialconnection.data.config.ConfigUtil.getMissingPropertyMessage;
import static org.springframework.util.Assert.hasText;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import com.exercise.socialconnection.core.model.Person;
import com.exercise.socialconnection.core.repository.PersonRepository;
import com.exercise.socialconnection.data.graph.Graph;
import com.exercise.socialconnection.data.graph.GraphUtil;
import com.exercise.socialconnection.repository.GraphPersonRepository;
import com.exercise.socialconnection.repository.Neo4jPersonRepository;

/**
 * Spring Java Configuration for binding {@link PersonRepository} implementation to context.
 * It binds repository implementation based on Spring active profile
 * <ol>
 *  <li>Neo4j
 *  <li>Graph
 * </ol> 
 * 
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
@Configuration
@Import(Neo4jDatabaseConfig.class)
@PropertySource("classpath:socialconnection-person_cyphers.xml")
public class PersonRepositoryConfig {

    /** The Constant PERSON_CYPHER_PATH_TO. */
    private static final String PERSON_CYPHER_PATH_TO = "Person.cypher.pathTo";

    /** The Constant PERSON_CYPHER_FIND_BY_NAME. */
    private static final String PERSON_CYPHER_FIND_BY_NAME = "Person.cypher.findByName";

    /** The Constant PERSON_CYPHER_FIND_ALL. */
    private static final String PERSON_CYPHER_FIND_ALL = "Person.cypher.findAll";

    /** The find all person cypher. */
    @Value("${" + PERSON_CYPHER_FIND_ALL + "}")
    private String findAllPersonCypher;

    /** The find by name person cypher. */
    @Value("${" + PERSON_CYPHER_FIND_BY_NAME + "}")
    private String findByNamePersonCypher;

    /** The find path to person cypher. */
    @Value("${" + PERSON_CYPHER_PATH_TO + "}")
    private String findPathToPersonCypher;

    /**
     * After initialization.
     */
    @PostConstruct
    public void afterInitialization() {
        hasText(findAllPersonCypher, getMissingPropertyMessage(PERSON_CYPHER_FIND_ALL));
        hasText(findByNamePersonCypher, getMissingPropertyMessage(PERSON_CYPHER_FIND_BY_NAME));
        hasText(findPathToPersonCypher, getMissingPropertyMessage(PERSON_CYPHER_PATH_TO));
    }

    /**
     * Neo4jperson repository.
     *
     * @param neo4jTemplate the neo4j template
     * @return the person repository
     */
    @Bean
    @Profile("Neo4j")
    public PersonRepository neo4jpersonRepository(Neo4jTemplate neo4jTemplate) {
        Neo4jPersonRepository springPersonRepository = new Neo4jPersonRepository(neo4jTemplate);
        springPersonRepository.setFindAllCypher(findAllPersonCypher);
        springPersonRepository.setFindByNameCypher(findByNamePersonCypher);
        springPersonRepository.setPathToCypher(findPathToPersonCypher);
        return springPersonRepository;
    }

    /**
     * Graph person repository.
     *
     * @return the person repository
     */
    @Bean
    @Profile("Graph")
    public PersonRepository graphPersonRepository() {
        return new GraphPersonRepository(GraphUtil.graph());
    }

    /**
     * Graph data structure
     *
     * @return the graph
     */
    @Bean
    @Profile("Graph")
    public Graph<Person> graph() {
        return GraphUtil.graph(DataBuilder.personEdges());
    }

    /**
     * Data builder.
     * It is used for generating demo data. 
     * It is not required in production.
     *
     * @param neo4jTemplate the neo4j template
     * @return the data builder
     */
    @Bean
    @Profile(value = { "Neo4j" })
    public DataBuilder dataBuilder(Neo4jTemplate neo4jTemplate) {
        return new DataBuilder(neo4jTemplate);
    }

}
