package com.exercise.socialgraph.webapp;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import com.exercise.socialgraph.config.web.webapp.SocialGraphAppConfig;
import com.exercise.socialgraph.config.web.webapp.SocialGraphWebAppConfig;

public class WebAppConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { SocialGraphAppConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { SocialGraphWebAppConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/api/social/1.0/*" };
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        //use -Dsocial.profile=Neo4j or Graph
        String activeProfile = System.getProperty("social.profile");
        if (activeProfile == null) {
            activeProfile = "Neo4j";
        }
        servletContext.setInitParameter("spring.profiles.active", activeProfile);
    }

}
