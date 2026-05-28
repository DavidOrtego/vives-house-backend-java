package es.vives.infrastructure.persistence.memory;

import es.vives.domain.Gasto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemoryGastoRepositoryTest {

    private MemoryGastoRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MemoryGastoRepository();
    }

    @Test
    void save_ShouldSaveGasto() {
        Gasto gasto = new Gasto(UUID.randomUUID(), "Compra supermercado", 45.50,
                LocalDate.of(2025, 6, 1), UUID.randomUUID(), UUID.randomUUID());
        Gasto savedGasto = repository.save(gasto);

        assertNotNull(savedGasto);
        assertEquals("Compra supermercado", savedGasto.getDescripcion());
        assertEquals(45.50, savedGasto.getCantidad());
    }

    @Test
    void findById_ShouldReturnGasto_WhenExists() {
        Gasto gasto = new Gasto(UUID.randomUUID(), "Luz", 80.0,
                LocalDate.of(2025, 7, 1), UUID.randomUUID(), UUID.randomUUID());
        repository.save(gasto);

        Optional<Gasto> foundGasto = repository.findById(gasto.getId());

        assertTrue(foundGasto.isPresent());
        assertEquals("Luz", foundGasto.get().getDescripcion());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotExists() {
        Optional<Gasto> foundGasto = repository.findById(UUID.randomUUID());
        assertFalse(foundGasto.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllGastos() {
        Gasto gasto1 = new Gasto(UUID.randomUUID(), "Agua", 30.0,
                LocalDate.of(2025, 1, 1), UUID.randomUUID(), UUID.randomUUID());
        Gasto gasto2 = new Gasto(UUID.randomUUID(), "Gas", 50.0,
                LocalDate.of(2025, 2, 1), UUID.randomUUID(), UUID.randomUUID());
        repository.save(gasto1);
        repository.save(gasto2);

        List<Gasto> gastos = repository.findAll();

        assertEquals(2, gastos.size());
    }

    @Test
    void update_ShouldUpdateGasto() {
        Gasto gasto = new Gasto(UUID.randomUUID(), "Internet", 40.0,
                LocalDate.of(2025, 3, 1), UUID.randomUUID(), UUID.randomUUID());
        repository.save(gasto);

        gasto.setDescripcion("Internet fibra");
        gasto.setCantidad(55.0);
        Gasto updatedGasto = repository.update(gasto);

        assertNotNull(updatedGasto);
        assertEquals("Internet fibra", updatedGasto.getDescripcion());
        assertEquals(55.0, updatedGasto.getCantidad());
    }

    @Test
    void update_ShouldReturnNull_WhenNotExists() {
        Gasto gasto = new Gasto(UUID.randomUUID(), "Seguro", 200.0,
                LocalDate.of(2025, 4, 1), UUID.randomUUID(), UUID.randomUUID());

        Gasto updatedGasto = repository.update(gasto);

        assertNull(updatedGasto);
    }

    @Test
    void deleteById_ShouldRemoveGasto() {
        Gasto gasto = new Gasto(UUID.randomUUID(), "Gasto a borrar", 10.0,
                LocalDate.of(2025, 5, 1), UUID.randomUUID(), UUID.randomUUID());
        repository.save(gasto);

        repository.deleteById(gasto.getId());

        Optional<Gasto> foundGasto = repository.findById(gasto.getId());
        assertFalse(foundGasto.isPresent());
    }

    @Test
    void findByCasaId_ShouldReturnGastosDeLaCasa() {
        UUID idCasa = UUID.randomUUID();
        UUID otraCasa = UUID.randomUUID();

        Gasto gasto1 = new Gasto(UUID.randomUUID(), "Luz", 80.0,
                LocalDate.of(2025, 6, 1), idCasa, UUID.randomUUID());
        Gasto gasto2 = new Gasto(UUID.randomUUID(), "Agua", 30.0,
                LocalDate.of(2025, 7, 1), idCasa, UUID.randomUUID());
        Gasto gasto3 = new Gasto(UUID.randomUUID(), "Gas", 50.0,
                LocalDate.of(2025, 8, 1), otraCasa, UUID.randomUUID());
        repository.save(gasto1);
        repository.save(gasto2);
        repository.save(gasto3);

        List<Gasto> resultado = repository.findByCasaId(idCasa);

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(g -> g.getIdCasa().equals(idCasa)));
    }

    @Test
    void findByUsuarioId_ShouldReturnGastosDelUsuario() {
        UUID idUsuario = UUID.randomUUID();
        UUID otroUsuario = UUID.randomUUID();

        Gasto gasto1 = new Gasto(UUID.randomUUID(), "Compra 1", 20.0,
                LocalDate.of(2025, 9, 1), UUID.randomUUID(), idUsuario);
        Gasto gasto2 = new Gasto(UUID.randomUUID(), "Compra 2", 35.0,
                LocalDate.of(2025, 10, 1), UUID.randomUUID(), idUsuario);
        Gasto gasto3 = new Gasto(UUID.randomUUID(), "Compra 3", 15.0,
                LocalDate.of(2025, 11, 1), UUID.randomUUID(), otroUsuario);
        repository.save(gasto1);
        repository.save(gasto2);
        repository.save(gasto3);

        List<Gasto> resultado = repository.findByUsuarioId(idUsuario);

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(g -> g.getIdUsuario().equals(idUsuario)));
    }
}
