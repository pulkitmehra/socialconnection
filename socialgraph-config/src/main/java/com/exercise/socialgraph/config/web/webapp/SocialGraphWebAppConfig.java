package com.exercise.socialgraph.config.web.webapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import com.exercise.socialgraph.config.web.PersonWebAdapterConfig;
import com.exercise.socialgraph.web.ExceptionControllerAdvice;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.exercise.socialgraph.web.adapters" })
@Import(value = { PersonWebAdapterConfig.class })
public class SocialGraphWebAppConfig extends WebMvcConfigurationSupport {

    @Bean
    public ExceptionControllerAdvice clientDiscoveryControllerAdvice() {
        return new ExceptionControllerAdvice();
    }
}
