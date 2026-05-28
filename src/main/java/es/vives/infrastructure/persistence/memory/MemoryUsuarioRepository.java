package es.vives.infrastructure.persistence.memory;

import es.vives.domain.Usuario;
import es.vives.domain.repository.UsuarioRepository;

import java.util.*;

public class MemoryUsuarioRepository implements UsuarioRepository {
    private final Map<UUID, Usuario> database = new HashMap<>();

    @Override
    public Usuario save(Usuario entity) {
        database.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Usuario> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Usuario update(Usuario entity) {
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
}
