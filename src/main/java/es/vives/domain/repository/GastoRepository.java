package es.vives.domain.repository;

import es.vives.domain.Gasto;
import java.util.UUID;
import java.util.List;

public interface GastoRepository extends CrudRepository<Gasto, UUID> {
    List<Gasto> findByCasaId(UUID idCasa);
    List<Gasto> findByUsuarioId(UUID idUsuario);
}
