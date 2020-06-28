package entities;

import entities.Account;
import entities.Permissions;
import junit.framework.TestCase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AccountTest extends TestCase {
    public void testCreation() {
        ArrayList permissions = new ArrayList<Permissions>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.LEND);
        permissions.add(Permissions.BORROW);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);
        
        Account user = new Account("Ethan", "1234", (ArrayList<Permissions>) permissions.clone(), 1);
        ArrayList wishlist = new ArrayList<Integer>();
        wishlist.add(1);
        wishlist.add(2);
        Account userOverload = new Account("Ethan", "1234", (ArrayList<Integer>) wishlist.clone(), (ArrayList<Permissions>) permissions.clone(), 1);
    }



    public void testGetUsername() {
        ArrayList permissions = new ArrayList<Permissions>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.LEND);
        permissions.add(Permissions.BORROW);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);

        Account user = new Account("Ethan", "1234", (ArrayList<Permissions>) permissions.clone(), 1);
        TestCase.assertEquals(user.getUsername(), "Ethan");
    }

    public void testGetPassword() {
        ArrayList permissions = new ArrayList<Permissions>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.LEND);
        permissions.add(Permissions.BORROW);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);
        
        Account user = new Account("Ethan", "1234", (ArrayList<Permissions>) permissions.clone(), 1);
        TestCase.assertEquals(user.getPassword(), "1234");
    }

    public void testSetPassword() {
        ArrayList permissions = new ArrayList<Permissions>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.LEND);
        permissions.add(Permissions.BORROW);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);
        
        Account user = new Account("Ethan", "1234", (ArrayList<Permissions>) permissions.clone(), 1);
        user.setPassword("4321");
        TestCase.assertEquals(user.getPassword(), "4321");
    }

    public void testGetWishList() {
        ArrayList permissions = new ArrayList<Permissions>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.LEND);
        permissions.add(Permissions.BORROW);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);
        
        Account user = new Account("Ethan", "1234", (ArrayList<Permissions>) permissions.clone(), 1);
        ArrayList lst = new ArrayList<Integer>();
        TestCase.assertEquals(user.getWishlist(), lst);
    }

    public void testGetPermissions() {
        ArrayList permissions = new ArrayList<Permissions>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.LEND);
        permissions.add(Permissions.BORROW);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);
        
        Account user = new Account("Ethan", "1234", (ArrayList<Permissions>) permissions.clone(), 1);
        TestCase.assertEquals(user.getPermissions(), permissions);
    }

    public void testGetOwnerID() {
        ArrayList permissions = new ArrayList<Permissions>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.LEND);
        permissions.add(Permissions.BORROW);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);
        
        Account user = new Account("Ethan", "1234", (ArrayList<Permissions>) permissions.clone(), 1);
        TestCase.assertEquals(user.getAccountID(), 1);
    }

    public void testAddPermission() {
        ArrayList permissions = new ArrayList<Permissions>();
        permissions.add(Permissions.LOGIN);
        Account user = new Account("Ethan", "1234", (ArrayList<Permissions>) permissions.clone(), 1);
        user.addPermission(Permissions.FREEZE);
        permissions.add(Permissions.FREEZE);
        TestCase.assertEquals(user.getPermissions(), permissions);
    }

    public void testRemovePermission() {
        ArrayList permissions = new ArrayList<Permissions>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.LEND);
        permissions.add(Permissions.BORROW);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);

        Account user = new Account("Ethan", "1234", (ArrayList<Permissions>) permissions.clone(), 1);
        TestCase.assertTrue(user.removePermission(Permissions.LOGIN));
        permissions.remove(Permissions.LOGIN);
        TestCase.assertEquals(user.getPermissions(), permissions);
    }

    public void testRemoveNotInPermissionsList() {
        ArrayList permissions = new ArrayList<Permissions>();
        permissions.add(Permissions.LOGIN);
        Account user = new Account("Ethan", "1234", (ArrayList<Permissions>) permissions.clone(), 1);
        user.removePermission(Permissions.CREATE_ITEM);
        TestCase.assertEquals(user.getPermissions(), permissions);
    }

    public void testAddToWishlist() {
        ArrayList permissions = new ArrayList<Permissions>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.FREEZE);
        Account user = new Account("Ethan", "1234", (ArrayList<Permissions>) permissions.clone(), 1);
        ArrayList lst = new ArrayList<Integer>();
        lst.add(1);
        lst.add(2);
        user.addToWishlist(1);
        user.addToWishlist(2);
        TestCase.assertEquals(user.getWishlist(), lst);
    }

    public void testRemoveFromWishlist() {
        ArrayList permissions = new ArrayList<Permissions>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.LEND);
        permissions.add(Permissions.BORROW);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);

Account user = new Account("Ethan", "1234", (ArrayList<Permissions>) permissions.clone(), 1);
        ArrayList lst = new ArrayList<Integer>();
        lst.add(1);
        user.addToWishlist(1);
        user.addToWishlist(2);
        TestCase.assertTrue(user.removeFromWishList(2));
        TestCase.assertEquals(user.getWishlist(), lst);
    }

    public void testRemoveNotInWishlist() {
        ArrayList permissions = new ArrayList<Permissions>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.LEND);
        permissions.add(Permissions.BORROW);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);

Account user = new Account("Ethan", "1234", (ArrayList<Permissions>) permissions.clone(), 1);
        ArrayList lst = new ArrayList<Integer>();
        lst.add(1);
        user.addToWishlist(1);
        user.removeFromWishList(2);
        TestCase.assertEquals(user.getWishlist(), lst);
    }

    public void testToString() {
        ArrayList permissions = new ArrayList<Permissions>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.FREEZE);
        Account user = new Account("Ethan", "1234", (ArrayList<Permissions>) permissions.clone(), 1);
        TestCase.assertEquals(user.toString(), "Account name: Ethan\nID: 1\nPermission: [BASIC, TRADER]");
    }
}
