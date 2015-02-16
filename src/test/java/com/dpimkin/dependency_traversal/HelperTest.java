package com.dpimkin.dependency_traversal;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class HelperTest {

    public void distinguishTest( String expectedKey, Set<String> expectedValue, String line ) {
        final Map.Entry<String, Set<String>> actualResult = Helper.distinguish( line );
        assertThat( actualResult, is( notNullValue() ));
        assertThat( actualResult.getKey(), is( equalTo( expectedKey )));
        assertThat( actualResult.getValue(), is( equalTo( expectedValue )));
    }


    @Test
    public void testWithoutDependency() throws Exception {
        String expectedKey;
        Set<String> expectedValue;
        distinguishTest(
                expectedKey = "single",
                expectedValue = null,
                "single: " );
    }

    @Test
    public void testWithoutDependencyTricky() throws Exception {
        String expectedKey;
        Set<String> expectedValue;
        distinguishTest(
                expectedKey = "tricky",
                expectedValue = null,
                "tricky:,, ,," );
    }

    @Test
    public void testWithOnlyOneDependency() throws Exception {
        String expectedKey;
        Set<String> expectedValue;
        distinguishTest(
                expectedKey = "alpha",
                expectedValue = new HashSet<String>() {{ add( "omega"); }},
                "alpha: omega" );
    }

    @Test
    public void testWithTwoDependencies() throws Exception {
        String expectedKey;
        Set<String> expectedValue;
        distinguishTest(
                expectedKey = "first",
                expectedValue = new HashSet<String>() {{ add( "second"); add( "third" ); }},
                "first: second, third" );
    }

    @Test
    public void testWithTwoDependenciesTricky() throws Exception {
        String expectedKey;
        Set<String> expectedValue;
        distinguishTest(
                expectedKey = "first",
                expectedValue = new HashSet<String>() {{ add( "second"); add( "third" ); }},
                "first: ,,,,second,,,,, third,,,,," );
    }
}