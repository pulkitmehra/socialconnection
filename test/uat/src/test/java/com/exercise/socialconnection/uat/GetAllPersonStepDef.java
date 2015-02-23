package com.exercise.socialconnection.uat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.exercise.socialconnection.web.model.PersonList;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Step definition for Getting all person for REST service.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
public class GetAllPersonStepDef {

    protected RestTemplate restTemplate;
    protected String baseURL;
    protected ResponseEntity<PersonList> response;

    @Before
    public void setUp() {
        SpringContext springContext = SpringContext
                .getInstance(new AnnotationConfigApplicationContext(RestConfig.class));
        restTemplate = springContext.getBean(RestTemplate.class);
        baseURL = springContext.getBaseURL();

    }

    @Given("^I have a REST Client and base url$")
    public void shouldhaveRestClientAndBaseUrl() throws Throwable {
        assertNotNull("Failed to bind context", restTemplate);
        assertNotNull("Base url not binded", baseURL);
    }

    @When("^I send a GET all person request to URL \"(.*?)\" with accept header \"(.*?)\"$")
    public void shouldInvokeGetAllPersonService(String uri, String contentType) throws Throwable {
        assertNotNull("getAllPersonURL is null", uri);
        assertNotNull("contentType is null", contentType);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, contentType);

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

        response = restTemplate.exchange(baseURL + uri, HttpMethod.GET, requestEntity,
                PersonList.class);

        assertNotNull(String.format("response '%s' is null", uri), response);

    }

    @Then("^I should get list of Persons as JSON response with status (\\d+)$")
    public void shouldHaveSuccessfullyInvokedService(int statusCode) {
        assertEquals(response.getStatusCode().value(), statusCode);
        assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
    }

    @Then("^The List size should be (\\d+)$")
    public void sizeOfResponse(int size) throws Throwable {
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getPersons());
        assertEquals(response.getBody().getPersons().size(), 7);
    }

}
