package es.vives.infrastructure.persistence.memory;

import es.vives.domain.Gasto;
import es.vives.domain.repository.GastoRepository;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryGastoRepository implements GastoRepository {
    private final Map<UUID, Gasto> database = new HashMap<>();

    @Override
    public Gasto save(Gasto entity) {
        database.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Gasto> findById(UUID id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Gasto> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Gasto update(Gasto entity) {
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
    public List<Gasto> findByCasaId(UUID idCasa) {
        return database.values().stream()
                .filter(g -> g.getIdCasa().equals(idCasa))
                .collect(Collectors.toList());
    }

    @Override
    public List<Gasto> findByUsuarioId(UUID idUsuario) {
        return database.values().stream()
                .filter(g -> g.getIdUsuario().equals(idUsuario))
                .collect(Collectors.toList());
    }
}
