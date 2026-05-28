package es.vives.infrastructure.factory;

import es.vives.domain.repository.CasaRepository;
import es.vives.domain.repository.EstanciaRepository;
import es.vives.domain.repository.GastoRepository;
import es.vives.domain.repository.TareaRepository;
import es.vives.domain.repository.UsuarioRepository;

public interface RepositoryFactory {
    CasaRepository createCasaRepository();
    UsuarioRepository createUsuarioRepository();
    EstanciaRepository createEstanciaRepository();
    GastoRepository createGastoRepository();
    TareaRepository createTareaRepository();
}
