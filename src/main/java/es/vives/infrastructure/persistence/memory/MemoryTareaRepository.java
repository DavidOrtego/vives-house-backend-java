package es.vives.infrastructure.persistence.memory;

import es.vives.domain.Tarea;
import es.vives.domain.repository.TareaRepository;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryTareaRepository implements TareaRepository {
    private final Map<UUID, Tarea> database = new HashMap<>();

    @Override
    public Tarea save(Tarea entity) {
        database.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Tarea> findById(UUID id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Tarea> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Tarea update(Tarea entity) {
        if (!database.containsKey(entity.getId())) {
            return null;
        }
        database.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void deleteById(UUID id) {
        database.remove(id);
    }

    @Override
    public List<Tarea> findByCasaId(UUID idCasa) {
        return database.values().stream()
                .filter(t -> t.getIdCasa().equals(idCasa))
                .collect(Collectors.toList());
    }

    @Override
    public List<Tarea> findByUsuarioAsignadoId(UUID idUsuarioAsignado) {
        return database.values().stream()
                .filter(t -> t.getIdUsuarioAsignado().equals(idUsuarioAsignado))
                .collect(Collectors.toList());
    }
}
