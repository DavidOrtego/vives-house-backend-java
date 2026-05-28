package es.vives.domain.repository;

import es.vives.domain.Estancia;
import java.util.UUID;
import java.util.List;

public interface EstanciaRepository extends CrudRepository<Estancia, UUID> {
    List<Estancia> findByCasaId(UUID idCasa);
    List<Estancia> findByUsuarioId(UUID idUsuario);
}
