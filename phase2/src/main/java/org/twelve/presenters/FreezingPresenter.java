package org.twelve.presenters;

import java.util.List;
import java.util.Map;

/**
 * Interface for freezing and unfreezing account.
 *
 * @author Ethan
 */
public interface FreezingPresenter {
    // void setAllAccounts(List<String> allAccounts);
    // List<String> getAllAccounts();

    void setBannedAccounts(List<String> allBannedAccounts);
    List<Map<String, String>> getBannedAccounts();

    void setFrozenAccounts(List<String> allFrozenAccounts);
    List<Map<String, String>> getFrozenAccounts();

    void setUnfreezeAccounts(List<String> unfreezeAccounts);
    List<Map<String, String>> getUnfreezeAccounts();

    void setToFreezeAccounts(List<String> toFreezeAccounts);
    List<Map<String, String>> getToFreezeAccounts();

    void setVacationingAccounts(List<String> vacationingAccounts);
    List<Map<String, String>> getVacationingAccounts();

    void setAdminAccounts(List<String> adminAccounts);
    List<Map<String, String>> getAdminAccounts();

}