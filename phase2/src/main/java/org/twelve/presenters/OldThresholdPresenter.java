package org.twelve.presenters;

import java.util.List;
import java.util.Scanner;

public interface OldThresholdPresenter {
    String displayThresholdOptions(List<String> thresholdOptions);

    String changeLendMoreThanBorrow(int lendMoreThanBorrow);

    String changeMaxIncompleteTrades(int maxIncompleteTrades);

    String changeMaxWeeklyTrades(int weeklyTrades);

    String changeNumberOfDays(int numberOfDays);

    String changeNumberOfEdits(int numberOfEdits);

    String changeNumberOfStats(int numberOfStats);

    void invalidInput();

    void displayChangedThresholds();

    String lendBorrowLimit();

    String maxIncompleteTrades();

    String maxWeeklyTrades();

    String returnToMainMenu();

    String numberOfDays();

    String numberOfEdits();

    String numberOfStats();
}
