package es.vives.application.dto;

import java.time.LocalDate;

public class EstanciaDTO {
    private String id;
    private String idCasa;
    private String idUsuario;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;

    public EstanciaDTO() {}

    public EstanciaDTO(String id, String idCasa, String idUsuario, LocalDate fechaEntrada, LocalDate fechaSalida) {
        this.id = id;
        this.idCasa = idCasa;
        this.idUsuario = idUsuario;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIdCasa() { return idCasa; }
    public void setIdCasa(String idCasa) { this.idCasa = idCasa; }
    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
    public LocalDate getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDate fechaEntrada) { this.fechaEntrada = fechaEntrada; }
    public LocalDate getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDate fechaSalida) { this.fechaSalida = fechaSalida; }

    @Override
    public String toString() {
        return "EstanciaDTO{" +
                "id='" + id + '\'' +
                ", idCasa='" + idCasa + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", fechaEntrada=" + fechaEntrada +
                ", fechaSalida=" + fechaSalida +
                '}';
    }
}
