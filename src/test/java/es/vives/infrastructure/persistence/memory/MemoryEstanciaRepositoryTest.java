package es.vives.infrastructure.persistence.memory;

import es.vives.domain.Estancia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemoryEstanciaRepositoryTest {

    private MemoryEstanciaRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MemoryEstanciaRepository();
    }

    @Test
    void save_ShouldSaveEstancia() {
        Estancia estancia = new Estancia(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 15));
        Estancia savedEstancia = repository.save(estancia);

        assertNotNull(savedEstancia);
        assertEquals(estancia.getId(), savedEstancia.getId());
        assertEquals(LocalDate.of(2025, 6, 1), savedEstancia.getFechaEntrada());
        assertEquals(LocalDate.of(2025, 6, 15), savedEstancia.getFechaSalida());
    }

    @Test
    void findById_ShouldReturnEstancia_WhenExists() {
        Estancia estancia = new Estancia(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                LocalDate.of(2025, 7, 1), LocalDate.of(2025, 7, 10));
        repository.save(estancia);

        Optional<Estancia> foundEstancia = repository.findById(estancia.getId());

        assertTrue(foundEstancia.isPresent());
        assertEquals(estancia.getId(), foundEstancia.get().getId());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotExists() {
        Optional<Estancia> foundEstancia = repository.findById(UUID.randomUUID());
        assertFalse(foundEstancia.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllEstancias() {
        Estancia estancia1 = new Estancia(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 10));
        Estancia estancia2 = new Estancia(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                LocalDate.of(2025, 2, 1), LocalDate.of(2025, 2, 10));
        repository.save(estancia1);
        repository.save(estancia2);

        List<Estancia> estancias = repository.findAll();

        assertEquals(2, estancias.size());
    }

    @Test
    void update_ShouldUpdateEstancia() {
        Estancia estancia = new Estancia(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 10));
        repository.save(estancia);

        estancia.setFechaSalida(LocalDate.of(2025, 3, 20));
        Estancia updatedEstancia = repository.update(estancia);

        assertNotNull(updatedEstancia);
        assertEquals(LocalDate.of(2025, 3, 20), updatedEstancia.getFechaSalida());
    }

    @Test
    void update_ShouldReturnNull_WhenNotExists() {
        Estancia estancia = new Estancia(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 10));

        Estancia updatedEstancia = repository.update(estancia);

        assertNull(updatedEstancia);
    }

    @Test
    void deleteById_ShouldRemoveEstancia() {
        Estancia estancia = new Estancia(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 10));
        repository.save(estancia);

        repository.deleteById(estancia.getId());

        Optional<Estancia> foundEstancia = repository.findById(estancia.getId());
        assertFalse(foundEstancia.isPresent());
    }

    @Test
    void findByCasaId_ShouldReturnEstanciasDeLaCasa() {
        UUID idCasa = UUID.randomUUID();
        UUID otraCasa = UUID.randomUUID();

        Estancia estancia1 = new Estancia(UUID.randomUUID(), idCasa, UUID.randomUUID(),
                LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 10));
        Estancia estancia2 = new Estancia(UUID.randomUUID(), idCasa, UUID.randomUUID(),
                LocalDate.of(2025, 7, 1), LocalDate.of(2025, 7, 10));
        Estancia estancia3 = new Estancia(UUID.randomUUID(), otraCasa, UUID.randomUUID(),
                LocalDate.of(2025, 8, 1), LocalDate.of(2025, 8, 10));
        repository.save(estancia1);
        repository.save(estancia2);
        repository.save(estancia3);

        List<Estancia> resultado = repository.findByCasaId(idCasa);

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(e -> e.getIdCasa().equals(idCasa)));
    }

    @Test
    void findByUsuarioId_ShouldReturnEstanciasDelUsuario() {
        UUID idUsuario = UUID.randomUUID();
        UUID otroUsuario = UUID.randomUUID();

        Estancia estancia1 = new Estancia(UUID.randomUUID(), UUID.randomUUID(), idUsuario,
                LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 10));
        Estancia estancia2 = new Estancia(UUID.randomUUID(), UUID.randomUUID(), idUsuario,
                LocalDate.of(2025, 10, 1), LocalDate.of(2025, 10, 10));
        Estancia estancia3 = new Estancia(UUID.randomUUID(), UUID.randomUUID(), otroUsuario,
                LocalDate.of(2025, 11, 1), LocalDate.of(2025, 11, 10));
        repository.save(estancia1);
        repository.save(estancia2);
        repository.save(estancia3);

        List<Estancia> resultado = repository.findByUsuarioId(idUsuario);

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(e -> e.getIdUsuario().equals(idUsuario)));
    }
}
