package es.vives.application.dto;

import java.time.LocalDate;

public class GastoDTO {
    private String id;
    private String descripcion;
    private double cantidad;
    private LocalDate fecha;
    private String idCasa;
    private String idUsuario;

    public GastoDTO() {}

    public GastoDTO(String id, String descripcion, double cantidad, LocalDate fecha, String idCasa, String idUsuario) {
        this.id = id;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.idCasa = idCasa;
        this.idUsuario = idUsuario;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getIdCasa() { return idCasa; }
    public void setIdCasa(String idCasa) { this.idCasa = idCasa; }
    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

    @Override
    public String toString() {
        return "GastoDTO{" +
                "id='" + id + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cantidad=" + cantidad +
                ", fecha=" + fecha +
                ", idCasa='" + idCasa + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                '}';
    }
}
