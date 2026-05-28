package es.vives.infrastructure.persistence.memory;

import es.vives.domain.Estancia;
import es.vives.domain.repository.EstanciaRepository;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryEstanciaRepository implements EstanciaRepository {
    private final Map<UUID, Estancia> database = new HashMap<>();

    @Override
    public Estancia save(Estancia entity) {
        database.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Estancia> findById(UUID id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Estancia> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Estancia update(Estancia entity) {
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
    public List<Estancia> findByCasaId(UUID idCasa) {
        return database.values().stream()
                .filter(e -> e.getIdCasa().equals(idCasa))
                .collect(Collectors.toList());
    }

    @Override
    public List<Estancia> findByUsuarioId(UUID idUsuario) {
        return database.values().stream()
                .filter(e -> e.getIdUsuario().equals(idUsuario))
                .collect(Collectors.toList());
    }
}
