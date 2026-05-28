package es.vives.infrastructure.persistence.memory;

import es.vives.domain.Casa;
import es.vives.domain.repository.CasaRepository;

import java.util.*;

public class MemoryCasaRepository implements CasaRepository {
    private final Map<UUID, Casa> database = new HashMap<>();

    @Override
    public Casa save(Casa entity) {
        database.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Casa> findById(UUID id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Casa> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Casa update(Casa entity) {
        if (!database.containsKey(entity.getId())) {
            return null; // Or throw exception
        }
        database.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void deleteById(UUID id) {
        database.remove(id);
    }
}
