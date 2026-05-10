package database;
import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DB_manager {
    private static SessionFactory factory;
    private static final Logger logger =  LoggerFactory.getLogger(DB_manager.class);
    public static void init() {
        if(factory == null){
            try{
                Dotenv dotenv = Dotenv.load();
                Configuration config = new Configuration().configure("hibernate.cfg.xml");

                config.setProperty("hibernate.connection.url", dotenv.get("DB_URL"));
                config.setProperty("hibernate.connection.username", dotenv.get("DB_USER"));
                config.setProperty("hibernate.connection.password", dotenv.get("DB_PASSWORD"));

                factory = config.buildSessionFactory();
                logger.info("Successfully connected to database.");
            }catch(Exception e){
                logger.error("Cannot connect to database : {}",e.getMessage(),e);
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