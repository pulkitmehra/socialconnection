package com.exercise.socialconnection.uat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

/**
 * Utility class encapsulate spring context
 * 
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
public class SpringContext {

    private static final String BASEURL_KEY = "rest.baseurl";
    private static ApplicationContext applicationContext;
    public static String BASE_URL_FORMAT = "http://%s/socialconnection";
    public static SpringContext springContext;

    private SpringContext(ApplicationContext appContext) {
        applicationContext = appContext;
    }

    public static SpringContext getInstance(ApplicationContext appContext) {
        springContext = new SpringContext(appContext);
        return springContext;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String bean) {
        Object beanObj = applicationContext.getBean(bean);
        Assert.notNull(beanObj);
        return (T) beanObj;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) {
        Object beanObj = applicationContext.getBean(type);
        Assert.notNull(beanObj);
        return (T) beanObj;
    }

    public Object getProperty(String key) {
        return applicationContext.getEnvironment().getProperty(key);
    }

    public String getBaseURL() {
        String baseURL = System.getProperty(BASEURL_KEY);
        if (StringUtils.isBlank(baseURL)) {
            return (String) String.format(BASE_URL_FORMAT, getProperty(BASEURL_KEY));
        }
        return String.format(BASE_URL_FORMAT, baseURL);
    }
}
