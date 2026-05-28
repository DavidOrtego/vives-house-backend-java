package es.vives.infrastructure.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class RepositoryFactoryProvider {
    private static final Logger logger = LoggerFactory.getLogger(RepositoryFactoryProvider.class);
    private static RepositoryFactory factory;

    public static synchronized RepositoryFactory getFactory() {
        if (factory == null) {
            Properties properties = new Properties();
            try (InputStream input = RepositoryFactoryProvider.class.getClassLoader().getResourceAsStream("application.properties")) {
                if (input == null) {
                    logger.warn("application.properties not found. Defaulting to MemoryRepositoryFactory.");
                    factory = MemoryRepositoryFactory.getInstance();
                    return factory;
                }
                properties.load(input);
                String dbType = properties.getProperty("db.type", "memory");
                
                if ("sqlite".equalsIgnoreCase(dbType)) {
                    logger.info("Using SQLiteRepositoryFactory");
                    factory = SQLiteRepositoryFactory.getInstance();
                } else {
                    logger.info("Using MemoryRepositoryFactory");
                    factory = MemoryRepositoryFactory.getInstance();
                }
            } catch (Exception ex) {
                logger.error("Error loading properties. Defaulting to MemoryRepositoryFactory.", ex);
                factory = MemoryRepositoryFactory.getInstance();
            }
        }
        return factory;
    }
}
