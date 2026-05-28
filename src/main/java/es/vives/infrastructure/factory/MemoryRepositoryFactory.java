package es.vives.infrastructure.factory;

import es.vives.domain.repository.CasaRepository;
import es.vives.domain.repository.EstanciaRepository;
import es.vives.domain.repository.GastoRepository;
import es.vives.domain.repository.TareaRepository;
import es.vives.domain.repository.UsuarioRepository;
import es.vives.infrastructure.persistence.memory.*;

public class MemoryRepositoryFactory implements RepositoryFactory {

    private static MemoryRepositoryFactory instance;

    // Repositories as singletons in memory
    private final CasaRepository casaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstanciaRepository estanciaRepository;
    private final GastoRepository gastoRepository;
    private final TareaRepository tareaRepository;

    private MemoryRepositoryFactory() {
        this.casaRepository = new MemoryCasaRepository();
        this.usuarioRepository = new MemoryUsuarioRepository();
        this.estanciaRepository = new MemoryEstanciaRepository();
        this.gastoRepository = new MemoryGastoRepository();
        this.tareaRepository = new MemoryTareaRepository();
    }

    public static synchronized MemoryRepositoryFactory getInstance() {
        if (instance == null) {
            instance = new MemoryRepositoryFactory();
        }
        return instance;
    }

    @Override
    public CasaRepository createCasaRepository() {
        return casaRepository;
    }

    @Override
    public UsuarioRepository createUsuarioRepository() {
        return usuarioRepository;
    }

    @Override
    public EstanciaRepository createEstanciaRepository() {
        return estanciaRepository;
    }

    @Override
    public GastoRepository createGastoRepository() {
        return gastoRepository;
    }

    @Override
    public TareaRepository createTareaRepository() {
        return tareaRepository;
    }
}
