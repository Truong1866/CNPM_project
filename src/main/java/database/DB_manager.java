package database;
import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DB_manager {
    private static SessionFactory factory;
    public static void init() {
        if(factory == null){
            try{
                Dotenv dotenv = Dotenv.load();
                Configuration config = new Configuration().configure("hibernate.cfg.xml");

                config.setProperty("hibernate.connection.url", dotenv.get("DB_URL"));
                config.setProperty("hibernate.connection.username", dotenv.get("DB_USER"));
                config.setProperty("hibernate.connection.password", dotenv.get("DB_PASSWORD"));

                factory = config.buildSessionFactory();
                System.out.println("✅ Đã kết nối Supabase thành công!");
            }catch(Exception e){
                System.err.println("❌ Lỗi khởi tạo Database!");
                e.printStackTrace();
            }
        }
    }
    public static SessionFactory getFactory() {
        if(factory == null){
            init();
        }
        return factory;
    }

    public static void shutdown(){
        factory.close();
    }
}