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
        ControllerInputValidator controllerInputValidator = new ControllerInputValidator();
        Assert.assertTrue(controllerInputValidator.isTime("23:59"));
        Assert.assertTrue(controllerInputValidator.isTime("00:00"));
        Assert.assertFalse(controllerInputValidator.isTime("11:AM"));
        Assert.assertFalse(controllerInputValidator.isTime("10 : 59"));
        Assert.assertFalse(controllerInputValidator.isTime("-3:45"));
    }

    @Test
    public void isBool() {
        ControllerInputValidator controllerInputValidator = new ControllerInputValidator();
        Assert.assertTrue(controllerInputValidator.isBool("y"));
        Assert.assertTrue(controllerInputValidator.isBool("n"));
        Assert.assertFalse(controllerInputValidator.isBool("yes"));
        Assert.assertFalse(controllerInputValidator.isBool("fjf"));
    }

    @Test
    public void isExitStr() {
        ControllerInputValidator controllerInputValidator = new ControllerInputValidator();
        Assert.assertTrue(controllerInputValidator.isExitStr("-1"));
        Assert.assertFalse(controllerInputValidator.isExitStr("-2"));
        Assert.assertFalse(controllerInputValidator.isExitStr("+1"));
    }
}
