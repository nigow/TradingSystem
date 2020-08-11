package org.twelve.gateways.ram;

import org.twelve.entities.Account;
import org.twelve.entities.Permissions;
import org.twelve.gateways.AccountGateway;
import org.twelve.usecases.AccountRepository;

import java.util.*;

public class InMemoryAccountGateway implements AccountGateway {

    /**
     * pseudo-external storage of accounts
     */
    //private final Map<Integer, Account> accountMap;

    /**
     * Initialize the gateway
     */
    public InMemoryAccountGateway(Map<Integer, String> accounts) {
        this.accounts = accounts;

    }

    private Map<Integer, String> accounts;
    // String separated by <bLaq2MF3WsRYdC4zkI56mGnXChO6k9eP9QjTnY1E>

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean populate(AccountRepository accountRepository) {
        System.out.println("Testing");
        for(Integer accountId: accounts.keySet()){
            String[] account = accounts.get(accountId).split("<bLaq2MF3WsRYdC4zkI56mGnXChO6k9eP9QjTnY1E>");
            String username = account[0];
            String password = account[1];
            String[] wishlistString = account[2].split(" ");
            List<Integer> wishlist = new ArrayList<>();
            for(String itemId: wishlistString){
                wishlist.add(Integer.parseInt(itemId));
            }
            List<String> perms = new ArrayList<>(Arrays.asList(account[3].split(" ")));
            String city = account[4];
            accountRepository.createAccount(accountId, username,password,perms,wishlist,city);

        }
//        for(Account account: accountMap.values()){
//            List<String> permissions = new ArrayList<>();
//            for(Permissions permission: account.getPermissions()){
//                permissions.add(permission.toString());
//            }
//            accountRepository.createAccount(account.getAccountID(), account.getUsername(),account.getPassword(),permissions,account.getWishlist(), account.getLocation());
//        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(int accountId, String username, String password, List<Integer> wishlist,
                        List<String> permissions, String location, boolean newAccount) {
//        List<Permissions> permissionNames = new ArrayList<>();
//        for (Permissions permission: Permissions.values()) {
//            if (permissions.contains(permission.name())) {
//                permissionNames.add(permission);
//            }
//        }
//        Account account = new Account(username, password, wishlist, permissionNames, accountId, location);
//        accountMap.put(account.getAccountID(), account);
        String value = "";
        value += username + "<bLaq2MF3WsRYdC4zkI56mGnXChO6k9eP9QjTnY1E>";
        value += password + "<bLaq2MF3WsRYdC4zkI56mGnXChO6k9eP9QjTnY1E>";
        if(wishlist.size() == 0){
            value += " " + "<bLaq2MF3WsRYdC4zkI56mGnXChO6k9eP9QjTnY1E>";
        }
        else{
            for(int wishlistId: wishlist) {
                value += String.valueOf(wishlistId) + " ";
            }
            value = value.substring(0, value.length()-1);
            value += "<bLaq2MF3WsRYdC4zkI56mGnXChO6k9eP9QjTnY1E>";
        }
        if(permissions.size() == 0){
            value += " ";
        }
        else{
            for(String permission: permissions){
                value += permission + " ";
            }
            value = value.substring(0, value.length()-1);
        }
        value += "<bLaq2MF3WsRYdC4zkI56mGnXChO6k9eP9QjTnY1E>";
        value += location;
        accounts.put(accountId, value);
        return true;
    }
}
