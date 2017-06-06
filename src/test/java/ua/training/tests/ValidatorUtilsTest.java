package ua.training.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.travel.command.utils.ValidatorUtils;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by yuuto on 6/6/17.
 */
@RunWith(Parameterized.class)
public class ValidatorUtilsTest {

    @Parameterized.Parameters
    public static Collection<Object> data() {
        return Arrays.asList(new Object[]{"1","2", "2.2", "0.2", "2.222", ".2", "1."});
    }

    private String value;

    public ValidatorUtilsTest(String value) {
        this.value = value;
    }

    @Test
    public void testValidatorDouble(){
        Assert.assertTrue(ValidatorUtils.isValidDouble(value));
    }

    @Test
    public void isEmptyStringTest(){
        Assert.assertFalse(ValidatorUtils.isEmptyString(value));
    }
}
