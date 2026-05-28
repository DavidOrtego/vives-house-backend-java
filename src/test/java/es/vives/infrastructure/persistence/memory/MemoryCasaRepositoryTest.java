package es.vives.infrastructure.persistence.memory;

import es.vives.domain.Casa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemoryCasaRepositoryTest {

    private MemoryCasaRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MemoryCasaRepository();
    }

    @Test
    void save_ShouldSaveCasa() {
        Casa casa = new Casa(UUID.randomUUID(), "Casa Vives", "Calle Sol, 1");
        Casa savedCasa = repository.save(casa);

        assertNotNull(savedCasa);
        assertEquals("Casa Vives", savedCasa.getNombre());
        assertEquals("Calle Sol, 1", savedCasa.getDireccion());
    }

    @Test
    void findById_ShouldReturnCasa_WhenExists() {
        Casa casa = new Casa(UUID.randomUUID(), "Casa Luna", "Calle Luna, 2");
        repository.save(casa);

        Optional<Casa> foundCasa = repository.findById(casa.getId());

        assertTrue(foundCasa.isPresent());
        assertEquals("Casa Luna", foundCasa.get().getNombre());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotExists() {
        Optional<Casa> foundCasa = repository.findById(UUID.randomUUID());
        assertFalse(foundCasa.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllCasas() {
        Casa casa1 = new Casa(UUID.randomUUID(), "Casa 1", "Dir 1");
        Casa casa2 = new Casa(UUID.randomUUID(), "Casa 2", "Dir 2");
        repository.save(casa1);
        repository.save(casa2);

        List<Casa> casas = repository.findAll();

        assertEquals(2, casas.size());
    }

    @Test
    void update_ShouldUpdateCasa() {
        Casa casa = new Casa(UUID.randomUUID(), "Casa Vieja", "Dir Antigua");
        repository.save(casa);

        casa.setNombre("Casa Nueva");
        casa.setDireccion("Dir Nueva");
        Casa updatedCasa = repository.update(casa);

        assertNotNull(updatedCasa);
        assertEquals("Casa Nueva", updatedCasa.getNombre());
        assertEquals("Dir Nueva", updatedCasa.getDireccion());
    }

    @Test
    void deleteById_ShouldRemoveCasa() {
        Casa casa = new Casa(UUID.randomUUID(), "Casa a borrar", "Dir a borrar");
        repository.save(casa);

        repository.deleteById(casa.getId());

        Optional<Casa> foundCasa = repository.findById(casa.getId());
        assertFalse(foundCasa.isPresent());
    }
}
