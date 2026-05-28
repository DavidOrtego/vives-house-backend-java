package es.vives.application.dto;

import es.vives.domain.Casa;
import java.util.UUID;

public class CasaMapper {
    public static CasaDTO toDTO(Casa casa) {
        if (casa == null) return null;
        return new CasaDTO(casa.getId().toString(), casa.getNombre(), casa.getDireccion());
    }

    public static Casa toEntity(CasaDTO dto) {
        if (dto == null) return null;
        return new Casa(
                dto.getId() != null ? UUID.fromString(dto.getId()) : UUID.randomUUID(),
                dto.getNombre(),
                dto.getDireccion()
        );
    }
}
