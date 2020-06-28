package gateways;

/**
 * A gateway for interacting with the persistent storage of roles.
 */
public interface RoleGateway {

    /**
     * Given a role's ID, return the corresponding Role object.
     * @param id ID of desired role.
     * @return Role possessing the given ID (null if an invalid ID was given).
     */
    Role findById(Roles id);

}
