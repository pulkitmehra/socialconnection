package com.exercise.socialconnection.webapp;

import static org.apache.commons.lang3.StringUtils.isBlank;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import com.exercise.socialconnection.web.config.SocialConnectionAppConfig;
import com.exercise.socialconnection.web.config.SocialConnectionWebAppConfig;

/**
 * <p>
 * Spring based Web application descriptor. It is equivalent to web.xml.
 * It fuses {@link SocialConnectionAppConfig} and {@link SocialConnectionWebAppConfig}
 * into a single context and start {@link DispatcherServlet}.
 * </p>
 * <ol>
 *  <li>{@link DispatcherServlet} will set spring context as <b><CONTEXT-ROOT>/api/social/1.0/</b>
 *  <li> It set <b>'Neo4j'</b> as default spring Active profile if none is passed in System properties with key <b>social.connection.profile</b>
 *  
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
public class WebAppConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { SocialConnectionAppConfig.class };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { SocialConnectionWebAppConfig.class };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/api/social/1.0/*" };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        //use -Dsocial.connection.profile=Neo4j or Graph
        String activeProfile = System.getProperty("social.connection.profile");
        if (activeProfile == null || isBlank(activeProfile)) {
            activeProfile = "Neo4j";
        }
        servletContext.setInitParameter("spring.profiles.active", activeProfile);
    }

}
