package es.vives.infrastructure.persistence.sqlite;

import es.vives.domain.Gasto;
import es.vives.domain.repository.GastoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SQLiteGastoRepository implements GastoRepository {
    private static final Logger logger = LoggerFactory.getLogger(SQLiteGastoRepository.class);
    private final DatabaseManager dbManager = DatabaseManager.getInstance();

    @Override
    public Gasto save(Gasto entity) {
        String sql = "INSERT INTO Gasto (id, descripcion, cantidad, fecha, id_casa, id_usuario) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getId().toString());
            pstmt.setString(2, entity.getDescripcion());
            pstmt.setDouble(3, entity.getCantidad());
            pstmt.setString(4, entity.getFecha().toString());
            pstmt.setString(5, entity.getIdCasa().toString());
            pstmt.setString(6, entity.getIdUsuario().toString());
            pstmt.executeUpdate();
            return entity;
        } catch (SQLException e) {
            logger.error("Error saving Gasto", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Gasto> findById(UUID id) {
        String sql = "SELECT * FROM Gasto WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSetToGasto(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding Gasto by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Gasto> findAll() {
        List<Gasto> gastos = new ArrayList<>();
        String sql = "SELECT * FROM Gasto";
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                gastos.add(mapResultSetToGasto(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all Gastos", e);
        }
        return gastos;
    }

    @Override
    public Gasto update(Gasto entity) {
        String sql = "UPDATE Gasto SET descripcion = ?, cantidad = ?, fecha = ?, id_casa = ?, id_usuario = ? WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getDescripcion());
            pstmt.setDouble(2, entity.getCantidad());
            pstmt.setString(3, entity.getFecha().toString());
            pstmt.setString(4, entity.getIdCasa().toString());
            pstmt.setString(5, entity.getIdUsuario().toString());
            pstmt.setString(6, entity.getId().toString());
            pstmt.executeUpdate();
            return entity;
        } catch (SQLException e) {
            logger.error("Error updating Gasto", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM Gasto WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting Gasto", e);
        }
    }

    @Override
    public List<Gasto> findByCasaId(UUID idCasa) {
        List<Gasto> gastos = new ArrayList<>();
        String sql = "SELECT * FROM Gasto WHERE id_casa = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idCasa.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                gastos.add(mapResultSetToGasto(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding Gastos by casa id", e);
        }
        return gastos;
    }

    @Override
    public List<Gasto> findByUsuarioId(UUID idUsuario) {
        List<Gasto> gastos = new ArrayList<>();
        String sql = "SELECT * FROM Gasto WHERE id_usuario = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idUsuario.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                gastos.add(mapResultSetToGasto(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding Gastos by usuario id", e);
        }
        return gastos;
    }

    private Gasto mapResultSetToGasto(ResultSet rs) throws SQLException {
        return new Gasto(
                UUID.fromString(rs.getString("id")),
                rs.getString("descripcion"),
                rs.getDouble("cantidad"),
                LocalDate.parse(rs.getString("fecha")),
                UUID.fromString(rs.getString("id_casa")),
                UUID.fromString(rs.getString("id_usuario"))
        );
    }
}
