package org.twelve.gateways;

import org.twelve.usecases.ThresholdRepository;

public interface ThresholdsGateway {

    boolean populate(ThresholdRepository thresholdRepository);

    void save(int lendMoreThanBorrow, int maxIncompleteTrade, int maxWeeklyTrade, int numberOfDays, int numberOfEdits,
              int numberOfStats);
}
