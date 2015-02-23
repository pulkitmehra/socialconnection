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
import com.exercise.socialconnection.web.model.InfoResponse;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Step definition for Getting count of person between two given person using REST service.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
public class GetCountBetweenTwoPersonStepDef {

    private RestTemplate restTemplate;
    private String baseURL;
    private ResponseEntity<InfoResponse> response;

    @Before
    public void setUp() {
        SpringContext springContext = SpringContext
                .getInstance(new AnnotationConfigApplicationContext(RestConfig.class));
        restTemplate = springContext.getBean(RestTemplate.class);
        baseURL = springContext.getBaseURL();
    }

    @When("^I send a GET request to find count of person between two person \"(.*?)\" and \"(.*?)\" request to URL \"(.*?)\" with accept header \"(.*?)\"$")
    public void getPathCountBetweenTwoPerson(String fromName, String toName, String URL,
            String contentType) throws Throwable {
        assertNotNull("getPathCountURL is null", URL);
        assertNotNull("contentType is null", contentType);
        assertNotNull("fromPerson name is null", fromName);
        assertNotNull("toPerson name is null", toName);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, contentType);
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

        response = restTemplate.exchange(baseURL + URL, HttpMethod.GET, requestEntity,
                InfoResponse.class, fromName, toName);

    }

    @Then("^The path count should be (\\d+)$")
    public void assertPathCountSize(int count) throws Throwable {
        assertEquals(response.getStatusCode().value(), 200);
        assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getValue());
        assertEquals(response.getBody().getValue(), Integer.valueOf(count));
    }

}
