package es.vives.application.service;

import es.vives.application.dto.UsuarioDTO;
import es.vives.domain.Usuario;
import es.vives.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioRepository usuarioRepositoryMock;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepositoryMock = Mockito.mock(UsuarioRepository.class);
        usuarioService = new UsuarioService(usuarioRepositoryMock);
    }

    @Test
    void createUsuario_ShouldReturnDto_WhenValidData() {
        UsuarioDTO dto = new UsuarioDTO(null, "Juan", "juan@email.com", "1234", "USER");
        Usuario savedUsuario = new Usuario(UUID.randomUUID(), "Juan", "juan@email.com", "1234", "USER");

        when(usuarioRepositoryMock.save(any(Usuario.class))).thenReturn(savedUsuario);

        UsuarioDTO result = usuarioService.createUsuario(dto);

        assertNotNull(result);
        assertEquals(savedUsuario.getId().toString(), result.getId());
        assertEquals("Juan", result.getNombre());
        assertEquals("juan@email.com", result.getEmail());
        verify(usuarioRepositoryMock, times(1)).save(any(Usuario.class));
    }

    @Test
    void createUsuario_ShouldThrowException_WhenEmailInvalid() {
        UsuarioDTO dto = new UsuarioDTO(null, "Juan", "emailsinArroba", "1234", "USER");

        assertThrows(IllegalArgumentException.class, () -> usuarioService.createUsuario(dto));
        verify(usuarioRepositoryMock, never()).save(any(Usuario.class));
    }

    @Test
    void createUsuario_ShouldThrowException_WhenEmailIsNull() {
        UsuarioDTO dto = new UsuarioDTO(null, "Juan", null, "1234", "USER");

        assertThrows(IllegalArgumentException.class, () -> usuarioService.createUsuario(dto));
        verify(usuarioRepositoryMock, never()).save(any(Usuario.class));
    }

    @Test
    void getUsuarioById_ShouldReturnDto_WhenExists() {
        UUID id = UUID.randomUUID();
        Usuario usuario = new Usuario(id, "Juan", "juan@email.com", "1234", "USER");

        when(usuarioRepositoryMock.findById(id)).thenReturn(Optional.of(usuario));

        Optional<UsuarioDTO> result = usuarioService.getUsuarioById(id.toString());

        assertTrue(result.isPresent());
        assertEquals(id.toString(), result.get().getId());
        assertEquals("Juan", result.get().getNombre());
    }

    @Test
    void getUsuarioById_ShouldReturnEmpty_WhenNotExists() {
        UUID id = UUID.randomUUID();
        when(usuarioRepositoryMock.findById(id)).thenReturn(Optional.empty());

        Optional<UsuarioDTO> result = usuarioService.getUsuarioById(id.toString());

        assertFalse(result.isPresent());
    }

    @Test
    void updateUsuario_ShouldReturnUpdatedDto_WhenExists() {
        UUID id = UUID.randomUUID();
        Usuario existing = new Usuario(id, "Juan", "juan@email.com", "1234", "USER");
        Usuario updated = new Usuario(id, "Juan Actualizado", "juan@email.com", "1234", "ADMIN");

        when(usuarioRepositoryMock.findById(id)).thenReturn(Optional.of(existing));
        when(usuarioRepositoryMock.update(any(Usuario.class))).thenReturn(updated);

        UsuarioDTO dto = new UsuarioDTO(null, "Juan Actualizado", null, null, "ADMIN");
        UsuarioDTO result = usuarioService.updateUsuario(id.toString(), dto);

        assertNotNull(result);
        assertEquals("Juan Actualizado", result.getNombre());
        assertEquals("ADMIN", result.getRol());
        verify(usuarioRepositoryMock, times(1)).update(any(Usuario.class));
    }

    @Test
    void updateUsuario_ShouldThrowException_WhenNotExists() {
        UUID id = UUID.randomUUID();
        when(usuarioRepositoryMock.findById(id)).thenReturn(Optional.empty());

        UsuarioDTO dto = new UsuarioDTO(null, "Juan", "juan@email.com", "1234", "USER");

        assertThrows(IllegalArgumentException.class, () -> usuarioService.updateUsuario(id.toString(), dto));
        verify(usuarioRepositoryMock, never()).update(any(Usuario.class));
    }
}
