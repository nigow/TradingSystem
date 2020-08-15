package org.twelve.gateways;

import org.twelve.entities.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteItemsGateway {

    private final String DBUri;

    public SQLiteItemsGateway(String DBUri){
        this.DBUri = DBUri;
    }

    private boolean updateTable(String query){
        Connection connection = null;
        Statement statement = null;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBUri);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.commit();

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }finally{
            try{
                if(statement!=null) statement.close();
                if(connection!=null) connection.close();
            }catch(SQLException e){
                System.out.println("Can't close");
                return false;
            }finally{
                return true;
            }
        }
    }


    public Item findById(int id) {
        Connection connection = null;
        ResultSet resultSet = null;
        Item item = null;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBUri);

            resultSet = connection.createStatement().executeQuery("SELECT * FROM items WHERE item_id = " + Integer.toString(id));

            while(resultSet.next()){
                item = new Item(id, resultSet.getString(2), resultSet.getString(3),
                        Integer.parseInt(resultSet.getString(5)));
                if (resultSet.getString(4).equals("true")) item.approve();
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
                return item;
            }

        }
    }

    public boolean updateItem(Item item) {

        String item_id = Integer.toString(item.getItemID());
        String name = item.getName();
        String description = item.getDescription();
        String is_approved = Boolean.toString(item.isApproved());
        String owner_id = Integer.toString(item.getOwnerID());
        String query = "INSERT INTO items VALUES(" + item_id + ", \'" + name + "\', \'" + description + "\', \'" + is_approved + "\', " + owner_id + ");";
        System.out.println(query);

        return updateTable(query);
    }

    public List<Item> getAllItems() {
        Connection connection = null;
        ResultSet resultSet = null;
        Item item = null;
        List<Item> items = new ArrayList<>();
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBUri);
            resultSet = connection.createStatement().executeQuery("SELECT * FROM items");
            while(resultSet.next()){
                item = new Item(Integer.parseInt(resultSet.getString(1)), resultSet.getString(2),
                        resultSet.getString(3), Integer.parseInt(resultSet.getString(5)));
                if (resultSet.getString(4).equals("true")) item.approve();
                items.add(item);
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
                return items;
            }

        }
    }

    public int generateValidId() {
        return getAllItems().size();
    }

    public boolean deleteItem(Item item) {
        String query = "DELETE FROM items WHERE item_id = " + Integer.toString(item.getItemID());
        return updateTable(query);
    }

}
