package es.vives.application.service;

import es.vives.application.dto.UsuarioDTO;
import es.vives.application.dto.UsuarioMapper;
import es.vives.domain.Usuario;
import es.vives.domain.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDTO createUsuario(UsuarioDTO dto) {
        if (dto.getEmail() == null || !dto.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        
        Usuario usuario = UsuarioMapper.toEntity(dto);
        usuario = usuarioRepository.save(usuario);
        return UsuarioMapper.toDTO(usuario);
    }

    public Optional<UsuarioDTO> getUsuarioById(String id) {
        return usuarioRepository.findById(UUID.fromString(id))
                .map(UsuarioMapper::toDTO);
    }

    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO updateUsuario(String id, UsuarioDTO dto) {
        Optional<Usuario> existingOpt = usuarioRepository.findById(UUID.fromString(id));
        if (existingOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        Usuario existing = existingOpt.get();
        if (dto.getNombre() != null) existing.setNombre(dto.getNombre());
        if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
        if (dto.getPassword() != null) existing.setPassword(dto.getPassword());
        if (dto.getRol() != null) existing.setRol(dto.getRol());

        existing = usuarioRepository.update(existing);
        return UsuarioMapper.toDTO(existing);
    }

    public void deleteUsuario(String id) {
        usuarioRepository.deleteById(UUID.fromString(id));
    }
}
