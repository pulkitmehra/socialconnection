package com.exercise.socialgraph.config.web.webapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import com.exercise.socialgraph.config.services.PersonServiceConfig;

@Configuration
@Import(value = { PersonServiceConfig.class })
@PropertySources({ @PropertySource("classpath:database.properties") })
public class SocialGraphAppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
