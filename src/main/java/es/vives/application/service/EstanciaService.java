package es.vives.application.service;

import es.vives.application.dto.EstanciaDTO;
import es.vives.application.dto.EstanciaMapper;
import es.vives.domain.Estancia;
import es.vives.domain.repository.EstanciaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class EstanciaService {
    private final EstanciaRepository estanciaRepository;

    public EstanciaService(EstanciaRepository estanciaRepository) {
        this.estanciaRepository = estanciaRepository;
    }

    public EstanciaDTO createEstancia(EstanciaDTO dto) {
        if (dto.getIdCasa() == null || dto.getIdUsuario() == null) {
            throw new IllegalArgumentException("Casa y Usuario son obligatorios para una estancia");
        }
        
        Estancia estancia = EstanciaMapper.toEntity(dto);
        estancia = estanciaRepository.save(estancia);
        return EstanciaMapper.toDTO(estancia);
    }

    public Optional<EstanciaDTO> getEstanciaById(String id) {
        return estanciaRepository.findById(UUID.fromString(id))
                .map(EstanciaMapper::toDTO);
    }

    public List<EstanciaDTO> getAllEstancias() {
        return estanciaRepository.findAll().stream()
                .map(EstanciaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EstanciaDTO updateEstancia(String id, EstanciaDTO dto) {
        Optional<Estancia> existingOpt = estanciaRepository.findById(UUID.fromString(id));
        if (existingOpt.isEmpty()) {
            throw new IllegalArgumentException("Estancia no encontrada");
        }

        Estancia existing = existingOpt.get();
        if (dto.getFechaSalida() != null) {
            existing.setFechaSalida(dto.getFechaSalida());
        }
        if (dto.getFechaEntrada() != null) {
            existing.setFechaEntrada(dto.getFechaEntrada());
        }

        existing = estanciaRepository.update(existing);
        return EstanciaMapper.toDTO(existing);
    }

    public void deleteEstancia(String id) {
        estanciaRepository.deleteById(UUID.fromString(id));
    }
}
