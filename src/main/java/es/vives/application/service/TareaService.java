package es.vives.application.service;

import es.vives.application.dto.TareaDTO;
import es.vives.application.dto.TareaMapper;
import es.vives.domain.Tarea;
import es.vives.domain.repository.TareaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class TareaService {
    private final TareaRepository tareaRepository;

    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public TareaDTO createTarea(TareaDTO dto) {
        if (dto.getDescripcion() == null || dto.getDescripcion().isEmpty()) {
            throw new IllegalArgumentException("La descripción es obligatoria");
        }
        
        Tarea tarea = TareaMapper.toEntity(dto);
        tarea = tareaRepository.save(tarea);
        return TareaMapper.toDTO(tarea);
    }

    public Optional<TareaDTO> getTareaById(String id) {
        return tareaRepository.findById(UUID.fromString(id))
                .map(TareaMapper::toDTO);
    }

    public List<TareaDTO> getAllTareas() {
        return tareaRepository.findAll().stream()
                .map(TareaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TareaDTO updateTarea(String id, TareaDTO dto) {
        Optional<Tarea> existingOpt = tareaRepository.findById(UUID.fromString(id));
        if (existingOpt.isEmpty()) {
            throw new IllegalArgumentException("Tarea no encontrada");
        }

        Tarea existing = existingOpt.get();
        if (dto.getDescripcion() != null && !dto.getDescripcion().isEmpty()) {
            existing.setDescripcion(dto.getDescripcion());
        }
        if (dto.getEstado() != null && !dto.getEstado().isEmpty()) {
            existing.setEstado(dto.getEstado());
        }
        if (dto.getIdUsuarioAsignado() != null) {
            existing.setIdUsuarioAsignado(UUID.fromString(dto.getIdUsuarioAsignado()));
        }

        existing = tareaRepository.update(existing);
        return TareaMapper.toDTO(existing);
    }

    public void deleteTarea(String id) {
        tareaRepository.deleteById(UUID.fromString(id));
    }
}
