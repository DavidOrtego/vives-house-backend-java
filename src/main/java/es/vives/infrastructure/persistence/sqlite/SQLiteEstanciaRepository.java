package es.vives.infrastructure.persistence.sqlite;

import es.vives.domain.Estancia;
import es.vives.domain.repository.EstanciaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SQLiteEstanciaRepository implements EstanciaRepository {
    private static final Logger logger = LoggerFactory.getLogger(SQLiteEstanciaRepository.class);
    private final DatabaseManager dbManager = DatabaseManager.getInstance();

    @Override
    public Estancia save(Estancia entity) {
        String sql = "INSERT INTO Estancia (id, id_casa, id_usuario, fecha_entrada, fecha_salida) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getId().toString());
            pstmt.setString(2, entity.getIdCasa().toString());
            pstmt.setString(3, entity.getIdUsuario().toString());
            pstmt.setString(4, entity.getFechaEntrada().toString());
            pstmt.setString(5, entity.getFechaSalida() != null ? entity.getFechaSalida().toString() : null);
            pstmt.executeUpdate();
            return entity;
        } catch (SQLException e) {
            logger.error("Error saving Estancia", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Estancia> findById(UUID id) {
        String sql = "SELECT * FROM Estancia WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSetToEstancia(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding Estancia by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Estancia> findAll() {
        List<Estancia> estancias = new ArrayList<>();
        String sql = "SELECT * FROM Estancia";
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                estancias.add(mapResultSetToEstancia(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all Estancias", e);
        }
        return estancias;
    }

    @Override
    public Estancia update(Estancia entity) {
        String sql = "UPDATE Estancia SET id_casa = ?, id_usuario = ?, fecha_entrada = ?, fecha_salida = ? WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getIdCasa().toString());
            pstmt.setString(2, entity.getIdUsuario().toString());
            pstmt.setString(3, entity.getFechaEntrada().toString());
            pstmt.setString(4, entity.getFechaSalida() != null ? entity.getFechaSalida().toString() : null);
            pstmt.setString(5, entity.getId().toString());
            pstmt.executeUpdate();
            return entity;
        } catch (SQLException e) {
            logger.error("Error updating Estancia", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM Estancia WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting Estancia", e);
        }
    }

    @Override
    public List<Estancia> findByCasaId(UUID idCasa) {
        List<Estancia> estancias = new ArrayList<>();
        String sql = "SELECT * FROM Estancia WHERE id_casa = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idCasa.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                estancias.add(mapResultSetToEstancia(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding Estancias by casa id", e);
        }
        return estancias;
    }

    @Override
    public List<Estancia> findByUsuarioId(UUID idUsuario) {
        List<Estancia> estancias = new ArrayList<>();
        String sql = "SELECT * FROM Estancia WHERE id_usuario = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idUsuario.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                estancias.add(mapResultSetToEstancia(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding Estancias by usuario id", e);
        }
        return estancias;
    }

    private Estancia mapResultSetToEstancia(ResultSet rs) throws SQLException {
        String fechaSalidaStr = rs.getString("fecha_salida");
        return new Estancia(
                UUID.fromString(rs.getString("id")),
                UUID.fromString(rs.getString("id_casa")),
                UUID.fromString(rs.getString("id_usuario")),
                LocalDate.parse(rs.getString("fecha_entrada")),
                fechaSalidaStr != null ? LocalDate.parse(fechaSalidaStr) : null
        );
    }
}
