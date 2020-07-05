package controllers;

import org.junit.Assert;
import org.junit.Test;

public class ControllerInputValidatorTest {

    @Test
    public void isNum() {
        ControllerInputValidator controllerInputValidator = new ControllerInputValidator();
        Assert.assertTrue(controllerInputValidator.isNum("3"));
        Assert.assertTrue(controllerInputValidator.isNum("0"));
        Assert.assertFalse(controllerInputValidator.isNum("ghd"));
        Assert.assertFalse(controllerInputValidator.isNum("😊"));
        Assert.assertFalse(controllerInputValidator.isNum("-1"));
    }

    @Test
    public void isDate() {
        // Passing tests:
        ControllerInputValidator controllerInputValidator = new ControllerInputValidator();
        Assert.assertTrue(controllerInputValidator.isDate("2019-05-11"));
        Assert.assertTrue(controllerInputValidator.isDate("3000-01-01"));
        Assert.assertTrue(controllerInputValidator.isDate("2020-02-29"));
        Assert.assertFalse(controllerInputValidator.isDate("3000-999"));
        Assert.assertFalse(controllerInputValidator.isDate("g000-99-gh"));


        // Failing Tests:
        Assert.assertFalse(controllerInputValidator.isDate("2018-11-31"));
        Assert.assertFalse(controllerInputValidator.isDate("2019-02-29"));
        Assert.assertFalse(controllerInputValidator.isDate("2018-02-30"));


    }

    @Test
    public void isTime() {
    }

    @Test
    public void isBool() {
    }

    @Test
    public void isExitStr() {
    }
}
