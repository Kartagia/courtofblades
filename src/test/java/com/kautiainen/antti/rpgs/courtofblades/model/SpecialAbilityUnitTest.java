package com.kautiainen.antti.rpgs.courtofblades.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SpecialAbilityUnitTest {

    @TestTemplate
    @ExtendWith(SpecialAbilityTestInvocationContextProvider.class)
    @Tag("serialization")
    @Tag("model")
    public void whenSerializationIsRequired_thenProperJsonIsReturned(SpecialAbilityTestCase testCase) {
        if (testCase == null) return;
        ObjectMapper mapper = new ObjectMapper();
        SpecialAbility specialAbility = testCase.getExpectedInstance();
        try {
            String json = mapper.writeValueAsString(specialAbility);
            SpecialAbility actualAbility = mapper.readValue(json, SpecialAbility.class);
     
            if (specialAbility == null) {
                assertTrue(actualAbility == null);
            } else {
                assertEquals(specialAbility.getName(), actualAbility.getName());
                assertEquals(specialAbility.getDescription(), actualAbility.getDescription());
            }
        } catch(JsonProcessingException jpe) {
            assertEquals(testCase.getExpectedException(), jpe.getCause());
        } catch (IOException e) {
            // This should never happen
            fail("I/O Exception on parse from string!");
        }
    }

}
