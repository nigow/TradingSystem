package gateways;

import entities.Permissions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * A role gateway that uses csv files as persistent storage.
 */
public class CSVRoleGateway implements RoleGateway{
    /**
     * A HashMap read from the CSV file that maps Roles against list of permissions
     */
    private final HashMap<Roles, ArrayList<Permissions>> map;


    /**
     * Constructor for CSVRoleGateway that construct map
     * @param filepath the filepath to the csv file
     */
    public CSVRoleGateway(String filepath) throws IOException{

        this.map = new HashMap<>();

        //pre-processing of file reading
        File f = new File(filepath);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();

        line = br.readLine(); //skip the first row

        while(line != null){
            String[] lineArray = line.split(","); //read the csv line "RoleID,permissionsSPACEpermissionSPACE..."
            Roles id = Roles.valueOf(lineArray[0]);
            ArrayList<Permissions> permissions = new ArrayList<>();

            //STG ONLY: if no permission is given to a role, hit a space for the permissions column
            for(String item: lineArray[1].split(" ")){
                permissions.add(Permissions.valueOf(item));
            }

            map.put(id, permissions);
            line = br.readLine();
        }

        br.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role findById(Roles id) {
        if(map.containsKey(id)){
            return new Role(id, map.get(id));
        }
        return null;

    }
}
