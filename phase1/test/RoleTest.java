import entities.Role;
import entities.Roles;
import junit.framework.TestCase;

import entities.Permissions;
import java.util.ArrayList;

public class RoleTest extends TestCase {

    public void testCreate() {
        new Role(Roles.BASIC,  new ArrayList<>());
    }

    public void testContains() {
        ArrayList<Permissions> permissions = new ArrayList<>();
        permissions.add(Permissions.LOGIN);
        Role account = new Role(Roles.BASIC, permissions);
        TestCase.assertFalse(account.contains(Permissions.ADD_ADMIN));
        TestCase.assertTrue(account.contains(Permissions.LOGIN));
    }

    public void testGetId() {
        Role initial = new Role(Roles.BASIC,  new ArrayList<>());
        TestCase.assertEquals(initial.getId(), Roles.BASIC);
    }
}