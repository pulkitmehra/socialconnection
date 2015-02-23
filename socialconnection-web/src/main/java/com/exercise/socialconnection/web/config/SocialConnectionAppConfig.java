package com.exercise.socialconnection.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import com.exercise.socialconnection.services.config.PersonServiceConfig;

/**
 * The Spring Java configuration class SocialConnectionAppConfig.
 * It is similar to application context for this Application.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
@Configuration
@Import(value = { PersonServiceConfig.class })
@PropertySources({ @PropertySource("classpath:database.properties") })
public class SocialConnectionAppConfig {

    /**
     * Property placeholder configurer.
     *
     * @return the property sources placeholder configurer
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
