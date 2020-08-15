package org.twelve.gateways;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteGatewayHelper {
    private final String DBUri;

    public SQLiteGatewayHelper(String DBUri){
        this.DBUri = DBUri;
    }

    protected boolean updateTable(String query){
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
}
