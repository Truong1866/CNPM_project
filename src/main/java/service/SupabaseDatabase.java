package service;

import java.sql.Connection;
import java.sql.DriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class SupabaseDatabase {
    private static final Dotenv dotenv = Dotenv.load();
    public static Connection connect(String url, String user, String password) throws Exception{
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public static Connection connect() throws Exception{
        return connect(dotenv.get(DB_URL), dotenv.get(DB_USER), dotenv.get(DB_PASSWORD));
    }
}

