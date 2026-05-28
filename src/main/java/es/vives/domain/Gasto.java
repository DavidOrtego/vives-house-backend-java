package es.vives.domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Gasto {
    private UUID id;
    private String descripcion;
    private double cantidad;
    private LocalDate fecha;
    private UUID idCasa;
    private UUID idUsuario;

    public Gasto() {
        this.id = UUID.randomUUID();
    }

    public Gasto(UUID id, String descripcion, double cantidad, LocalDate fecha, UUID idCasa, UUID idUsuario) {
        this.id = id;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.idCasa = idCasa;
        this.idUsuario = idUsuario;
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

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gasto gasto = (Gasto) o;
        return Objects.equals(id, gasto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Gasto{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", cantidad=" + cantidad +
                ", fecha=" + fecha +
                ", idCasa=" + idCasa +
                ", idUsuario=" + idUsuario +
                '}';
    }
}
