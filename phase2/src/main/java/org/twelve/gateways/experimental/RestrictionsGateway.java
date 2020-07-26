package org.twelve.gateways.experimental;

import org.twelve.usecases.FreezingUtility;

public interface RestrictionsGateway {

    // todo: no parameter until we find out which use case is handling this
    // FreezingUtility :)
    boolean populate(FreezingUtility freezingUtility);

    boolean save(int lendMoreThanBorrow, int maxIncompleteTrade, int maxWeeklyTrade, int numberOfDays, int numberOfEdits,
              int numberOfStats);
}
