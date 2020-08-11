package org.twelve.gateways.ram;

import org.twelve.gateways.AccountGateway;
import org.twelve.usecases.AccountRepository;

import java.util.*;

/**
 * pseudo-external storage of accounts and gateway
 * @author Tairi
 */
public class InMemoryAccountGateway implements AccountGateway {

//    /**
//     * pseudo-external storage of accounts
//     */
    //private final Map<Integer, Account> accountMap;

    /**
     * Initialize the external storage
     * @param accounts pseudo-external storage of accounts
     */
    public InMemoryAccountGateway(Map<Integer, String> accounts) {
        this.accounts = accounts;

    }

//    Account properties are stored as a String representation.
//    String separated by a random string <bLaq2MF3WsRYdC4zkI56mGnXChO6k9eP9QjTnY1E>
//    example:
//    1: "demouser<bLaq2MF3WsRYdC4zkI56mGnXChO6k9eP9QjTnY1E>hogehoge<bLaq2MF3WsRYdC4zkI56mGnXChO6k9eP9QjTnY1E> <bLaq2MF3WsRYdC4zkI56mGnXChO6k9eP9QjTnY1E>LOGIN ADD_TO_WISHLIST BROWSE_INVENTORY<bLaq2MF3WsRYdC4zkI56mGnXChO6k9eP9QjTnY1E>utm"
    private final Map<Integer, String> accounts;


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean populate(AccountRepository accountRepository) {
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
        StringBuilder value = new StringBuilder();
        value.append(username).append("<bLaq2MF3WsRYdC4zkI56mGnXChO6k9eP9QjTnY1E>");
        value.append(password).append("<bLaq2MF3WsRYdC4zkI56mGnXChO6k9eP9QjTnY1E>");
        if(wishlist.size() == 0){
            value.append(" " + "<bLaq2MF3WsRYdC4zkI56mGnXChO6k9eP9QjTnY1E>");
        }
        else{
            for(int wishlistId: wishlist) value.append(wishlistId).append(" ");
            value = new StringBuilder(value.substring(0, value.length() - 1));
            value.append("<bLaq2MF3WsRYdC4zkI56mGnXChO6k9eP9QjTnY1E>");
        }
        if(permissions.size() == 0){
            value.append(" ");
        }
        else{
            for(String permission: permissions) {
                value.append(permission).append(" ");
            }
            value = new StringBuilder(value.substring(0, value.length() - 1));
        }
        value.append("<bLaq2MF3WsRYdC4zkI56mGnXChO6k9eP9QjTnY1E>");
        value.append(location);
        accounts.put(accountId, value.toString());
        System.out.print(value.toString());
        return true;
    }
}
