package gateways;

import junit.framework.TestCase;


public class ManualConfigTest extends TestCase {
    public void testAccountManagerCreation() {
        ManualConfig mc = new ManualConfig();
        assertNotNull(mc.getAccountManager());
    }

    public void testAuthManagerCreation() {
        ManualConfig mc = new ManualConfig();
        assertNotNull(mc.getAuthManager());
    }

    public void testItemManagerCreation() {
        ManualConfig mc = new ManualConfig();
        assertNotNull(mc.getItemManager());
    }

    public void testFreezingUtilityCreation() {
        ManualConfig mc = new ManualConfig();
        assertNotNull(mc.getFreezingUtility());
    }

    public void testTradeManagerCreation() {
        ManualConfig mc = new ManualConfig();
        assertNotNull(mc.getTradeManager());
    }

}
