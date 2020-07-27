package org.twelve.gateways.csv;

import org.twelve.entities.Thresholds;

/**
 * A gateway for interacting with the persistent storage of system restrictions.
 */
public interface ThresholdsGateway {

    /**
     * Retrieve current system restrictions object.
     *
     * @return Current system restrictions.
     */
    Thresholds getThresholds();

    /**
     * Given a system restrictions object, save its information to persistent storage.
     *
     * @param thresholds Restrictions being saved.
     * @return Whether restriction's persistent storage was successfully updated or not
     */
    boolean updateRestrictions(Thresholds thresholds);

}
