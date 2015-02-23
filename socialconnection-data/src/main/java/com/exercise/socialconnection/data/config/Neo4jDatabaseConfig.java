package com.exercise.socialconnection.data.config;

import static com.exercise.socialconnection.data.config.ConfigUtil.getMissingPropertyMessage;
import static org.springframework.util.Assert.hasText;
import javax.annotation.PostConstruct;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Java Configuration for binding Neo4j to context.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories
@Profile("Neo4j")
public class Neo4jDatabaseConfig {

    /** The Constant NEO4J_DB_PATH. */
    private static final String NEO4J_DB_PATH = "neo.db.path";

    /** The DB path. */
    @Value("${" + NEO4J_DB_PATH + "}")
    private String DBPath;

    /**
     * After initialization.
     */
    @PostConstruct
    public void afterInitialization() {
        hasText(DBPath, getMissingPropertyMessage(NEO4J_DB_PATH));
    }

    /**
     * Graph database service.
     *
     * @return the graph database service
     */
    @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService() {
        GraphDatabaseFactory graphDatabaseFactory = new GraphDatabaseFactory();
        return graphDatabaseFactory.newEmbeddedDatabase(DBPath);
    }

    /**
     * The Class Neo4jConfig.
     */
    @Configuration
    static class Neo4jConfig extends Neo4jConfiguration {

    }

}
