package es.vives.infrastructure.persistence.memory;

import es.vives.domain.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemoryUsuarioRepositoryTest {

    private MemoryUsuarioRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MemoryUsuarioRepository();
    }

    @Test
    void save_ShouldSaveUsuario() {
        Usuario usuario = new Usuario(UUID.randomUUID(), "Juan", "juan@test.com", "pass", "admin");
        Usuario savedUsuario = repository.save(usuario);

        assertNotNull(savedUsuario);
        assertEquals("Juan", savedUsuario.getNombre());
    }

    @Test
    void findById_ShouldReturnUsuario() {
        Usuario usuario = new Usuario(UUID.randomUUID(), "Ana", "ana@test.com", "pass", "miembro");
        repository.save(usuario);

        Optional<Usuario> foundUsuario = repository.findById(usuario.getId());

        assertTrue(foundUsuario.isPresent());
        assertEquals("Ana", foundUsuario.get().getNombre());
    }
}
