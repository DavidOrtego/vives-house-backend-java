package es.vives.domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Estancia {
    private UUID id;
    private UUID idCasa;
    private UUID idUsuario;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;

    public Estancia() {
        this.id = UUID.randomUUID();
    }

    public Estancia(UUID id, UUID idCasa, UUID idUsuario, LocalDate fechaEntrada, LocalDate fechaSalida) {
        this.id = id;
        this.idCasa = idCasa;
        this.idUsuario = idUsuario;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdCasa() {
        return idCasa;
    }

    public void setIdCasa(UUID idCasa) {
        this.idCasa = idCasa;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estancia estancia = (Estancia) o;
        return Objects.equals(id, estancia.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Estancia{" +
                "id=" + id +
                ", idCasa=" + idCasa +
                ", idUsuario=" + idUsuario +
                ", fechaEntrada=" + fechaEntrada +
                ", fechaSalida=" + fechaSalida +
                '}';
    }
}
