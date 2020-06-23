package entities;

import java.util.List;

/**
 * Stores permissions for a specific Role entity.
 */
public class Role {

    /**
     * The ID of this role
     */
    private final String id;
    /**
     * A list of permissions for this role.
     */
    private final List<String> permissions;

    /**
     * @param id The Role's id.
     * @param permissions The permissions for this role.
     */
    public Role(String id, List<String> permissions) {
        this.id = id;
        this.permissions = permissions;
    }

    /**
     * @param action The action a user is interested in performed.
     * @return Whether the user can return this action.
     */
    public boolean contains(String action) {
        return permissions.contains(action);
    }


    /**
     * @return The Role's id.
     */
    public String getId() {
        return id;
    }
}
