package com.exercise.socialconnection.web;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.exercise.socialconnection.data.config.DataBuilder;
import com.exercise.socialconnection.services.PersonService;
import com.exercise.socialconnection.services.UnknownPersonException;
import com.exercise.socialconnection.web.config.SocialConnectionWebAppConfig;

/**
 * Test case for {@link ExceptionControllerAdvice}
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = ExceptionControllerAdviceTest.Config.class)
@ActiveProfiles("Neo4j")
public class ExceptionControllerAdviceTest {

    private static final String PARAM_FROM_NAME = "fromName";
    private static final String PARAM_TO_NAME = "toName";
    private static final String ERROR_MESSAGE = "Unknown person error message";
    private static final String UNKNOWN = "Unknown";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private PersonService personService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        assertNotNull(personService);
        Mockito.reset(personService);
    }

    @Test
    public void testUnknownPersonErrorMessage() throws Exception, UnknownPersonException {
        when(personService.getPathToPerson(UNKNOWN, UNKNOWN)).thenThrow(
                new UnknownPersonException(ERROR_MESSAGE));

        this.mockMvc
                .perform(
                        get("/person").param(PARAM_FROM_NAME, UNKNOWN)
                                .param(PARAM_TO_NAME, UNKNOWN)
                                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value(equalTo(ERROR_MESSAGE)))
                .andExpect(jsonPath("$.statusCode").value(equalTo(404)))
                .andExpect(jsonPath("$.requestedURI").exists());

        verify(personService).getPathToPerson(UNKNOWN, UNKNOWN);
    }

    @Configuration
    @Import(SocialConnectionWebAppConfig.class)
    static class Config {

        @Bean
        public PersonService personService() {
            return Mockito.mock(PersonService.class);
        }

        @Bean
        public DataBuilder dataBuilder() {
            return Mockito.mock(DataBuilder.class);
        }

    }

}
