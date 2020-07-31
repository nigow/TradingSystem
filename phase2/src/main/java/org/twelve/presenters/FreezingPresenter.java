package org.twelve.presenters;

import java.util.List;

/**
 * Interface for freezing and unfreezing account.
 *
 * @author Ethan
 */
public interface FreezingPresenter {
    void setAllAccounts(List<String> allAccounts);
    List<String> getAllAccounts();
    void setAllFrozenAccounts(List<String> allFrozenAccounts);
    List<String> getAllFrozenAccounts();
    void setAllBannedAccounts(List<String> allBannedAccounts);
    List<String> getAllBannedAccounts();
    void setSelectedAccountName(String name);
    String getSelectedAccountName();
}