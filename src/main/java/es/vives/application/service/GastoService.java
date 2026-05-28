package es.vives.application.service;

import es.vives.application.dto.GastoDTO;
import es.vives.application.dto.GastoMapper;
import es.vives.domain.Gasto;
import es.vives.domain.repository.GastoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class GastoService {
    private final GastoRepository gastoRepository;

    public GastoService(GastoRepository gastoRepository) {
        this.gastoRepository = gastoRepository;
    }

    public GastoDTO createGasto(GastoDTO dto) {
        if (dto.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        
        Gasto gasto = GastoMapper.toEntity(dto);
        gasto = gastoRepository.save(gasto);
        return GastoMapper.toDTO(gasto);
    }

    public Optional<GastoDTO> getGastoById(String id) {
        return gastoRepository.findById(UUID.fromString(id))
                .map(GastoMapper::toDTO);
    }

    public List<GastoDTO> getAllGastos() {
        return gastoRepository.findAll().stream()
                .map(GastoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public GastoDTO updateGasto(String id, GastoDTO dto) {
        Optional<Gasto> existingOpt = gastoRepository.findById(UUID.fromString(id));
        if (existingOpt.isEmpty()) {
            throw new IllegalArgumentException("Gasto no encontrado");
        }

        Gasto existing = existingOpt.get();
        if (dto.getDescripcion() != null && !dto.getDescripcion().isEmpty()) {
            existing.setDescripcion(dto.getDescripcion());
        }
        if (dto.getCantidad() > 0) {
            existing.setCantidad(dto.getCantidad());
        }

        existing = gastoRepository.update(existing);
        return GastoMapper.toDTO(existing);
    }

    public void deleteGasto(String id) {
        gastoRepository.deleteById(UUID.fromString(id));
    }
}
