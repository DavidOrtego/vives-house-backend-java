package es.vives.application.service;

import es.vives.application.dto.EstanciaDTO;
import es.vives.domain.Estancia;
import es.vives.domain.repository.EstanciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EstanciaServiceTest {

    private EstanciaRepository estanciaRepositoryMock;
    private EstanciaService estanciaService;

    @BeforeEach
    void setUp() {
        estanciaRepositoryMock = Mockito.mock(EstanciaRepository.class);
        estanciaService = new EstanciaService(estanciaRepositoryMock);
    }

    @Test
    void createEstancia_ShouldReturnDto_WhenValidData() {
        UUID idCasa = UUID.randomUUID();
        UUID idUsuario = UUID.randomUUID();
        LocalDate fechaEntrada = LocalDate.of(2026, 6, 1);
        LocalDate fechaSalida = LocalDate.of(2026, 6, 15);

        EstanciaDTO dto = new EstanciaDTO(null, idCasa.toString(), idUsuario.toString(), fechaEntrada, fechaSalida);
        Estancia savedEstancia = new Estancia(UUID.randomUUID(), idCasa, idUsuario, fechaEntrada, fechaSalida);

        when(estanciaRepositoryMock.save(any(Estancia.class))).thenReturn(savedEstancia);

        EstanciaDTO result = estanciaService.createEstancia(dto);

        assertNotNull(result);
        assertEquals(savedEstancia.getId().toString(), result.getId());
        assertEquals(idCasa.toString(), result.getIdCasa());
        assertEquals(idUsuario.toString(), result.getIdUsuario());
        verify(estanciaRepositoryMock, times(1)).save(any(Estancia.class));
    }

    @Test
    void createEstancia_ShouldThrowException_WhenIdCasaIsNull() {
        UUID idUsuario = UUID.randomUUID();
        EstanciaDTO dto = new EstanciaDTO(null, null, idUsuario.toString(), LocalDate.now(), LocalDate.now().plusDays(7));

        assertThrows(IllegalArgumentException.class, () -> estanciaService.createEstancia(dto));
        verify(estanciaRepositoryMock, never()).save(any(Estancia.class));
    }

    @Test
    void createEstancia_ShouldThrowException_WhenIdUsuarioIsNull() {
        UUID idCasa = UUID.randomUUID();
        EstanciaDTO dto = new EstanciaDTO(null, idCasa.toString(), null, LocalDate.now(), LocalDate.now().plusDays(7));

        assertThrows(IllegalArgumentException.class, () -> estanciaService.createEstancia(dto));
        verify(estanciaRepositoryMock, never()).save(any(Estancia.class));
    }

    @Test
    void getEstanciaById_ShouldReturnDto_WhenExists() {
        UUID id = UUID.randomUUID();
        UUID idCasa = UUID.randomUUID();
        UUID idUsuario = UUID.randomUUID();
        LocalDate fechaEntrada = LocalDate.of(2026, 6, 1);
        LocalDate fechaSalida = LocalDate.of(2026, 6, 15);

        Estancia estancia = new Estancia(id, idCasa, idUsuario, fechaEntrada, fechaSalida);

        when(estanciaRepositoryMock.findById(id)).thenReturn(Optional.of(estancia));

        Optional<EstanciaDTO> result = estanciaService.getEstanciaById(id.toString());

        assertTrue(result.isPresent());
        assertEquals(id.toString(), result.get().getId());
        assertEquals(fechaEntrada, result.get().getFechaEntrada());
    }

    @Test
    void getEstanciaById_ShouldReturnEmpty_WhenNotExists() {
        UUID id = UUID.randomUUID();
        when(estanciaRepositoryMock.findById(id)).thenReturn(Optional.empty());

        Optional<EstanciaDTO> result = estanciaService.getEstanciaById(id.toString());

        assertFalse(result.isPresent());
    }

    @Test
    void updateEstancia_ShouldReturnUpdatedDto_WhenExists() {
        UUID id = UUID.randomUUID();
        UUID idCasa = UUID.randomUUID();
        UUID idUsuario = UUID.randomUUID();
        LocalDate fechaEntrada = LocalDate.of(2026, 6, 1);
        LocalDate fechaSalida = LocalDate.of(2026, 6, 15);
        LocalDate nuevaFechaSalida = LocalDate.of(2026, 6, 20);

        Estancia existing = new Estancia(id, idCasa, idUsuario, fechaEntrada, fechaSalida);
        Estancia updated = new Estancia(id, idCasa, idUsuario, fechaEntrada, nuevaFechaSalida);

        when(estanciaRepositoryMock.findById(id)).thenReturn(Optional.of(existing));
        when(estanciaRepositoryMock.update(any(Estancia.class))).thenReturn(updated);

        EstanciaDTO dto = new EstanciaDTO(null, null, null, null, nuevaFechaSalida);
        EstanciaDTO result = estanciaService.updateEstancia(id.toString(), dto);

        assertNotNull(result);
        assertEquals(nuevaFechaSalida, result.getFechaSalida());
        verify(estanciaRepositoryMock, times(1)).update(any(Estancia.class));
    }

    @Test
    void updateEstancia_ShouldThrowException_WhenNotExists() {
        UUID id = UUID.randomUUID();
        when(estanciaRepositoryMock.findById(id)).thenReturn(Optional.empty());

        EstanciaDTO dto = new EstanciaDTO(null, null, null, null, LocalDate.now());

        assertThrows(IllegalArgumentException.class, () -> estanciaService.updateEstancia(id.toString(), dto));
        verify(estanciaRepositoryMock, never()).update(any(Estancia.class));
    }
}
