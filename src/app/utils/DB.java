package app.utils;

import java.sql.*;

public final class DB {
    private static Connection connection;
    private DB(){}

     private static Connection getConnection() throws SQLException {
        if(connection == null){

            connection =  DriverManager.getConnection("jdbc:mysql://localhost/demo","root","mehak3021");
        }
        return connection;
    }

    public static ResultSet executeQuery(String query, Boolean update) throws SQLException{
         Connection connection = getConnection();
        Statement st = connection.createStatement();
        if(update){
            st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            return st.getGeneratedKeys();
        }else {
            return st.executeQuery(query);
        }
    }


}
