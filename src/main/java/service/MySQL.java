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
        String host = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
        String port = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "3306";
        String name = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "quan_ly_chung_cu";
        String user = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "root";
        String password = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "";
        String url = "jdbc:" + type + "://" + host + ":" + port + "/" + name;
        return connect(url, user, password);
    }
}
