package com.kautiainen.antti.rpgs.courtofblades.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The test testing coterie model.
 */
public class CoterieUnitTest {
    @Disabled("Unimplemented")
    @Test
    void testGetEquivalentFilter() {

    }

    @Disabled("Unimplemented")
    @Test
    void testGetHouse() {

    }

    @Disabled("Unimplemented")
    @Test
    void testGetSpecialAbilities() {

    }

    @Disabled("Unimplemented")
    @Test
    void testGetType() {

    }

    @Disabled("Unimplemented")
    @Test
    void testGetUpgrades() {

    }

    @Disabled("Unimplemented")
    @Test
    void testSetHouse() {

    }

    @Disabled("Unimplemented")
    @Test
    void testSetSpecialAbilities() {

    }

    @Disabled("Unimplemented")
    @Test
    void testSetType() {

    }

    @Disabled("Unimplemented")
    @Test
    void testSetUpgrades() {

    }

    @Disabled("Unimplemented")
    @Test
    void testSetUpgrades2() {

    }

    @Disabled("Unimplemented")
    @Test
    void testValidSpecialAbility() {

    }

    /**
     * The serialization and deserialization test.
     * The test does simply tests the serialization and deserialization produces same
     * test result.
     * @param testCase The tested test case.
     */
    @TestTemplate
    @ExtendWith(CoterieTestInvocationContextProvider.class)
    @Tag("serialization")
    @Tag("model")
    public void whenSerializationIsRequired_thenProperJsonIsReturned(CoterieTestCase<?, ?> testCase) {
        if (testCase == null) return;
        ObjectMapper mapper = new ObjectMapper();
        Coterie expectedResult = testCase.getTestTarget().orElse(null);
        try {
            String json = mapper.writeValueAsString(expectedResult);
            Coterie result = mapper.readValue(json, Coterie.class);
     
            if (expectedResult == null) {
                assertTrue(result == null);
            } else if (result == null) {
                fail("The deserialization returned unexpected undefined value", null);
            } else {
                assertEquals(expectedResult.getName(), result.getName());
                assertEquals(expectedResult.getUpgrades(), result.getUpgrades());
                assertEquals(expectedResult.getSpecialAbilities(), result.getSpecialAbilities());
                assertEquals(expectedResult.getType(), result.getType());
            }
        } catch(JsonProcessingException jpe) {
            assertEquals(testCase.getExpectedException(), jpe.getCause());
        } catch (IOException e) {
            // This should never happen
            fail("I/O Exception on parse from string!");
        }
    }

    /**
     * The test case testing the json strings produce corretn coterie.
     * @param testCase The test case.
     */
    @TestTemplate
    @ExtendWith(CoterieTestInvocationContextProvider.class)
    @Tag("serialization")
    @Tag("model")
    public void whenSerializationIsRequired_thenProperJsonIsReturned(TestCase<Void, String, Coterie> testCase) {
        if (testCase == null) return;
        ObjectMapper mapper = new ObjectMapper();
        String json = testCase.getParameters();
        Coterie expectedResult = testCase.getExpectedResult();
        try {
            Coterie result = mapper.readValue(json, Coterie.class);
     
            if (expectedResult == null) {
                assertTrue(result == null);
            } else if (result == null) {
                fail("The deserialization returned unexpected undefined value", null);
            } else {
                assertEquals(expectedResult.getName(), result.getName());
                assertEquals(expectedResult.getUpgrades(), result.getUpgrades());
                assertEquals(expectedResult.getSpecialAbilities(), result.getSpecialAbilities());
                assertEquals(expectedResult.getType(), result.getType());
            }
        } catch(JsonProcessingException jpe) {
            assertEquals(testCase.getExpectedException(), jpe.getCause());
        } catch (IOException e) {
            // This should never happen
            fail("I/O Exception on parse from string!");
        }
    }


}
