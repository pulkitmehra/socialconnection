package com.exercise.socialconnection.uat;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * Runs {@link Cucumber} Acceptance test cases.
 * 
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
@RunWith(Cucumber.class)
@CucumberOptions(format = { "pretty", "html:build/reports/html" })
public class SocialConnectionUATRunner {
}