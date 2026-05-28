package es.vives.infrastructure.persistence.memory;

import es.vives.domain.Tarea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemoryTareaRepositoryTest {

    private MemoryTareaRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MemoryTareaRepository();
    }

    @Test
    void save_ShouldSaveTarea() {
        Tarea tarea = new Tarea(UUID.randomUUID(), "Limpiar cocina", "PENDIENTE",
                UUID.randomUUID(), UUID.randomUUID());
        Tarea savedTarea = repository.save(tarea);

        assertNotNull(savedTarea);
        assertEquals("Limpiar cocina", savedTarea.getDescripcion());
        assertEquals("PENDIENTE", savedTarea.getEstado());
    }

    @Test
    void findById_ShouldReturnTarea_WhenExists() {
        Tarea tarea = new Tarea(UUID.randomUUID(), "Fregar platos", "PENDIENTE",
                UUID.randomUUID(), UUID.randomUUID());
        repository.save(tarea);

        Optional<Tarea> foundTarea = repository.findById(tarea.getId());

        assertTrue(foundTarea.isPresent());
        assertEquals("Fregar platos", foundTarea.get().getDescripcion());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotExists() {
        Optional<Tarea> foundTarea = repository.findById(UUID.randomUUID());
        assertFalse(foundTarea.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllTareas() {
        Tarea tarea1 = new Tarea(UUID.randomUUID(), "Tarea 1", "PENDIENTE",
                UUID.randomUUID(), UUID.randomUUID());
        Tarea tarea2 = new Tarea(UUID.randomUUID(), "Tarea 2", "EN_PROGRESO",
                UUID.randomUUID(), UUID.randomUUID());
        repository.save(tarea1);
        repository.save(tarea2);

        List<Tarea> tareas = repository.findAll();

        assertEquals(2, tareas.size());
    }

    @Test
    void update_ShouldUpdateTarea() {
        Tarea tarea = new Tarea(UUID.randomUUID(), "Barrer salon", "PENDIENTE",
                UUID.randomUUID(), UUID.randomUUID());
        repository.save(tarea);

        tarea.setEstado("COMPLETADA");
        tarea.setDescripcion("Barrer y fregar salon");
        Tarea updatedTarea = repository.update(tarea);

        assertNotNull(updatedTarea);
        assertEquals("COMPLETADA", updatedTarea.getEstado());
        assertEquals("Barrer y fregar salon", updatedTarea.getDescripcion());
    }

    @Test
    void update_ShouldReturnNull_WhenNotExists() {
        Tarea tarea = new Tarea(UUID.randomUUID(), "Tarea inexistente", "PENDIENTE",
                UUID.randomUUID(), UUID.randomUUID());

        Tarea updatedTarea = repository.update(tarea);

        assertNull(updatedTarea);
    }

    @Test
    void deleteById_ShouldRemoveTarea() {
        Tarea tarea = new Tarea(UUID.randomUUID(), "Tarea a borrar", "PENDIENTE",
                UUID.randomUUID(), UUID.randomUUID());
        repository.save(tarea);

        repository.deleteById(tarea.getId());

        Optional<Tarea> foundTarea = repository.findById(tarea.getId());
        assertFalse(foundTarea.isPresent());
    }

    @Test
    void findByCasaId_ShouldReturnTareasDeLaCasa() {
        UUID idCasa = UUID.randomUUID();
        UUID otraCasa = UUID.randomUUID();

        Tarea tarea1 = new Tarea(UUID.randomUUID(), "Limpiar baño", "PENDIENTE",
                idCasa, UUID.randomUUID());
        Tarea tarea2 = new Tarea(UUID.randomUUID(), "Hacer camas", "EN_PROGRESO",
                idCasa, UUID.randomUUID());
        Tarea tarea3 = new Tarea(UUID.randomUUID(), "Cocinar", "PENDIENTE",
                otraCasa, UUID.randomUUID());
        repository.save(tarea1);
        repository.save(tarea2);
        repository.save(tarea3);

        List<Tarea> resultado = repository.findByCasaId(idCasa);

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(t -> t.getIdCasa().equals(idCasa)));
    }

    @Test
    void findByUsuarioAsignadoId_ShouldReturnTareasDelUsuario() {
        UUID idUsuario = UUID.randomUUID();
        UUID otroUsuario = UUID.randomUUID();

        Tarea tarea1 = new Tarea(UUID.randomUUID(), "Planchar", "PENDIENTE",
                UUID.randomUUID(), idUsuario);
        Tarea tarea2 = new Tarea(UUID.randomUUID(), "Tender ropa", "COMPLETADA",
                UUID.randomUUID(), idUsuario);
        Tarea tarea3 = new Tarea(UUID.randomUUID(), "Aspirar", "EN_PROGRESO",
                UUID.randomUUID(), otroUsuario);
        repository.save(tarea1);
        repository.save(tarea2);
        repository.save(tarea3);

        List<Tarea> resultado = repository.findByUsuarioAsignadoId(idUsuario);

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(t -> t.getIdUsuarioAsignado().equals(idUsuario)));
    }
}
