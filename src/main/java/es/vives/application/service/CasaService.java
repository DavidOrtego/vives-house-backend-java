package es.vives.application.service;

import es.vives.application.dto.CasaDTO;
import es.vives.application.dto.CasaMapper;
import es.vives.domain.Casa;
import es.vives.domain.repository.CasaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class CasaService {
    private final CasaRepository casaRepository;

    public CasaService(CasaRepository casaRepository) {
        this.casaRepository = casaRepository;
    }

    public CasaDTO createCasa(CasaDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la casa no puede estar vacío");
        }
        if (dto.getDireccion() == null || dto.getDireccion().trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía");
        }
        
        Casa casa = CasaMapper.toEntity(dto);
        casa = casaRepository.save(casa);
        return CasaMapper.toDTO(casa);
    }

    public Optional<CasaDTO> getCasaById(String id) {
        return casaRepository.findById(UUID.fromString(id))
                .map(CasaMapper::toDTO);
    }

    public List<CasaDTO> getAllCasas() {
        return casaRepository.findAll().stream()
                .map(CasaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CasaDTO updateCasa(String id, CasaDTO dto) {
        Optional<Casa> existingCasaOpt = casaRepository.findById(UUID.fromString(id));
        if (existingCasaOpt.isEmpty()) {
            throw new IllegalArgumentException("Casa no encontrada");
        }

        Casa casaToUpdate = existingCasaOpt.get();
        if (dto.getNombre() != null && !dto.getNombre().trim().isEmpty()) {
            casaToUpdate.setNombre(dto.getNombre());
        }
        if (dto.getDireccion() != null && !dto.getDireccion().trim().isEmpty()) {
            casaToUpdate.setDireccion(dto.getDireccion());
        }

        casaToUpdate = casaRepository.update(casaToUpdate);
        return CasaMapper.toDTO(casaToUpdate);
    }

    public void deleteCasa(String id) {
        casaRepository.deleteById(UUID.fromString(id));
    }
}
