package org.twelve.gateways;

import org.twelve.entities.Account;
import org.twelve.entities.Permissions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteAccountGateway {

    private final String DBUri;

    public SQLiteAccountGateway(String DBUri){
        this.DBUri = DBUri;
    }


    private Account findByKey(String query){
        Connection connection = null;
        ResultSet resultSet = null;
        Account account = null;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBUri);

            resultSet = connection.createStatement().executeQuery(query);

            while(resultSet.next()){
                String username = resultSet.getString(2);
                String password = resultSet.getString(3);
                List<Integer> wishlist = new ArrayList<>();
                for(String id: resultSet.getString(4).split(" ")) wishlist.add(Integer.parseInt(id));
                List<Permissions> permissions = new ArrayList<>();
                for(String permission: resultSet.getString(5).split(" ")) permissions.add(Permissions.valueOf(permission));
                int account_id = Integer.parseInt(resultSet.getString(2));

                account = new Account(username, password, wishlist, permissions, account_id, "placeholder");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(connection!=null) connection.close();
                if(resultSet!=null) resultSet.close();
            }catch(SQLException e){
                System.out.println("Can't close");
            }finally{
                return account;
            }
        }
    }

    public Account findById(int id) {
        String query = "SELECT * FROM accounts WHERE id = " + Integer.toString(id) + ";";
        return findByKey(query);
    }

    public Account findByUsername(String username) {
        String query = "SELECT * FROM accounts WHERE username = " + username + ";";
        return findByKey(query);
    }

    public boolean updateAccount(Account account) {
        String wishlist = "";
        for(int id: account.getWishlist()) wishlist += Integer.toString(id) + " ";
        if(wishlist.length() > 0) wishlist = wishlist.substring(0, wishlist.length()-1);

        String permissions = "";
        for(Permissions permission: account.getPermissions()) permissions += permission.toString() + " ";
        if(permissions.length() > 0) permissions = permissions.substring(0, permissions.length()-1);

        String query = "INSERT INTO accounts VALUES(" + Integer.toString(account.getAccountID()) + ", \'" + account.getUsername() +
                "\', " + wishlist + ", " + permissions + ");";

        return new SQLiteGatewayHelper(DBUri).updateTable(query);
    }

    public List<Account> getAllAccounts() {
        Connection connection = null;
        ResultSet resultSet = null;
        Account account = null;
        List<Account> accounts = new ArrayList<>();
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBUri);

            resultSet = connection.createStatement().executeQuery("SELECT * FROM accounts");

            while(resultSet.next()){
                String username = resultSet.getString(2);
                String password = resultSet.getString(3);
                List<Integer> wishlist = new ArrayList<>();
                for(String id: resultSet.getString(4).split(" ")) wishlist.add(Integer.parseInt(id));
                List<Permissions> permissions = new ArrayList<>();
                for(String permission: resultSet.getString(5).split(" ")) permissions.add(Permissions.valueOf(permission));
                int account_id = Integer.parseInt(resultSet.getString(2));

                account = new Account(username, password, wishlist, permissions, account_id, "placeholder");
                accounts.add(account);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(connection!=null) connection.close();
                if(resultSet!=null) resultSet.close();
            }catch(SQLException e){
                System.out.println("Can't close");
            }finally{
                return accounts;
            }
        }
    }

    public int generateValidId() {
        return getAllAccounts().size();
    }

}
