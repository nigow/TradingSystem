package gateways;

import entities.Restrictions;

/**
 * A gateway for interacting with the persistent storage of system restrictions.
 */
public interface RestrictionsGateway {

    /**
     * Retrieve current system restrictions object.
     *
     * @return Current system restrictions.
     */
    Restrictions getRestrictions();

    /**
     * Given a system restrictions object, save its information to persistent storage.
     *
     * @param restrictions Restrictions being saved.
     */
    boolean updateRestrictions(Restrictions restrictions);

}
