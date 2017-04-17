package group3.psit3.zhaw.ch.travelbuddy.util;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class SimpleJsonBuilderTest {

    private SimpleJsonBuilder testObj;

    @Before
    public void setUp() {
        this.testObj = new SimpleJsonBuilder();
    }

    @Test
    public void testBuildWithNoPropertiesReturnsEmptyJsonObjectString() throws Exception {
        assertThat(testObj.build(), is("{}"));
    }

    @Test
    public void testBuildWithStringPropertiesReturnsValidJsonString() throws Exception {
        String actual = testObj.setProperty("whatever", 42).setProperty("foo", "bar").build();
        assertThat(actual, containsString("whatever:42"));
        assertThat(actual, containsString("foo:bar"));
    }

    @Test
    public void testBuildWithStringPropertiesReturnsMinifiedJsonString() throws Exception {
        String actual = testObj.setProperty("whatever", 42).setProperty("foo", "bar").build();
        assertThat(actual, not(containsString(" ")));
    }

    @Test
    public void testBuildWithStringPropertiesReturnsJsonStringEnclosedBrackets() throws Exception {
        String actual = testObj.setProperty("whatever", 42).setProperty("foor", "bar").build();
        assertThat(actual.substring(0, 1), is("{"));
        assertThat(actual.substring(actual.length()-1, actual.length()), is("}"));
    }

}