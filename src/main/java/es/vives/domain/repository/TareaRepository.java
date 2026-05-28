package es.vives.domain.repository;

import es.vives.domain.Tarea;
import java.util.UUID;
import java.util.List;

public interface TareaRepository extends CrudRepository<Tarea, UUID> {
    List<Tarea> findByCasaId(UUID idCasa);
    List<Tarea> findByUsuarioAsignadoId(UUID idUsuarioAsignado);
}
