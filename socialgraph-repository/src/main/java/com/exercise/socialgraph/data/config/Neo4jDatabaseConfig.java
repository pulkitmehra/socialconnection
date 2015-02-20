package com.exercise.socialgraph.data.config;

import static com.exercise.socialgraph.data.config.ConfigUtil.getMissingPropertyMessage;
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

@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories
@Profile("Neo4j")
public class Neo4jDatabaseConfig {

    private static final String NEO4J_DB_PATH = "neo.db.path";

    @Value("${" + NEO4J_DB_PATH + "}")
    private String DBPath;

    @PostConstruct
    public void afterInitialization() {
        hasText(DBPath, getMissingPropertyMessage(NEO4J_DB_PATH));
    }

    @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService() {
        GraphDatabaseFactory graphDatabaseFactory = new GraphDatabaseFactory();
        return graphDatabaseFactory.newEmbeddedDatabase(DBPath);
    }

    @Configuration
    static class Neo4jConfig extends Neo4jConfiguration {

    }

}
