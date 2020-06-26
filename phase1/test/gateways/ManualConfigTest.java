package gateways;

import junit.framework.Test;
import junit.framework.TestCase;


public class ManualConfigTest extends TestCase {
    public void testAccountManager() {
        ManualConfig mc = new ManualConfig();
        assertNotNull(mc.getAccountManager());
    }
}
