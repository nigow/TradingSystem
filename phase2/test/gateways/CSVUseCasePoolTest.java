package gateways;

import junit.framework.TestCase;

import java.io.IOException;


public class CSVUseCasePoolTest extends TestCase {
    public void testAccountManagerCreation() {
        try {
            CSVUseCasePool mc = new CSVUseCasePool();
            assertNotNull(mc.getAccountManager());
        }
        catch (IOException ignored) {
            fail();
        }

    }

    public void testAuthManagerCreation() {
        try {
            CSVUseCasePool mc = new CSVUseCasePool();
            assertNotNull(mc.getAuthManager());
        }
        catch (IOException ignored) {
            fail();
        }

    }

    public void testItemManagerCreation() {
        try {
            CSVUseCasePool mc = new CSVUseCasePool();
            assertNotNull(mc.getItemManager());
        }
        catch (IOException ignored) {
            fail();
        }

    }

    public void testFreezingUtilityCreation() {
        try {
            CSVUseCasePool mc = new CSVUseCasePool();
            assertNotNull(mc.getFreezingUtility());
        }
        catch (IOException ignored) {
            fail();
        }

    }

    public void testTradeManagerCreation() {
        try {
            CSVUseCasePool mc = new CSVUseCasePool();
            assertNotNull(mc.getOldTradeManager());
        }
        catch (IOException ignored) {
            fail();
        }

    }

    public void testWishlistUtilityCreation() {
        try {
            CSVUseCasePool mc = new CSVUseCasePool();
            assertNotNull(mc.getWishlistManager());
        }
        catch (IOException ignored) {
            fail();
        }

    }

    public void testGetItemUtility() {
        try {
            CSVUseCasePool mc = new CSVUseCasePool();
            assertNotNull(mc.getItemUtility());
        }
        catch (IOException ignored) {
            fail();
        }

    }

    public void testGetTradeUtility() {
        try {
            CSVUseCasePool mc = new CSVUseCasePool();
            assertNotNull(mc.getOldTradeUtility());
        }
        catch (IOException ignored) {
            fail();
        }

    }


}
