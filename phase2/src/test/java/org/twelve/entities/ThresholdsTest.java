package org.twelve.entities;

import junit.framework.TestCase;


public class ThresholdsTest extends TestCase {

    public void testCreate() {
        new Thresholds(1, 2, 10, 1, 1, 1, 1);
    }

    public void testGetLendMoreThanBorrow() {
        Thresholds initial = new Thresholds(1, 2, 10, 1, 1, 1, 1);
        TestCase.assertEquals(initial.getLendMoreThanBorrow(), 1);
        TestCase.assertFalse("Test", initial.getLendMoreThanBorrow() == 2);
    }

    public void testGetMaxIncompleteTrade() {
        Thresholds initial = new Thresholds(1, 2, 10, 1, 1, 1, 1);
        TestCase.assertEquals(initial.getMaxIncompleteTrade(), 2);
        TestCase.assertFalse("Test", initial.getMaxIncompleteTrade() == 1);
    }

    public void testSetLendMoreThanBorrow() {
        Thresholds initial = new Thresholds(1, 2, 10, 1, 1, 1, 1);
        initial.setLendMoreThanBorrow(3);
        TestCase.assertEquals(initial.getLendMoreThanBorrow(), 3);
    }

    public void testSetMaxIncompleteTrade() {
        Thresholds initial = new Thresholds(1, 2, 10, 1, 1, 1, 1);
        initial.setMaxIncompleteTrade(4);
        TestCase.assertEquals(initial.getMaxIncompleteTrade(), 4);
    }

    public void testSetMaxWeeklyTrade() {
        Thresholds initial = new Thresholds(1, 2, 10, 1, 1, 1, 1);
        initial.setMaxWeeklyTrade(4);
        TestCase.assertEquals(initial.getMaxWeeklyTrade(), 4);
    }

    public void testGetMaxWeeklyTrade() {
        Thresholds initial = new Thresholds(1, 2, 10, 1, 1, 1, 1);
        TestCase.assertEquals(initial.getMaxWeeklyTrade(), 10);
    }
}