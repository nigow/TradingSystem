package gateways;

import entities.OldTrade;
import entities.TimePlace;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class CSVOldTradeGatewayTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void findTradeById() throws IOException {

        String tempPath = tempFolder.getRoot().getAbsolutePath() + "\\temp.csv";

        TradeGateway tg = new CSVTradeGateway(tempPath);

        int id = tg.generateValidId();

        OldTrade oldTrade = new OldTrade(id, id, true, 0, 1, new ArrayList<>(Arrays.asList(0)),
                                new ArrayList<>(Arrays.asList(1)), 0);

        TimePlace timePlace = new TimePlace(id, LocalDateTime.now(), "skrgk");

        tg.updateTrade(oldTrade, timePlace);

        assertEquals(tg.findTradeById(id).getId(), id);
        assertTrue(tg.findTradeById(id).isPermanent());
        assertEquals(tg.findTradeById(id).getTraderOneID(), 0);
        assertEquals(tg.findTradeById(id).getTraderTwoID(), 1);
        assertEquals(tg.findTradeById(id).getItemOneIDs(), new ArrayList<>(Arrays.asList(0)));
        assertEquals(tg.findTradeById(id).getItemTwoIDs(), new ArrayList<>(Arrays.asList(1)));
        assertEquals(tg.findTradeById(id).getEditedCounter(), 0);

    }

    @Test
    public void findTimePlaceById() throws IOException {

        String tempPath = tempFolder.getRoot().getAbsolutePath() + "\\temp.csv";

        TradeGateway tg = new CSVTradeGateway(tempPath);

        int id = tg.generateValidId();

        OldTrade oldTrade = new OldTrade(id, id, true, 0, 1, new ArrayList<>(Arrays.asList(0)),
                new ArrayList<>(Arrays.asList(1)), 0);

        TimePlace timePlace = new TimePlace(id, LocalDateTime.now(), "skrgk");

        LocalDateTime time = timePlace.getTime();

        tg.updateTrade(oldTrade, timePlace);

        assertEquals(tg.findTimePlaceById(id).getTime(), time);
        assertEquals(tg.findTimePlaceById(id).getPlace(), "skrgk");

    }

    @Test
    public void updateTrade() throws IOException {

        String tempPath = tempFolder.getRoot().getAbsolutePath() + "\\temp.csv";

        TradeGateway tg = new CSVTradeGateway(tempPath);

        int id = tg.generateValidId();

        OldTrade oldTrade = new OldTrade(id, id, true, 0, 1, new ArrayList<>(Arrays.asList(0)),
                new ArrayList<>(Arrays.asList(1)), 0);

        TimePlace timePlace = new TimePlace(id, LocalDateTime.now(), "skrgk");

        tg.updateTrade(oldTrade, timePlace);

        OldTrade retrieved = tg.findTradeById(id);

        retrieved.setPermanent(false);

        tg.updateTrade(retrieved, tg.findTimePlaceById(id));

        assertFalse(tg.findTradeById(id).isPermanent());

    }

    @Test
    public void getAllTrades() throws IOException {

        String tempPath = tempFolder.getRoot().getAbsolutePath() + "\\temp.csv";

        TradeGateway tg = new CSVTradeGateway(tempPath);

        int id = tg.generateValidId();

        OldTrade oldTrade = new OldTrade(id, id, true, 0, 1, new ArrayList<>(Arrays.asList(0)),
                new ArrayList<>(Arrays.asList(1)), 0);

        TimePlace timePlace = new TimePlace(id, LocalDateTime.now(), "skrgk");

        tg.updateTrade(oldTrade, timePlace);

        assertTrue(tg.getAllTrades().get(0).isPermanent());

    }

    @Test
    public void generateValidId() throws IOException {

        String tempPath = tempFolder.getRoot().getAbsolutePath() + "\\temp.csv";

        TradeGateway tg = new CSVTradeGateway(tempPath);

        int id = tg.generateValidId();

        OldTrade oldTrade = new OldTrade(id, id, true, 0, 1, new ArrayList<>(Arrays.asList(0)),
                new ArrayList<>(Arrays.asList(1)), 0);

        TimePlace timePlace = new TimePlace(id, LocalDateTime.now(), "skrgk");

        tg.updateTrade(oldTrade, timePlace);

        assertEquals(tg.generateValidId(), 1);

    }
}