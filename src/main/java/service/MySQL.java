package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLTimeoutException;

public class MySQL {
    public static Connection connect(String url, String user, String password) throws Exception{
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public static Connection connect() throws Exception{
        String type = "mysql";
        String host = "localhost";
        String port = "3306";
        String name = "quan_ly_chung_cu";
        String url = "jdbc:" + type + "://" + host + ":" + port + "/" + name;
        String user = "root";
        String password = "Mafumafu1#";
        return connect(url, user, password);
    }
}
