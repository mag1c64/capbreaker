package com.exceptions;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ValidationExceptionTest {
    @Test
    public void noArgsConstructorTest() {
        Exception e = new ValidationException();
        Assert.assertNotNull("Exception constructor error", e);
    }

    @Test
    public void argConstructorTest() {
        Exception e = new ValidationException("error");
        Assert.assertNotNull("Exception constructor error", e);
    }
}