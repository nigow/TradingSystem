package gateways.experimental;

import usecases.FreezingUtility;

public interface RestrictionsGateway {

    void populate(FreezingUtility freezingUtility);
    void save(int lendMoreThanBorrow, int maxIncompleteTrade, int maxWeeklyTrade);

}
