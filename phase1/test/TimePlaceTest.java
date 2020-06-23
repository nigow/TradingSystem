import entities.TimePlace;
import org.junit.*;
import java.time.LocalDateTime;
import java.sql.Time;

public class TimePlaceTest {

    @Test(timeout = 50)
    public void testTimePlaceExists() {
        TimePlace initial = new TimePlace(1, LocalDateTime.now(), "UTM");
    }
}
