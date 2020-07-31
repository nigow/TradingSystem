package org.twelve.entities;

import junit.framework.TestCase;


public class RestrictionsTest extends TestCase {

    public void testCreate() {
        new Restrictions(1,  2, 10);
    }

    public void testGetLendMoreThanBorrow() {
        Restrictions initial = new Restrictions(1, 2, 10);
        TestCase.assertEquals(initial.getLendMoreThanBorrow(), 1);
        TestCase.assertFalse("Test", initial.getLendMoreThanBorrow() == 2);
    }
    public void testGetMaxIncompleteTrade() {
        Restrictions initial = new Restrictions(1, 2, 10);
        TestCase.assertEquals(initial.getMaxIncompleteTrade(), 2);
        TestCase.assertFalse("Test", initial.getMaxIncompleteTrade() == 1);
    }
    public void testSetLendMoreThanBorrow() {
        Restrictions initial = new Restrictions(1, 2, 10);
        initial.setLendMoreThanBorrow(3);
        TestCase.assertEquals(initial.getLendMoreThanBorrow(), 3);
    }
    public void testSetMaxIncompleteTrade() {
        Restrictions initial = new Restrictions(1, 2, 10);
        initial.setMaxIncompleteTrade(4);
        TestCase.assertEquals(initial.getMaxIncompleteTrade(), 4);
    }

    public void testSetMaxWeeklyTrade() {
        Restrictions initial = new Restrictions(1, 2, 10);
        initial.setMaxWeeklyTrade(4);
        TestCase.assertEquals(initial.getMaxWeeklyTrade(), 4);
    }
    public void testGetMaxWeeklyTrade() {
        Restrictions initial = new Restrictions(1, 2, 10);
        TestCase.assertEquals(initial.getMaxWeeklyTrade(), 10);
    }
}