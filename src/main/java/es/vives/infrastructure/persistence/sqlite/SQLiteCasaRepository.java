package es.vives.infrastructure.persistence.sqlite;

import es.vives.domain.Casa;
import es.vives.domain.repository.CasaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SQLiteCasaRepository implements CasaRepository {
    private static final Logger logger = LoggerFactory.getLogger(SQLiteCasaRepository.class);
    private final DatabaseManager dbManager = DatabaseManager.getInstance();

    @Override
    public Casa save(Casa entity) {
        String sql = "INSERT INTO Casa (id, nombre, direccion) VALUES (?, ?, ?)";
        try (Connection conn = dbManager.getConnection()) {
            conn.setAutoCommit(false); // Gestión de transacciones conceptual/real
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, entity.getId().toString());
                pstmt.setString(2, entity.getNombre());
                pstmt.setString(3, entity.getDireccion());
                pstmt.executeUpdate();
                conn.commit();
                return entity;
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            logger.error("Error saving Casa", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Casa> findById(UUID id) {
        String sql = "SELECT * FROM Casa WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSetToCasa(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding Casa by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Casa> findAll() {
        List<Casa> casas = new ArrayList<>();
        String sql = "SELECT * FROM Casa";
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                casas.add(mapResultSetToCasa(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all Casas", e);
        }
        return casas;
    }

    @Override
    public Casa update(Casa entity) {
        String sql = "UPDATE Casa SET nombre = ?, direccion = ? WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getNombre());
            pstmt.setString(2, entity.getDireccion());
            pstmt.setString(3, entity.getId().toString());
            pstmt.executeUpdate();
            return entity;
        } catch (SQLException e) {
            logger.error("Error updating Casa", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM Casa WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting Casa", e);
        }
    }

    private Casa mapResultSetToCasa(ResultSet rs) throws SQLException {
        return new Casa(
                UUID.fromString(rs.getString("id")),
                rs.getString("nombre"),
                rs.getString("direccion")
        );
    }
}
