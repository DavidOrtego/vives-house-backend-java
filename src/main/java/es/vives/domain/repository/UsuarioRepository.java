package es.vives.domain.repository;

import es.vives.domain.Usuario;
import java.util.UUID;

public interface UsuarioRepository extends CrudRepository<Usuario, UUID> {
}
