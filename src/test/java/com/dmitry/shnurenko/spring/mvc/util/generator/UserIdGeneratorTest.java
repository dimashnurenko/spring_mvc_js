package com.dmitry.shnurenko.spring.mvc.util.generator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class UserIdGeneratorTest {

    @InjectMocks
    private UserIdGenerator userIdGenerator;

    @Test
    public void uniqueValueShouldBeGenerate() throws Exception {
        Set<String> uniqueValues = new HashSet<>();

        for (int i = 0; i < 100_000; i++) {
            uniqueValues.add(userIdGenerator.generate());
        }

        assertThat(uniqueValues.size(), equalTo(100_000));
    }
}