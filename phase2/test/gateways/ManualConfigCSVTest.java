package gateways;

import junit.framework.TestCase;

import javax.imageio.IIOException;
import java.io.IOException;


public class ManualConfigCSVTest extends TestCase {
    public void testAccountManagerCreation() {
        try {
            ManualConfigCSV mc = new ManualConfigCSV();
            assertNotNull(mc.getAccountManager());
        }
        catch (IOException ignored) {
            fail();
        }

    }

    public void testAuthManagerCreation() {
        try {
            ManualConfigCSV mc = new ManualConfigCSV();
            assertNotNull(mc.getAuthManager());
        }
        catch (IOException ignored) {
            fail();
        }

    }

    public void testItemManagerCreation() {
        try {
            ManualConfigCSV mc = new ManualConfigCSV();
            assertNotNull(mc.getItemManager());
        }
        catch (IOException ignored) {
            fail();
        }

    }

    public void testFreezingUtilityCreation() {
        try {
            ManualConfigCSV mc = new ManualConfigCSV();
            assertNotNull(mc.getFreezingUtility());
        }
        catch (IOException ignored) {
            fail();
        }

    }

    public void testTradeManagerCreation() {
        try {
            ManualConfigCSV mc = new ManualConfigCSV();
            assertNotNull(mc.getTradeManager());
        }
        catch (IOException ignored) {
            fail();
        }

    }

    public void testWishlistUtilityCreation() {
        try {
            ManualConfigCSV mc = new ManualConfigCSV();
            assertNotNull(mc.getWishlistUtility());
        }
        catch (IOException ignored) {
            fail();
        }

    }

    public void testGetItemUtility() {
        try {
            ManualConfigCSV mc = new ManualConfigCSV();
            assertNotNull(mc.getItemUtility());
        }
        catch (IOException ignored) {
            fail();
        }

    }

    public void testGetTradeUtility() {
        try {
            ManualConfigCSV mc = new ManualConfigCSV();
            assertNotNull(mc.getTradeUtility());
        }
        catch (IOException ignored) {
            fail();
        }

    }


}
