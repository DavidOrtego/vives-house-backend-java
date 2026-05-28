package es.vives.application.service;

import es.vives.application.dto.TareaDTO;
import es.vives.domain.Tarea;
import es.vives.domain.repository.TareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TareaServiceTest {

    private TareaRepository tareaRepositoryMock;
    private TareaService tareaService;

    @BeforeEach
    void setUp() {
        tareaRepositoryMock = Mockito.mock(TareaRepository.class);
        tareaService = new TareaService(tareaRepositoryMock);
    }

    @Test
    void createTarea_ShouldReturnDto_WhenValidData() {
        UUID idCasa = UUID.randomUUID();
        UUID idUsuarioAsignado = UUID.randomUUID();

        TareaDTO dto = new TareaDTO(null, "Limpiar cocina", "PENDIENTE", idCasa.toString(), idUsuarioAsignado.toString());
        Tarea savedTarea = new Tarea(UUID.randomUUID(), "Limpiar cocina", "PENDIENTE", idCasa, idUsuarioAsignado);

        when(tareaRepositoryMock.save(any(Tarea.class))).thenReturn(savedTarea);

        TareaDTO result = tareaService.createTarea(dto);

        assertNotNull(result);
        assertEquals(savedTarea.getId().toString(), result.getId());
        assertEquals("Limpiar cocina", result.getDescripcion());
        assertEquals("PENDIENTE", result.getEstado());
        verify(tareaRepositoryMock, times(1)).save(any(Tarea.class));
    }

    @Test
    void createTarea_ShouldThrowException_WhenDescripcionIsNull() {
        TareaDTO dto = new TareaDTO(null, null, "PENDIENTE", UUID.randomUUID().toString(), UUID.randomUUID().toString());

        assertThrows(IllegalArgumentException.class, () -> tareaService.createTarea(dto));
        verify(tareaRepositoryMock, never()).save(any(Tarea.class));
    }

    @Test
    void createTarea_ShouldThrowException_WhenDescripcionIsEmpty() {
        TareaDTO dto = new TareaDTO(null, "", "PENDIENTE", UUID.randomUUID().toString(), UUID.randomUUID().toString());

        assertThrows(IllegalArgumentException.class, () -> tareaService.createTarea(dto));
        verify(tareaRepositoryMock, never()).save(any(Tarea.class));
    }

    @Test
    void getTareaById_ShouldReturnDto_WhenExists() {
        UUID id = UUID.randomUUID();
        UUID idCasa = UUID.randomUUID();
        UUID idUsuarioAsignado = UUID.randomUUID();

        Tarea tarea = new Tarea(id, "Limpiar cocina", "PENDIENTE", idCasa, idUsuarioAsignado);

        when(tareaRepositoryMock.findById(id)).thenReturn(Optional.of(tarea));

        Optional<TareaDTO> result = tareaService.getTareaById(id.toString());

        assertTrue(result.isPresent());
        assertEquals(id.toString(), result.get().getId());
        assertEquals("Limpiar cocina", result.get().getDescripcion());
    }

    @Test
    void getTareaById_ShouldReturnEmpty_WhenNotExists() {
        UUID id = UUID.randomUUID();
        when(tareaRepositoryMock.findById(id)).thenReturn(Optional.empty());

        Optional<TareaDTO> result = tareaService.getTareaById(id.toString());

        assertFalse(result.isPresent());
    }

    @Test
    void deleteTarea_ShouldCallRepository() {
        UUID id = UUID.randomUUID();

        tareaService.deleteTarea(id.toString());

        verify(tareaRepositoryMock, times(1)).deleteById(id);
    }
}
