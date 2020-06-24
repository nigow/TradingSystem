import entities.TimePlace;
import org.junit.*;
import junit.framework.TestCase;
import java.time.LocalDateTime;
import java.sql.Time;

public class TimePlaceTest extends TestCase{

    public void testTimePlaceExists() {
        TimePlace initial = new TimePlace(1, LocalDateTime.now(), "UTM");
    }

    public void testGetId() {
        LocalDateTime date = LocalDateTime.of(2018, 2, 13, 15, 56);
        TimePlace initial = new TimePlace(1, date, "UTM");
        TestCase.assertEquals(initial.getId(), 1);
    }

    public void testGetPlace() {
        LocalDateTime date = LocalDateTime.of(2018, 2, 13, 15, 56);
        TimePlace initial = new TimePlace(1, date, "UTM");
        TestCase.assertEquals(initial.getPlace(), "UTM");
    }

    public void testGetTime() {
        LocalDateTime date = LocalDateTime.of(2018, 2, 13, 15, 56);
        TimePlace initial = new TimePlace(1, date, "UTM");
        LocalDateTime date2 = LocalDateTime.of(2018, 2, 13, 15, 56);
        TestCase.assertEquals(initial.getTime(), date2);
    }

    public void testSetPlace() {
        LocalDateTime date = LocalDateTime.of(2018, 2, 13, 15, 56);
        TimePlace initial = new TimePlace(1, date, "UTM");
        initial.setPlace("home");
        TestCase.assertEquals(initial.getPlace(), "home");
    }

    public void testSetTime() {
        LocalDateTime date = LocalDateTime.of(2018, 2, 13, 15, 56);
        TimePlace initial = new TimePlace(1, date, "UTM");
        LocalDateTime date2 = LocalDateTime.of(2017, 1, 11, 12, 12);
        initial.setTime(date2);
        LocalDateTime date3 = LocalDateTime.of(2017, 1, 11, 12, 12);
        TestCase.assertEquals(initial.getTime(), date3);
    }

    public void testToString() {
        LocalDateTime date = LocalDateTime.of(2018, 2, 13, 15, 56);
        TimePlace initial = new TimePlace(1, date, "UTM");
        TestCase.assertEquals(initial.toString(), "id: 1, time: 2018-02-13T15:56, place: UTM");
    }
}
