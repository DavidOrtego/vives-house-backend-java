package es.vives.infrastructure.factory;

import es.vives.domain.repository.CasaRepository;
import es.vives.domain.repository.EstanciaRepository;
import es.vives.domain.repository.GastoRepository;
import es.vives.domain.repository.TareaRepository;
import es.vives.domain.repository.UsuarioRepository;
import es.vives.infrastructure.persistence.sqlite.*;

public class SQLiteRepositoryFactory implements RepositoryFactory {

    private static SQLiteRepositoryFactory instance;

    // Repositories
    private final CasaRepository casaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstanciaRepository estanciaRepository;
    private final GastoRepository gastoRepository;
    private final TareaRepository tareaRepository;

    private SQLiteRepositoryFactory() {
        this.casaRepository = new SQLiteCasaRepository();
        this.usuarioRepository = new SQLiteUsuarioRepository();
        this.estanciaRepository = new SQLiteEstanciaRepository();
        this.gastoRepository = new SQLiteGastoRepository();
        this.tareaRepository = new SQLiteTareaRepository();
    }

    public static synchronized SQLiteRepositoryFactory getInstance() {
        if (instance == null) {
            instance = new SQLiteRepositoryFactory();
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
