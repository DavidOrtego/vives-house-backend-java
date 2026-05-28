package es.vives.infrastructure.persistence.sqlite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
    private static final String URL = "jdbc:sqlite:vives_house.db";
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        try {
            connection = DriverManager.getConnection(URL);
            logger.info("Connected to SQLite database: {}", URL);
            initializeDatabase();
        } catch (SQLException e) {
            logger.error("Error connecting to SQLite database", e);
            throw new RuntimeException("Error initializing database", e);
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
            }
        } catch (SQLException e) {
            logger.error("Error checking connection status", e);
        }
        return connection;
    }

    private void initializeDatabase() {
        try (Statement stmt = connection.createStatement()) {
            // Create Casa table
            stmt.execute("CREATE TABLE IF NOT EXISTS Casa (" +
                    "id TEXT PRIMARY KEY, " +
                    "nombre TEXT NOT NULL, " +
                    "direccion TEXT NOT NULL)");

            // Create Usuario table
            stmt.execute("CREATE TABLE IF NOT EXISTS Usuario (" +
                    "id TEXT PRIMARY KEY, " +
                    "nombre TEXT NOT NULL, " +
                    "email TEXT NOT NULL, " +
                    "password TEXT NOT NULL, " +
                    "rol TEXT NOT NULL)");

            // Create Estancia table
            stmt.execute("CREATE TABLE IF NOT EXISTS Estancia (" +
                    "id TEXT PRIMARY KEY, " +
                    "id_casa TEXT NOT NULL, " +
                    "id_usuario TEXT NOT NULL, " +
                    "fecha_entrada TEXT NOT NULL, " +
                    "fecha_salida TEXT, " +
                    "FOREIGN KEY(id_casa) REFERENCES Casa(id), " +
                    "FOREIGN KEY(id_usuario) REFERENCES Usuario(id))");

            // Create Gasto table
            stmt.execute("CREATE TABLE IF NOT EXISTS Gasto (" +
                    "id TEXT PRIMARY KEY, " +
                    "descripcion TEXT NOT NULL, " +
                    "cantidad REAL NOT NULL, " +
                    "fecha TEXT NOT NULL, " +
                    "id_casa TEXT NOT NULL, " +
                    "id_usuario TEXT NOT NULL, " +
                    "FOREIGN KEY(id_casa) REFERENCES Casa(id), " +
                    "FOREIGN KEY(id_usuario) REFERENCES Usuario(id))");

            // Create Tarea table
            stmt.execute("CREATE TABLE IF NOT EXISTS Tarea (" +
                    "id TEXT PRIMARY KEY, " +
                    "descripcion TEXT NOT NULL, " +
                    "estado TEXT NOT NULL, " +
                    "id_casa TEXT NOT NULL, " +
                    "id_usuario_asignado TEXT, " +
                    "FOREIGN KEY(id_casa) REFERENCES Casa(id), " +
                    "FOREIGN KEY(id_usuario_asignado) REFERENCES Usuario(id))");

            logger.info("Database schema initialized successfully.");
        } catch (SQLException e) {
            logger.error("Error initializing database schema", e);
        }
    }
}
