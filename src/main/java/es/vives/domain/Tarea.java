package es.vives.domain;

import java.util.Objects;
import java.util.UUID;

public class Tarea {
    private UUID id;
    private String descripcion;
    private String estado; // PENDIENTE, EN_PROGRESO, COMPLETADA
    private UUID idCasa;
    private UUID idUsuarioAsignado;

    public Tarea() {
        this.id = UUID.randomUUID();
    }

    public Tarea(UUID id, String descripcion, String estado, UUID idCasa, UUID idUsuarioAsignado) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
        this.idCasa = idCasa;
        this.idUsuarioAsignado = idUsuarioAsignado;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public UUID getIdCasa() {
        return idCasa;
    }

    public void setIdCasa(UUID idCasa) {
        this.idCasa = idCasa;
    }

    public UUID getIdUsuarioAsignado() {
        return idUsuarioAsignado;
    }

    public void setIdUsuarioAsignado(UUID idUsuarioAsignado) {
        this.idUsuarioAsignado = idUsuarioAsignado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarea tarea = (Tarea) o;
        return Objects.equals(id, tarea.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", idCasa=" + idCasa +
                ", idUsuarioAsignado=" + idUsuarioAsignado +
                '}';
    }
}
