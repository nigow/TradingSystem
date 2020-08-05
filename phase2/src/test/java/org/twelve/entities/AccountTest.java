package org.twelve.entities;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked"})
public class AccountTest extends TestCase {


    public void testCreation() {
        ArrayList<Permissions> permissions = new ArrayList<>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.TRADE);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);
        
        ArrayList<Integer> wishlist = new ArrayList<>();
        wishlist.add(1);
        wishlist.add(2);
        new Account("Ethan", "1234",
                (ArrayList<Integer>) wishlist.clone(),
                (ArrayList<Permissions>) permissions.clone(), 1, "placeholder");
    }


    public void testGetUsername() {
        ArrayList<Permissions> permissions = new ArrayList<>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.TRADE);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);

        Account user = new Account("Ethan", "1234",
                (ArrayList<Permissions>) permissions.clone(), 1,
                "placeholder");
        TestCase.assertEquals(user.getUsername(), "Ethan");
    }


    public void testGetPassword() {
        ArrayList<Permissions> permissions = new ArrayList<>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.TRADE);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);
        
        Account user = new Account("Ethan", "1234",
                (ArrayList<Permissions>) permissions.clone(), 1,
                "placeholder");
        TestCase.assertEquals(user.getPassword(), "1234");
    }

    public void testSetPassword() {
        ArrayList<Permissions> permissions = new ArrayList<>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.TRADE);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);
        
        Account user = new Account("Ethan", "1234",
                (ArrayList<Permissions>) permissions.clone(), 1, "placeholder");
        user.setPassword("4321");
        TestCase.assertEquals(user.getPassword(), "4321");
    }

    public void testGetWishList() {
        ArrayList<Permissions> permissions = new ArrayList<>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.TRADE);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);
        
        Account user = new Account("Ethan", "1234",
                (ArrayList<Permissions>) permissions.clone(), 1, "placeholder");
        ArrayList<Integer> lst = new ArrayList<>();
        TestCase.assertEquals(user.getWishlist(), lst);
    }

    public void testGetPermissions() {
        ArrayList<Permissions> permissions = new ArrayList<>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.TRADE);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);
        
        Account user = new Account("Ethan", "1234",
                (ArrayList<Permissions>) permissions.clone(), 1,
                "placeholder");
        TestCase.assertEquals(user.getPermissions(), permissions);
    }

    public void testGetOwnerID() {
        ArrayList<Permissions> permissions = new ArrayList<>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.TRADE);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);
        
        Account user = new Account("Ethan", "1234",
                (ArrayList<Permissions>) permissions.clone(), 1,
                "placeholder");
        TestCase.assertEquals(user.getAccountID(), 1);
    }

    public void testAddPermission() {
        ArrayList<Permissions> permissions = new ArrayList<>();
        permissions.add(Permissions.LOGIN);
        Account user = new Account("Ethan", "1234",
                (ArrayList<Permissions>) permissions.clone(), 1,
                "placeholder");
        user.addPermission(Permissions.FREEZE);
        permissions.add(Permissions.FREEZE);
        TestCase.assertEquals(user.getPermissions(), permissions);
    }

    public void testRemovePermission() {
        ArrayList<Permissions> permissions = new ArrayList<>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.TRADE);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);

        Account user = new Account("Ethan", "1234",
                (List<Permissions>) permissions.clone(), 1,
                "placeholder");
        TestCase.assertTrue(user.removePermission(Permissions.LOGIN));
        permissions.remove(Permissions.LOGIN);
        TestCase.assertEquals(user.getPermissions(), permissions);
    }

    public void testRemoveNotInPermissionsList() {
        ArrayList<Permissions> permissions = new ArrayList<>();
        permissions.add(Permissions.LOGIN);
        Account user = new Account("Ethan", "1234",
                (List<Permissions>) permissions.clone(), 1,
                "placeholder");
        user.removePermission(Permissions.CREATE_ITEM);
        TestCase.assertEquals(user.getPermissions(), permissions);
    }

    public void testAddToWishlist() {
        ArrayList<Permissions> permissions = new ArrayList<>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.FREEZE);
        Account user = new Account("Ethan", "1234",
                (ArrayList<Permissions>) permissions.clone(), 1,
                "placeholder");
        List<Integer> lst = new ArrayList<>();
        lst.add(1);
        lst.add(2);
        user.addToWishlist(1);
        user.addToWishlist(2);
        TestCase.assertEquals(user.getWishlist(), lst);
    }

    public void testRemoveFromWishlist() {
        ArrayList<Permissions> permissions = new ArrayList<>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.TRADE);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);

        Account user = new Account("Ethan", "1234",
                (ArrayList<Permissions>) permissions.clone(), 1,
                "placeholder");
        ArrayList<Integer> lst = new ArrayList<>();
        lst.add(1);
        user.addToWishlist(1);
        user.addToWishlist(2);
        TestCase.assertTrue(user.removeFromWishList(2));
        TestCase.assertEquals(user.getWishlist(), lst);
    }

    public void testRemoveNotInWishlist() {

        ArrayList<Permissions> permissions = new ArrayList<>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.CREATE_ITEM);
        permissions.add(Permissions.CONFIRM_ITEM);
        permissions.add(Permissions.TRADE);
        permissions.add(Permissions.BROWSE_INVENTORY);
        permissions.add(Permissions.REQUEST_UNFREEZE);

        Account user = new Account("Ethan", "1234",
                (ArrayList<Permissions>) permissions.clone(), 1, "placeholder");
        ArrayList<Integer> lst = new ArrayList<>();
        lst.add(1);
        user.addToWishlist(1);
        user.removeFromWishList(2);
        TestCase.assertEquals(user.getWishlist(), lst);
    }

    public void testToString() {
        ArrayList<Permissions> permissions = new ArrayList<>();
        permissions.add(Permissions.LOGIN);
        permissions.add(Permissions.FREEZE);
        Account user = new Account("Ethan", "1234",
                (List<Permissions>) permissions.clone(), 1, "placeholder");
        TestCase.assertTrue(user.toString().contains("Ethan"));
    }
}
