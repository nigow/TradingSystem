package org.twelve.presenters;

import java.util.List;
import java.util.Map;

/**
 * Interface for statuses of accounts
 *
 * @author Ethan
 */
public interface FreezingPresenter {

    /**
     * Setter for the list of regular accounts
     *
     * @param regularAccounts the list of regular accounts
     */
    void setRegularAccounts(List<String> regularAccounts);

    /**
     * Getter for the list of the regular accounts
     *
     * @return all regular accounts
     */
    List<Map<String, String>> getRegularAccounts();

    /**
     * Setter for the list of banned accounts
     *
     * @param bannedAccounts the list of banned accounts
     */
    void setBannedAccounts(List<String> bannedAccounts);


    List<Map<String, String>> getBannedAccounts();

    /**
     * Setter for the list of frozen accounts
     *
     * @param frozenAccounts the list of frozen accounts
     */
    void setFrozenAccounts(List<String> frozenAccounts);

    /**
     * Getter for the list of frozen accounts
     *
     * @return the list of frozen accounts
     */
    List<Map<String, String>> getFrozenAccounts();

    /**
     * Setter for the list of accounts that have requested to be unfrozen
     *
     * @param unfreezeAccounts the list of accounts that have requested to be unfrozen
     */
    void setUnfreezeAccounts(List<String> unfreezeAccounts);

    /**
     * Getter for the list of accounts that have requested to be unfrozen
     *
     * @return the list of accounts that have requested to be unfrozen
     */
    List<Map<String, String>> getUnfreezeAccounts();

    /**
     * Setter for the list of accounts that are pending freeze
     *
     * @param toFreezeAccounts the list of accounts that are pending freeze
     */
    void setToFreezeAccounts(List<String> toFreezeAccounts);

    /**
     * Getter for teh list of accounts that are pending freeze
     *
     * @return the list of accounts that are pending freeze
     */
    List<Map<String, String>> getToFreezeAccounts();

    /**
     * Setter for the list of admin accounts
     *
     * @param adminAccounts the list of admin accounts
     */
    void setAdminAccounts(List<String> adminAccounts);

    /**
     * Getter for the list of admin accounts
     *
     * @return the list of admin accounts
     */
    List<Map<String, String>> getAdminAccounts();

    /**
     * Setter for the list of trusted accounts
     *
     * @param trustedAccounts the list of trusted accounts
     */
    void setTrustedAccounts(List<String> trustedAccounts);

    /**
     * Getter for the list of trusted accounts
     *
     * @return the list of trusted accounts
     */
    List<Map<String, String>> getTrustedAccounts();

    /**
     * Setter for the list of moderator accounts
     *
     * @param modAccounts the list of moderator accounts
     */
    void setModAccounts(List<String> modAccounts);

    /**
     * Getter for the list of moderator accounts
     *
     * @return the list of moderator accounts
     */
    List<Map<String, String>> getModAccounts();

    /**
     * Setter for the boolean determining if a user can mod another user
     *
     * @param canMod the boolean determining if a user can mod another user
     */
    void setCanMod(boolean canMod);

    /**
     * Getter for the boolean determining if a user can mod another user
     *
     * @return the boolean determining if a user can mod another user
     */
    boolean getCanMod();

    /**
     * Setter for the boolean determining if a user can unmod another user
     *
     * @param canUnmod the boolean determining if a user can unmod another user
     */
    void setCanUnmod(boolean canUnmod);

    /**
     * Getter for the boolean determining if a user can unmod another user
     *
     * @return the boolean determining if a user can unmod another user
     */
    boolean getCanUnmod();
}