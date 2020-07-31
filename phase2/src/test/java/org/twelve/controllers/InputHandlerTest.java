package org.twelve.controllers;

import org.junit.Assert;
import org.junit.Test;

public class InputHandlerTest {

    @Test
    public void isNum() {
        InputHandler inputHandler = new InputHandler();
        Assert.assertTrue(inputHandler.isNum("3"));
        Assert.assertTrue(inputHandler.isNum("0"));
        Assert.assertFalse(inputHandler.isNum("ghd"));
        Assert.assertFalse(inputHandler.isNum("😊"));
        Assert.assertFalse(inputHandler.isNum("-1"));
    }

    @Test
    public void isDate() {
        // Passing tests:
        InputHandler inputHandler = new InputHandler();
        Assert.assertTrue(inputHandler.isDate("2019-05-11"));
        Assert.assertTrue(inputHandler.isDate("3000-01-01"));
        Assert.assertTrue(inputHandler.isDate("2020-02-29"));
        Assert.assertFalse(inputHandler.isDate("3000-999"));
        Assert.assertFalse(inputHandler.isDate("g000-99-gh"));


        // Failing Tests:
        Assert.assertFalse(inputHandler.isDate("2018-11-31"));
        Assert.assertFalse(inputHandler.isDate("2019-02-29"));
        Assert.assertFalse(inputHandler.isDate("2018-02-30"));
    }

    @Test
    public void isTime() {
        InputHandler inputHandler = new InputHandler();
        Assert.assertTrue(inputHandler.isTime("23:59"));
        Assert.assertTrue(inputHandler.isTime("00:00"));
        Assert.assertFalse(inputHandler.isTime("11:AM"));
        Assert.assertFalse(inputHandler.isTime("10 : 59"));
        Assert.assertFalse(inputHandler.isTime("-3:45"));
    }

    @Test
    public void isBool() {
        InputHandler inputHandler = new InputHandler();
        Assert.assertTrue(inputHandler.isBool("y"));
        Assert.assertTrue(inputHandler.isBool("n"));
        Assert.assertFalse(inputHandler.isBool("yes"));
        Assert.assertFalse(inputHandler.isBool("fjf"));
    }

    @Test
    public void isExitStr() {
        InputHandler inputHandler = new InputHandler();
        Assert.assertTrue(inputHandler.isExitStr("-1"));
        Assert.assertFalse(inputHandler.isExitStr("-2"));
        Assert.assertFalse(inputHandler.isExitStr("+1"));
    }
}
