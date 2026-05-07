package service;

import java.sql.Connection;
import java.sql.DriverManager;

public class SupabaseDatabase {
    public static Connection connect(String url, String user, String password) throws Exception{
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public static Connection connect() throws Exception{
        return connect(doten.get(DB_URL), doten.get(DB_USER), doten.get(DB_PASSWORD);
    }
}

