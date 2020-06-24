import entities.Restrictions;
import junit.framework.TestCase;


public class RestrictionsTest extends TestCase {

    public void testCreate() {
        new Restrictions(1,  2);
    }

    public void testGetLendMoreThanBorrow() {
        Restrictions initial = new Restrictions(1, 2);
        TestCase.assertEquals(initial.getLendMoreThanBorrow(), 1);
        TestCase.assertFalse("Test", initial.getLendMoreThanBorrow() == 2);
    }
    public void testGetMaxIncompleteTrade() {
        Restrictions initial = new Restrictions(1, 2);
        TestCase.assertEquals(initial.getMaxIncompleteTrade(), 2);
        TestCase.assertFalse("Test", initial.getLendMoreThanBorrow() == 1);
    }
    public void testSetLendMoreThanBorrow() {
        Restrictions initial = new Restrictions(1, 2);
        initial.setLendMoreThanBorrow(3);
        TestCase.assertEquals(initial.getLendMoreThanBorrow(), 3);
    }
    public void testSetMaxIncompleteTrade() {
        Restrictions initial = new Restrictions(1, 2);
        initial.setMaxIncompleteTrade(4);
        TestCase.assertEquals(initial.getMaxIncompleteTrade(), 4);
    }
}