package org.twelve.entities;

import junit.framework.TestCase;

import java.util.ArrayList;

public class ItemTest extends TestCase{

    public void testCreate() {
        new Item(1, "Harry Potter", "a book", 2);
    }

    public void testGetItemID() {
        Item item = new Item(1, "Harry Potter", "a book", 2);
        TestCase.assertEquals(item.getItemID(), 1);
    }

    public void testGetItemName() {
        Item item = new Item(1, "Harry Potter", "a book", 2);
        TestCase.assertEquals(item.getName(), "Harry Potter");
    }

    public void testGetItemDescription() {
        Item item = new Item(1, "Harry Potter", "a book", 2);
        TestCase.assertEquals(item.getDescription(), "a book");
    }

    public void testGetIsApproved() {
        Item item = new Item(1, "Harry Potter", "a book", 2);
        TestCase.assertFalse(item.isApproved());
    }

    public void testGetUserID(){
        Item item = new Item(1, "Harry Potter", "a book", 2);
        TestCase.assertEquals(item.getOwnerID(), 2);
    }

    public void testSetName() {
        Item item = new Item(1, "Harry Potter", "a book", 2);
        item.setName("1984");
        TestCase.assertEquals(item.getName(), "1984");
    }

    public void testSetDescription() {
        Item item = new Item(1, "Harry Potter", "a book", 2);
        item.setDescription("not a book");
        TestCase.assertEquals(item.getDescription(), "not a book");
    }

    public void testApproval() {
        Item item = new Item(1, "Harry Potter", "a book", 2);
        TestCase.assertFalse(item.isApproved());
        item.approve();
        TestCase.assertTrue(item.isApproved());
        item.disapprove();
        TestCase.assertFalse(item.isApproved());
    }

    public void testSetOwnerID() {
        Item item = new Item(1, "Harry Potter", "a book", 2);
        item.setOwnerID(3);
        TestCase.assertEquals(item.getOwnerID(), 3);
    }

}
