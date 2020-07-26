package usecases;

import entities.Account;
import entities.Permissions;
import gateways.AccountGateway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Builder for creating different types of accounts.
 *
 * @author Andrew
 */

//TODO what it would look like if following build pattern but notice how simple it is? I feel like this class is unnecessary.
public class AccountBuilder {

    private String username;
    private String password;
    private int accountID;
    private List<Permissions> perms;
    private Account account;

    public void buildCredentials(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void buildPermissions(List<Permissions> perms){
        this.perms = perms;
    }

    public void buildID(int accountID){
        this.accountID = accountID;
    }

    public Account buildAccount(){
        account = new Account(username, password, perms, accountID);
        return account;
    }

}
