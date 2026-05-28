package es.vives.application.dto;

import es.vives.domain.Usuario;
import java.util.UUID;

public class UsuarioMapper {
    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioDTO(
                usuario.getId().toString(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getRol()
        );
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;
        return new Usuario(
                dto.getId() != null ? UUID.fromString(dto.getId()) : UUID.randomUUID(),
                dto.getNombre(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getRol()
        );
    }
}
