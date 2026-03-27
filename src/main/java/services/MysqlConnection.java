package services;

import java.sql.*;
/**
 *
 * @Thanh
 */
public class MysqlConnection {
    public static Connection getMysqlConnection() throws SQLException, ClassNotFoundException {
        String hostName = "localhost";
        String dbName = "quan_ly_chung_cu";
        String userName = "root";
        String password = "Mafumafu1#";
        return getMysqlConnection(hostName, dbName, userName, password);
    }

    public static Connection getMysqlConnection(String hostName, String dbName, String userName, String password)
            throws SQLException, ClassNotFoundException{
        //Class.forName("com.mysql.jdbc.Driver");
        String connectionUrl = "jdbc:mysql://" + hostName + "/" + dbName;
       // Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(connectionUrl, userName, password);
        return conn;
    }
}