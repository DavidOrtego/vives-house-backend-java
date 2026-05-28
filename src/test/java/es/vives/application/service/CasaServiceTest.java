package es.vives.application.service;

import es.vives.application.dto.CasaDTO;
import es.vives.domain.Casa;
import es.vives.domain.repository.CasaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CasaServiceTest {

    private CasaRepository casaRepositoryMock;
    private CasaService casaService;

    @BeforeEach
    void setUp() {
        casaRepositoryMock = Mockito.mock(CasaRepository.class);
        casaService = new CasaService(casaRepositoryMock);
    }

    @Test
    void createCasa_ShouldReturnDto_WhenValidData() {
        CasaDTO dto = new CasaDTO(null, "Casa Test", "Dir Test");
        Casa savedCasa = new Casa(UUID.randomUUID(), "Casa Test", "Dir Test");
        
        when(casaRepositoryMock.save(any(Casa.class))).thenReturn(savedCasa);

        CasaDTO result = casaService.createCasa(dto);

        assertNotNull(result);
        assertEquals(savedCasa.getId().toString(), result.getId());
        assertEquals("Casa Test", result.getNombre());
        verify(casaRepositoryMock, times(1)).save(any(Casa.class));
    }

    @Test
    void createCasa_ShouldThrowException_WhenNameIsEmpty() {
        CasaDTO dto = new CasaDTO(null, "", "Dir Test");

        assertThrows(IllegalArgumentException.class, () -> casaService.createCasa(dto));
        verify(casaRepositoryMock, never()).save(any(Casa.class));
    }

    @Test
    void getCasaById_ShouldReturnDto_WhenExists() {
        UUID id = UUID.randomUUID();
        Casa casa = new Casa(id, "Casa Test", "Dir Test");
        
        when(casaRepositoryMock.findById(id)).thenReturn(Optional.of(casa));

        Optional<CasaDTO> result = casaService.getCasaById(id.toString());

        assertTrue(result.isPresent());
        assertEquals(id.toString(), result.get().getId());
    }

    @Test
    void getCasaById_ShouldReturnEmpty_WhenNotExists() {
        UUID id = UUID.randomUUID();
        when(casaRepositoryMock.findById(id)).thenReturn(Optional.empty());

        Optional<CasaDTO> result = casaService.getCasaById(id.toString());

        assertFalse(result.isPresent());
    }
}
