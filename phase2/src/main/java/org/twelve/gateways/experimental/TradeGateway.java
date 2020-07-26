package org.twelve.gateways.experimental;

import org.twelve.usecases.TradeUtility;

import java.util.List;

public interface TradeGateway {

    void populate(TradeUtility tradeUtility);

    // needs storage spec update
    void save(int tradeId, boolean isPermanent, List<Integer> traderIds, List<Integer> itemIds, int editedCounter,
              String tradeStatus, List<Boolean> tradeCompletions, String time, String location);

}
