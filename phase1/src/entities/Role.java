package entities;

import java.util.ArrayList;

/**
 * Stores permissions for a specific Role entity.
 * @author Michael
 */
public class Role {

    /**
     * The ID of this role
     */
    private final Roles id;
    /**
     * A list of permissions for this role.
     */
    private final ArrayList<Permissions> permissions;

    /**
     * @param id The Role's id.
     * @param permissions The permissions for this role.
     */
    public Role(Roles id, ArrayList<Permissions> permissions) {
        this.id = id;
        this.permissions = permissions;
    }

    /**
     * @param action The action a user is interested in performed.
     * @return Whether the user can return this action.
     */
    public boolean contains(Permissions action) {
        return permissions.contains(action);
    }


    /**
     * @return The Role's id.
     */
    public Roles getId() {
        return id;
    }
}
