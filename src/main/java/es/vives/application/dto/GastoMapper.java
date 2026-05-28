package es.vives.application.dto;

import es.vives.domain.Gasto;
import java.util.UUID;

public class GastoMapper {
    public static GastoDTO toDTO(Gasto gasto) {
        if (gasto == null) return null;
        return new GastoDTO(
                gasto.getId().toString(),
                gasto.getDescripcion(),
                gasto.getCantidad(),
                gasto.getFecha(),
                gasto.getIdCasa().toString(),
                gasto.getIdUsuario().toString()
        );
    }

    public static Gasto toEntity(GastoDTO dto) {
        if (dto == null) return null;
        return new Gasto(
                dto.getId() != null ? UUID.fromString(dto.getId()) : UUID.randomUUID(),
                dto.getDescripcion(),
                dto.getCantidad(),
                dto.getFecha(),
                UUID.fromString(dto.getIdCasa()),
                UUID.fromString(dto.getIdUsuario())
        );
    }
}
