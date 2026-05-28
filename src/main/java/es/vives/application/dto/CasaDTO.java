package es.vives.application.dto;

import java.util.UUID;

public class CasaDTO {
    private String id;
    private String nombre;
    private String direccion;

    public CasaDTO() {}

    public CasaDTO(String id, String nombre, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    @Override
    public String toString() {
        return "CasaDTO{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
