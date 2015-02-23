package com.exercise.socialconnection.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import com.exercise.socialconnection.data.config.DataBuilder;
import com.exercise.socialconnection.web.ExceptionControllerAdvice;

/**
 *  The Spring Java configuration class SocialConnectionWebAppConfig.
 *  It is similar to Web Application Context for this application.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
@Configuration
@EnableWebMvc
@ComponentScan({ "com.exercise.socialconnection.web.adapters" })
public class SocialConnectionWebAppConfig extends WebMvcConfigurationSupport {

    /**
     * Exception controller advice.
     *
     * @return the exception controller advice
     */
    @Bean
    public ExceptionControllerAdvice exceptionControllerAdvice() {
        return new ExceptionControllerAdvice();
    }

    /**
     * Post context data initializer.
     *
     * @param dataBuilder the data builder
     * @return the post context data initializer
     */
    @Bean
    @Profile(value = { "Neo4j" })
    public PostContextDataInitializer postContextDataInitializer(DataBuilder dataBuilder) {
        return new PostContextDataInitializer(dataBuilder);
    }
}
