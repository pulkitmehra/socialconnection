package com.exercise.socialconnection.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import com.exercise.socialconnection.data.config.Neo4jDatabaseConfig;

/**
 * The Interface EmbeddedDatabase.
 * <p>
 * Its a spring based configuration annotation {@link Configuration}, which
 * binds the data source and transaction manager into environment. Database can
 * be configured into externalize 'test-database.properties' file, which should
 * present in the class path. <br>
 * <i>NOTE: This annotation will only work on Spring java configuration
 * classes</i>
 * </p>
 * 
 * @author Pulkit.Mehra
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Configuration
@PropertySource("classpath:test-database.properties")
@Import(Neo4jDatabaseConfig.class)
public @interface EmbeddedDatabase {

}
