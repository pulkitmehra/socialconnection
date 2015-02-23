package com.exercise.socialconnection.web.config;

import java.util.Arrays;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import com.exercise.socialconnection.data.config.DataBuilder;

/**
 * The Class PostContextDataInitializer.
 * if spring active profile is 'Neo4j' then, It add a small data set into Neo4j database.
 * See {@link DataBuilder} class for understanding data set.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
public class PostContextDataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    /** The data builder. */
    private DataBuilder dataBuilder;

    /**
     * Instantiates a new post context data initializer.
     *
     * @param dataBuilder the data builder
     */
    public PostContextDataInitializer(DataBuilder dataBuilder) {
        this.dataBuilder = dataBuilder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String[] activeProfiles = event.getApplicationContext().getEnvironment()
                .getActiveProfiles();
        if (Arrays.stream(activeProfiles).anyMatch(a -> a.equals("Neo4j"))) {
            dataBuilder.cleanDB();
            dataBuilder.addPersonsToDB();
        }
    }
}
