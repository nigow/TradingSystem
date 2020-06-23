import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RoleTest extends TestCase {

    public void testCreate() {
        new Role("useless",  new ArrayList<>());
    }

    public void testContains() {
        List<String> permissions = new ArrayList<>();
        permissions.add("login");
        Role account = new Role("account", permissions);
        TestCase.assertFalse(account.contains("fly"));
        TestCase.assertTrue(account.contains("login"));
    }

    public void testGetId() {
        Role initial = new Role("useless",  new ArrayList<>());
        TestCase.assertEquals(initial.getId(), "useless");
    }
}