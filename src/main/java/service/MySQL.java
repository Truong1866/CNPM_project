package service;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQL {
    public static Connection connect(String url, String user, String password) throws Exception{
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public static Connection connect() throws Exception{
        // PostgreSQL/Supabase connection
        String type = "postgresql";
        String host = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
        String port = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "5432";
        String name = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "quan_ly_chung_cu";
        String user = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "postgres";
        String password = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "";
        
        // Supabase: jdbc:postgresql://host:port/database?user=user&password=pass&sslmode=require
        String url = "jdbc:" + type + "://" + host + ":" + port + "/" + name + "?sslmode=require";
        return connect(url, user, password);
    }
}
