package es.vives.application.dto;

public class TareaDTO {
    private String id;
    private String descripcion;
    private String estado;
    private String idCasa;
    private String idUsuarioAsignado;

    public TareaDTO() {}

    public TareaDTO(String id, String descripcion, String estado, String idCasa, String idUsuarioAsignado) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
        this.idCasa = idCasa;
        this.idUsuarioAsignado = idUsuarioAsignado;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getIdCasa() { return idCasa; }
    public void setIdCasa(String idCasa) { this.idCasa = idCasa; }
    public String getIdUsuarioAsignado() { return idUsuarioAsignado; }
    public void setIdUsuarioAsignado(String idUsuarioAsignado) { this.idUsuarioAsignado = idUsuarioAsignado; }

    @Override
    public String toString() {
        return "TareaDTO{" +
                "id='" + id + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", idCasa='" + idCasa + '\'' +
                ", idUsuarioAsignado='" + idUsuarioAsignado + '\'' +
                '}';
    }
}
