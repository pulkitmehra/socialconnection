package com.exercise.socialconnection.uat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.exercise.socialconnection.web.model.ErrorResponse;
import com.exercise.socialconnection.web.model.PersonList;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Step definition for path of person between two given person using REST service.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
public class GetPathBetweenTwoPersonStepDef {

    private RestTemplate restTemplate;
    private String baseURL;
    private ResponseEntity<PersonList> response;
    private ObjectMapper jsonMapper = new ObjectMapper();
    private ErrorResponse errorResponse;

    @Before
    public void setUp() {
        SpringContext springContext = SpringContext
                .getInstance(new AnnotationConfigApplicationContext(RestConfig.class));
        restTemplate = springContext.getBean(RestTemplate.class);
        baseURL = springContext.getBaseURL();
    }

    @When("^I send a GET request to find path between two person \"(.*?)\" and \"(.*?)\" to URL \"(.*?)\" with accept header \"(.*?)\"$")
    public void getPathBetweenTwoGivenPerson(String fromPerson, String toPerson, String URL,
            String contentType) throws Throwable {
        assertNotNull("getPathUrl is null", URL);
        assertNotNull("contentType is null", contentType);
        assertNotNull("fromPerson name is null", fromPerson);
        assertNotNull("toPerson name is null", toPerson);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, contentType);
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        try {
            response = restTemplate.exchange(baseURL + URL, HttpMethod.GET, requestEntity,
                    PersonList.class, fromPerson, toPerson);

        }
        catch (HttpClientErrorException e) {
            assertNotNull(e.getResponseBodyAsString());
            errorResponse = jsonMapper.reader(ErrorResponse.class).readValue(
                    e.getResponseBodyAsString());
        }

    }

    @Then("^I should get list of Persons path as JSON response with status (\\d+)$")
    public void assertStatusAndContentType(int statusCode) throws Throwable {
        assertEquals(response.getStatusCode().value(), statusCode);
        assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
    }

    @Then("^The path size should be (\\d+)$")
    public void asssertSize(int size) throws Throwable {
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getPersons());
        assertEquals(response.getBody().getPersons().size(), 3);
    }

    @Then("^I should get error response JSON response with status (\\d+)$")
    public void assertJsonErrorResponse(int statuscode) throws Throwable {
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getStatusCode(), statuscode);
        assertNotNull(errorResponse.getErrorMessage());
        assertNotNull(errorResponse.getRequestedURI());
    }

}
