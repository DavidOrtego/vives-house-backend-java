package es.vives.application.dto;

import es.vives.domain.Estancia;
import java.util.UUID;

public class EstanciaMapper {
    public static EstanciaDTO toDTO(Estancia estancia) {
        if (estancia == null) return null;
        return new EstanciaDTO(
                estancia.getId().toString(),
                estancia.getIdCasa().toString(),
                estancia.getIdUsuario().toString(),
                estancia.getFechaEntrada(),
                estancia.getFechaSalida()
        );
    }

    public static Estancia toEntity(EstanciaDTO dto) {
        if (dto == null) return null;
        return new Estancia(
                dto.getId() != null ? UUID.fromString(dto.getId()) : UUID.randomUUID(),
                UUID.fromString(dto.getIdCasa()),
                UUID.fromString(dto.getIdUsuario()),
                dto.getFechaEntrada(),
                dto.getFechaSalida()
        );
    }
}
