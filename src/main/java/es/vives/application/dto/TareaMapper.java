package es.vives.application.dto;

import es.vives.domain.Tarea;
import java.util.UUID;

public class TareaMapper {
    public static TareaDTO toDTO(Tarea tarea) {
        if (tarea == null) return null;
        return new TareaDTO(
                tarea.getId().toString(),
                tarea.getDescripcion(),
                tarea.getEstado(),
                tarea.getIdCasa().toString(),
                tarea.getIdUsuarioAsignado() != null ? tarea.getIdUsuarioAsignado().toString() : null
        );
    }

    public static Tarea toEntity(TareaDTO dto) {
        if (dto == null) return null;
        return new Tarea(
                dto.getId() != null ? UUID.fromString(dto.getId()) : UUID.randomUUID(),
                dto.getDescripcion(),
                dto.getEstado(),
                UUID.fromString(dto.getIdCasa()),
                dto.getIdUsuarioAsignado() != null ? UUID.fromString(dto.getIdUsuarioAsignado()) : null
        );
    }
}
