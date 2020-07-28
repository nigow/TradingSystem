package test.java.org.twelve.gateways;

import org.twelve.usecases.TradeManager;

import java.util.List;

public class InMemoryTradeGateway implements org.twelve.gateways.TradeGateway {
    @Override
    public void populate(TradeManager tradeManager) {

    }

    @Override
    public void save(int tradeId, boolean isPermanent, List<Integer> traderIds, List<Integer> itemIds, int editedCounter, String tradeStatus, List<Boolean> tradeCompletions, String time, String location) {

    }
}
