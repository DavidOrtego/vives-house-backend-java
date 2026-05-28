package es.vives.infrastructure.persistence.sqlite;

import es.vives.domain.Tarea;
import es.vives.domain.repository.TareaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SQLiteTareaRepository implements TareaRepository {
    private static final Logger logger = LoggerFactory.getLogger(SQLiteTareaRepository.class);
    private final DatabaseManager dbManager = DatabaseManager.getInstance();

    @Override
    public Tarea save(Tarea entity) {
        String sql = "INSERT INTO Tarea (id, descripcion, estado, id_casa, id_usuario_asignado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getId().toString());
            pstmt.setString(2, entity.getDescripcion());
            pstmt.setString(3, entity.getEstado());
            pstmt.setString(4, entity.getIdCasa().toString());
            pstmt.setString(5, entity.getIdUsuarioAsignado() != null ? entity.getIdUsuarioAsignado().toString() : null);
            pstmt.executeUpdate();
            return entity;
        } catch (SQLException e) {
            logger.error("Error saving Tarea", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Tarea> findById(UUID id) {
        String sql = "SELECT * FROM Tarea WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSetToTarea(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding Tarea by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Tarea> findAll() {
        List<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT * FROM Tarea";
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tareas.add(mapResultSetToTarea(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all Tareas", e);
        }
        return tareas;
    }

    @Override
    public Tarea update(Tarea entity) {
        String sql = "UPDATE Tarea SET descripcion = ?, estado = ?, id_casa = ?, id_usuario_asignado = ? WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getDescripcion());
            pstmt.setString(2, entity.getEstado());
            pstmt.setString(3, entity.getIdCasa().toString());
            pstmt.setString(4, entity.getIdUsuarioAsignado() != null ? entity.getIdUsuarioAsignado().toString() : null);
            pstmt.setString(5, entity.getId().toString());
            pstmt.executeUpdate();
            return entity;
        } catch (SQLException e) {
            logger.error("Error updating Tarea", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM Tarea WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting Tarea", e);
        }
    }

    @Override
    public List<Tarea> findByCasaId(UUID idCasa) {
        List<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT * FROM Tarea WHERE id_casa = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idCasa.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tareas.add(mapResultSetToTarea(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding Tareas by casa id", e);
        }
        return tareas;
    }

    @Override
    public List<Tarea> findByUsuarioAsignadoId(UUID idUsuarioAsignado) {
        List<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT * FROM Tarea WHERE id_usuario_asignado = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idUsuarioAsignado.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tareas.add(mapResultSetToTarea(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding Tareas by usuario asignado id", e);
        }
        return tareas;
    }

    private Tarea mapResultSetToTarea(ResultSet rs) throws SQLException {
        String idUsuarioAsignadoStr = rs.getString("id_usuario_asignado");
        return new Tarea(
                UUID.fromString(rs.getString("id")),
                rs.getString("descripcion"),
                rs.getString("estado"),
                UUID.fromString(rs.getString("id_casa")),
                idUsuarioAsignadoStr != null ? UUID.fromString(idUsuarioAsignadoStr) : null
        );
    }
}
