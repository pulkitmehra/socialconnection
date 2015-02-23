package com.exercise.socialconnection.uat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

/**
 * Spring Java config for forming {@link RestTemplate}
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
@Configuration
@PropertySource("classpath:rest-url.properties")
public class RestConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
