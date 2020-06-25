package entities;

import entities.Account;
import entities.Roles;
import junit.framework.TestCase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AccountTest extends TestCase {
    public void testCreation() {
        ArrayList roles = new ArrayList<Roles>();
        roles.add(Roles.BASIC);
        roles.add(Roles.TRADER);
        roles.add(Roles.ADMIN);
        Account user = new Account("Ethan", "1234", (ArrayList<Roles>) roles.clone(), 1);
    }

    public void testGetUsername() {
        ArrayList roles = new ArrayList<Roles>();
        roles.add(Roles.BASIC);
        roles.add(Roles.TRADER);
        roles.add(Roles.ADMIN);
        Account user = new Account("Ethan", "1234", (ArrayList<Roles>) roles.clone(), 1);
        TestCase.assertEquals(user.getUsername(), "Ethan");
    }

    public void testGetPassword() {
        ArrayList roles = new ArrayList<Roles>();
        roles.add(Roles.BASIC);
        roles.add(Roles.TRADER);
        roles.add(Roles.ADMIN);
        Account user = new Account("Ethan", "1234", (ArrayList<Roles>) roles.clone(), 1);
        TestCase.assertEquals(user.getPassword(), "1234");
    }

    public void testSetPassword() {
        ArrayList roles = new ArrayList<Roles>();
        roles.add(Roles.BASIC);
        roles.add(Roles.TRADER);
        roles.add(Roles.ADMIN);
        Account user = new Account("Ethan", "1234", (ArrayList<Roles>) roles.clone(), 1);
        user.setPassword("4321");
        TestCase.assertEquals(user.getPassword(), "4321");
    }

    public void testGetWishList() {
        ArrayList roles = new ArrayList<Roles>();
        roles.add(Roles.BASIC);
        roles.add(Roles.TRADER);
        roles.add(Roles.ADMIN);
        Account user = new Account("Ethan", "1234", (ArrayList<Roles>) roles.clone(), 1);
        ArrayList lst = new ArrayList<Integer>();
        TestCase.assertEquals(user.getWishlist(), lst);
    }

    public void testGetRolesID() {
        ArrayList roles = new ArrayList<Roles>();
        roles.add(Roles.BASIC);
        roles.add(Roles.TRADER);
        roles.add(Roles.ADMIN);
        Account user = new Account("Ethan", "1234", (ArrayList<Roles>) roles.clone(), 1);
        TestCase.assertEquals(user.getRolesID(), roles);
    }

    public void testGetOwnerID() {
        ArrayList roles = new ArrayList<Roles>();
        roles.add(Roles.BASIC);
        roles.add(Roles.TRADER);
        roles.add(Roles.ADMIN);
        Account user = new Account("Ethan", "1234", (ArrayList<Roles>) roles.clone(), 1);
        TestCase.assertEquals(user.getAccountID(), 1);
    }

    public void testAddRole() {
        ArrayList roles = new ArrayList<Roles>();
        roles.add(Roles.BASIC);
        roles.add(Roles.ADMIN);
        Account user = new Account("Ethan", "1234", (ArrayList<Roles>) roles.clone(), 1);
        user.addRole(Roles.TRADER);
        roles.add(Roles.TRADER);
        TestCase.assertEquals(user.getRolesID(), roles);
    }

    public void testRemoveRole() {
        ArrayList roles = new ArrayList<Roles>();
        roles.add(Roles.BASIC);
        roles.add(Roles.TRADER);
        roles.add(Roles.ADMIN);
        Account user = new Account("Ethan", "1234", (ArrayList<Roles>) roles.clone(), 1);
        TestCase.assertTrue(user.removeRole(Roles.TRADER));
        roles.remove(Roles.TRADER);
        TestCase.assertEquals(user.getRolesID(), roles);
    }

    public void testRemoveNotInRolesList() {
        ArrayList roles = new ArrayList<Roles>();
        roles.add(Roles.BASIC);
        roles.add(Roles.TRADER);
        Account user = new Account("Ethan", "1234", (ArrayList<Roles>) roles.clone(), 1);
        user.removeRole(Roles.ADMIN);
        TestCase.assertEquals(user.getRolesID(), roles);
    }

    public void testAddToWishlist() {
        ArrayList roles = new ArrayList<Roles>();
        roles.add(Roles.BASIC);
        roles.add(Roles.ADMIN);
        Account user = new Account("Ethan", "1234", (ArrayList<Roles>) roles.clone(), 1);
        ArrayList lst = new ArrayList<Integer>();
        lst.add(1);
        lst.add(2);
        user.addToWishlist(1);
        user.addToWishlist(2);
        TestCase.assertEquals(user.getWishlist(), lst);
    }

    public void testRemoveFromWishlist() {
        ArrayList roles = new ArrayList<Roles>();
        roles.add(Roles.BASIC);
        roles.add(Roles.TRADER);
        roles.add(Roles.ADMIN);
        Account user = new Account("Ethan", "1234", (ArrayList<Roles>) roles.clone(), 1);
        ArrayList lst = new ArrayList<Integer>();
        lst.add(1);
        user.addToWishlist(1);
        user.addToWishlist(2);
        TestCase.assertTrue(user.removeFromWishList(2));
        TestCase.assertEquals(user.getWishlist(), lst);
    }

    public void testRemoveNotInWishlist() {
        ArrayList roles = new ArrayList<Roles>();
        roles.add(Roles.BASIC);
        roles.add(Roles.TRADER);
        roles.add(Roles.ADMIN);
        Account user = new Account("Ethan", "1234", (ArrayList<Roles>) roles.clone(), 1);
        ArrayList lst = new ArrayList<Integer>();
        lst.add(1);
        user.addToWishlist(1);
        user.removeFromWishList(2);
        TestCase.assertEquals(user.getWishlist(), lst);
    }

    public void testToString() {
        ArrayList roles = new ArrayList<Roles>();
        roles.add(Roles.BASIC);
        roles.add(Roles.TRADER);
        Account user = new Account("Ethan", "1234", (ArrayList<Roles>) roles.clone(), 1);
        TestCase.assertEquals(user.toString(), "Account name: Ethan\nID: 1\nRole: [BASIC, TRADER]");
    }
}
