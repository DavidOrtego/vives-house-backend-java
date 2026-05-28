package es.vives.application.service;

import es.vives.application.dto.GastoDTO;
import es.vives.domain.Gasto;
import es.vives.domain.repository.GastoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GastoServiceTest {

    private GastoRepository gastoRepositoryMock;
    private GastoService gastoService;

    @BeforeEach
    void setUp() {
        gastoRepositoryMock = Mockito.mock(GastoRepository.class);
        gastoService = new GastoService(gastoRepositoryMock);
    }

    @Test
    void createGasto_ShouldReturnDto_WhenValidData() {
        UUID idCasa = UUID.randomUUID();
        UUID idUsuario = UUID.randomUUID();
        LocalDate fecha = LocalDate.of(2026, 5, 28);

        GastoDTO dto = new GastoDTO(null, "Compra supermercado", 50.0, fecha, idCasa.toString(), idUsuario.toString());
        Gasto savedGasto = new Gasto(UUID.randomUUID(), "Compra supermercado", 50.0, fecha, idCasa, idUsuario);

        when(gastoRepositoryMock.save(any(Gasto.class))).thenReturn(savedGasto);

        GastoDTO result = gastoService.createGasto(dto);

        assertNotNull(result);
        assertEquals(savedGasto.getId().toString(), result.getId());
        assertEquals("Compra supermercado", result.getDescripcion());
        assertEquals(50.0, result.getCantidad());
        verify(gastoRepositoryMock, times(1)).save(any(Gasto.class));
    }

    @Test
    void createGasto_ShouldThrowException_WhenCantidadIsZero() {
        GastoDTO dto = new GastoDTO(null, "Gasto", 0, LocalDate.now(), UUID.randomUUID().toString(), UUID.randomUUID().toString());

        assertThrows(IllegalArgumentException.class, () -> gastoService.createGasto(dto));
        verify(gastoRepositoryMock, never()).save(any(Gasto.class));
    }

    @Test
    void createGasto_ShouldThrowException_WhenCantidadIsNegative() {
        GastoDTO dto = new GastoDTO(null, "Gasto", -10.0, LocalDate.now(), UUID.randomUUID().toString(), UUID.randomUUID().toString());

        assertThrows(IllegalArgumentException.class, () -> gastoService.createGasto(dto));
        verify(gastoRepositoryMock, never()).save(any(Gasto.class));
    }

    @Test
    void getGastoById_ShouldReturnDto_WhenExists() {
        UUID id = UUID.randomUUID();
        UUID idCasa = UUID.randomUUID();
        UUID idUsuario = UUID.randomUUID();
        LocalDate fecha = LocalDate.of(2026, 5, 28);

        Gasto gasto = new Gasto(id, "Compra supermercado", 50.0, fecha, idCasa, idUsuario);

        when(gastoRepositoryMock.findById(id)).thenReturn(Optional.of(gasto));

        Optional<GastoDTO> result = gastoService.getGastoById(id.toString());

        assertTrue(result.isPresent());
        assertEquals(id.toString(), result.get().getId());
        assertEquals("Compra supermercado", result.get().getDescripcion());
    }

    @Test
    void getGastoById_ShouldReturnEmpty_WhenNotExists() {
        UUID id = UUID.randomUUID();
        when(gastoRepositoryMock.findById(id)).thenReturn(Optional.empty());

        Optional<GastoDTO> result = gastoService.getGastoById(id.toString());

        assertFalse(result.isPresent());
    }

    @Test
    void deleteGasto_ShouldCallRepository() {
        UUID id = UUID.randomUUID();

        gastoService.deleteGasto(id.toString());

        verify(gastoRepositoryMock, times(1)).deleteById(id);
    }
}
