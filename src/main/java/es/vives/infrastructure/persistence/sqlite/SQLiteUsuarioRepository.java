package es.vives.infrastructure.persistence.sqlite;

import es.vives.domain.Usuario;
import es.vives.domain.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SQLiteUsuarioRepository implements UsuarioRepository {
    private static final Logger logger = LoggerFactory.getLogger(SQLiteUsuarioRepository.class);
    private final DatabaseManager dbManager = DatabaseManager.getInstance();

    @Override
    public Usuario save(Usuario entity) {
        String sql = "INSERT INTO Usuario (id, nombre, email, password, rol) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getId().toString());
            pstmt.setString(2, entity.getNombre());
            pstmt.setString(3, entity.getEmail());
            pstmt.setString(4, entity.getPassword());
            pstmt.setString(5, entity.getRol());
            pstmt.executeUpdate();
            return entity;
        } catch (SQLException e) {
            logger.error("Error saving Usuario", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        String sql = "SELECT * FROM Usuario WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSetToUsuario(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding Usuario by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(mapResultSetToUsuario(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all Usuarios", e);
        }
        return usuarios;
    }

    @Override
    public Usuario update(Usuario entity) {
        String sql = "UPDATE Usuario SET nombre = ?, email = ?, password = ?, rol = ? WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getNombre());
            pstmt.setString(2, entity.getEmail());
            pstmt.setString(3, entity.getPassword());
            pstmt.setString(4, entity.getRol());
            pstmt.setString(5, entity.getId().toString());
            pstmt.executeUpdate();
            return entity;
        } catch (SQLException e) {
            logger.error("Error updating Usuario", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM Usuario WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting Usuario", e);
        }
    }

    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                UUID.fromString(rs.getString("id")),
                rs.getString("nombre"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("rol")
        );
    }
}
