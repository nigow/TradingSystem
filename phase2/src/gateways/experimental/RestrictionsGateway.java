package gateways.experimental;

public interface RestrictionsGateway {

    // todo: no parameter until we find out which use case is handling this
    void populate();

    void save(int lendMoreThanBorrow, int maxIncompleteTrade, int maxWeeklyTrade, int numberOfDays, int numberOfEdits,
              int numberOfStats);
}
