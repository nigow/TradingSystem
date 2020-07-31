package org.twelve.entities;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class OldTradeTest extends TestCase {

    public void testCreate() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        new OldTrade(1, 3, true, 1, 2, a, b, 0);
    }

    public void testGetId() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        OldTrade initial = new OldTrade(1, 3, true, 1, 2, a, b, 0);
        TestCase.assertEquals(initial.getId(), 1);
    }

    public void testGetTimePlaceId() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        OldTrade initial = new OldTrade(1, 3, true, 1, 2, a, b, 0);
        TestCase.assertEquals(initial.getTimePlaceID(), 3);
    }

    public void testIsPermanent() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        OldTrade initial = new OldTrade(1, 3, true, 1, 2, a, b, 0);
        TestCase.assertTrue(initial.isPermanent());
    }

    public void testGetTraderOneID() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        OldTrade initial = new OldTrade(1, 3, true, 4, 5, a, b, 0);
        TestCase.assertEquals(initial.getTraderOneID(), 4);
    }

    public void testGetTraderTwoID() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        OldTrade initial = new OldTrade(1, 3, true, 4, 5, a, b, 0);
        TestCase.assertEquals(initial.getTraderTwoID(), 5);
    }

    public void testGetItemOneID() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        a.add(1);
        a.add(2);
        b.add(3);
        List<Integer> c = new ArrayList<>();
        c.add(1);
        c.add(2);
        OldTrade initial = new OldTrade(1, 3, true, 4, 5, a, b, 0);
        TestCase.assertEquals(initial.getItemOneIDs(), c);
    }

    public void testGetItemTwoID() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        a.add(1);
        a.add(2);
        b.add(3);
        List<Integer> c = new ArrayList<>();
        c.add(3);
        OldTrade initial = new OldTrade(1, 3, true, 4, 5, a, b, 0);
        TestCase.assertEquals(initial.getItemTwoIDs(), c);
    }

    public void testGetStatus() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        OldTrade initial = new OldTrade(1, 3, true, 4, 5, a, b, 0);
        TestCase.assertEquals(initial.getStatus(), TradeStatus.UNCONFIRMED);
    }

    public void testGetLastEditorID() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        OldTrade initial = new OldTrade(1, 3, true, 4, 5, a, b, 0);
        TestCase.assertEquals(initial.getLastEditorID(), 4);
    }

    public void testGetEditedCounter() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        OldTrade initial = new OldTrade(1, 3, true, 4, 5, a, b, 0);
        TestCase.assertEquals(initial.getEditedCounter(), 0);
    }

    public void testSetTimePlaceID() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        OldTrade initial = new OldTrade(1, 3, true, 4, 5, a, b, 0);
        initial.setTimePlaceID(4);
        TestCase.assertEquals(initial.getTimePlaceID(), 4);
    }

    public void testSetPermanent() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        OldTrade initial = new OldTrade(1, 3, true, 4, 5, a, b, 0);
        initial.setPermanent(false);
        TestCase.assertFalse(initial.isPermanent());
    }

    public void testSetStatus() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        OldTrade initial = new OldTrade(1, 3, true, 4, 5, a, b, 0);
        initial.setStatus(TradeStatus.CONFIRMED);
        TestCase.assertEquals(initial.getStatus(), TradeStatus.CONFIRMED);
    }

    public void testSetLastEditorID() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        OldTrade initial = new OldTrade(1, 3, true, 4, 5, a, b, 0);
        initial.setLastEditorID(5);
        TestCase.assertEquals(initial.getLastEditorID(), 5);
    }

    public void testIncrementEditedCounter() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        OldTrade initial = new OldTrade(1, 3, true, 4, 5, a, b, 0);
        initial.incrementEditedCounter();
        TestCase.assertEquals(initial.getEditedCounter(), 1);
    }

    public void testToString() {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        a.add(1);
        a.add(2);
        b.add(3);
        OldTrade initial = new OldTrade(1, 3, true, 4, 5, a, b, 0);
        TestCase.assertTrue(initial.toString().contains("UNCONFIRMED"));

    }
}
