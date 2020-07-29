package org.twelve.gateways;

import org.twelve.entities.Thresholds;
import org.twelve.usecases.ThresholdRepository;

public class InMemoryThresholdsGateway implements ThresholdsGateway {
    public final Thresholds thresholds;

    public InMemoryThresholdsGateway(Thresholds thresholds) {
        this.thresholds = thresholds;
    }
    @Override
    public boolean populate(ThresholdRepository thresholdRepository) {
        thresholdRepository.createThresholds(thresholds.getLendMoreThanBorrow(), thresholds.getMaxIncompleteTrade(),
                thresholds.getLendMoreThanBorrow(), thresholds.getNumberOfDays(), thresholds.getNumberOfEdits(),
                thresholds.getNumberOfStats());
        return true;
    }

    @Override
    public boolean save(int lendMoreThanBorrow, int maxIncompleteTrade, int maxWeeklyTrade, int numberOfDays, int numberOfEdits, int numberOfStats) {
        thresholds.setLendMoreThanBorrow(lendMoreThanBorrow);
        thresholds.setMaxIncompleteTrade(maxIncompleteTrade);
        thresholds.setMaxWeeklyTrade(maxWeeklyTrade);
        thresholds.setNumberOfDays(numberOfDays);
        thresholds.setNumberOfEdits(numberOfEdits);
        thresholds.setNumberOfStats(numberOfStats);
        return true;
    }
}
