package org.twelve.presenters;

import java.util.List;
import java.util.Map;

/**
 * Interface for freezing and unfreezing account.
 *
 * @author Ethan
 */
public interface FreezingPresenter {

    /**
     * Setter for the list of regular accounts
     * @param regularAccounts the list of regular accounts
     */
    void setRegularAccounts(List<String> regularAccounts);

    /**
     * Getter for the list of the regular accounts
     * @return all regular accounts
     */
    List<Map<String, String>> getRegularAccounts();

    /**
     *
     * @param bannedAccounts
     */
    void setBannedAccounts(List<String> bannedAccounts);
    List<Map<String, String>> getBannedAccounts();

    void setFrozenAccounts(List<String> frozenAccounts);
    List<Map<String, String>> getFrozenAccounts();

    void setUnfreezeAccounts(List<String> unfreezeAccounts);
    List<Map<String, String>> getUnfreezeAccounts();

    void setToFreezeAccounts(List<String> toFreezeAccounts);
    List<Map<String, String>> getToFreezeAccounts();

    void setAdminAccounts(List<String> adminAccounts);
    List<Map<String, String>> getAdminAccounts();

    void setTrustedAccounts(List<String> trustedAccounts);
    List<Map<String, String>> getTrustedAccounts();

    void setModAccounts(List<String> modAccounts);
    List<Map<String, String>> getModAccounts();

    void setCanMod(boolean canMod);
    boolean getCanMod();

    void setCanUnmod(boolean canUnmod);
    boolean getCanUnmod();
}