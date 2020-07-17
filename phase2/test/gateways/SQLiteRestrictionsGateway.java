package gateways;

import entities.Restrictions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteRestrictionsGateway implements RestrictionsGateway{
    private final String DBUri;

    public SQLiteRestrictionsGateway(String DBUri){
        this.DBUri = DBUri;
    }

    //source: https://bit.ly/32skkSn
    //needs to add JDBC driver to the class path: https://bitbucket.org/xerial/sqlite-jdbc/downloads/
    @Override
    public Restrictions getRestrictions() {
        Restrictions restriction = null;
        Connection connection = null;
        ResultSet restrictions = null;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBUri);
            connection.setAutoCommit(false);
            restrictions = connection.createStatement().executeQuery("SELECT * FROM restrictions");

            while(restrictions.next()){
                int lend_limit = Integer.parseInt(restrictions.getString(1));
                int incomplete_limit = Integer.parseInt(restrictions.getString(2));
                int weekly_limit = Integer.parseInt(restrictions.getString(3));

                restriction = new Restrictions(lend_limit, incomplete_limit, weekly_limit);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(connection!=null) connection.close();
                if(restrictions!=null) restrictions.close();
            }catch(SQLException e){
                System.out.println("Can't close");
            }finally{
                return restriction;
            }

        }
    }

    @Override
    public boolean updateRestrictions(Restrictions restrictions) {
        String lendLimit = Integer.toString(restrictions.getLendMoreThanBorrow());
        String incompleteLimit = Integer.toString(restrictions.getMaxIncompleteTrade());
        String weeklyLimit = Integer.toString(restrictions.getMaxWeeklyTrade());
        Restrictions restriction = null;
        Connection connection = null;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBUri);
            connection.setAutoCommit(false);
            connection.createStatement().executeUpdate("DELETE FROM restrictions;");
            connection.createStatement().executeUpdate(
                    "INSERT INTO restrictions VALUES(" + lendLimit + "," + incompleteLimit + "," + weeklyLimit + ");"
            );
            connection.commit();

        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }finally{
            try{
                if(connection!=null) connection.close();
                return true;
            }catch(SQLException e) {
                e.printStackTrace();
                return false;
            }

        }
    }

    public static void main(String[] args) {
        SQLiteRestrictionsGateway dbGateway = new SQLiteRestrictionsGateway("jdbc:sqlite:/Users/tairi/Development/CSC207/group_0012/phase2/test/gateways/testdb.db");
        System.out.println(dbGateway.getRestrictions().getMaxIncompleteTrade());
        System.out.println(dbGateway.updateRestrictions(new Restrictions(15,20,30)));
        System.out.println(dbGateway.getRestrictions().getMaxIncompleteTrade());
    }
}
