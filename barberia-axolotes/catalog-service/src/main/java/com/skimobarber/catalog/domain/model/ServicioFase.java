package com.skimobarber.catalog.domain.model;

/**
 * Representa una fase dentro de un servicio (la "receta" del servicio)
 */
public class ServicioFase {
    private Long id;
    private Long servicioId;
    private Integer orden;
    private String nombreFase;
    private Integer duracionMinutos;
    private boolean requiereEmpleado;

    public ServicioFase() {}

    public ServicioFase(Long id, Long servicioId, Integer orden, String nombreFase,
                        Integer duracionMinutos, boolean requiereEmpleado) {
        this.id = id;
        this.servicioId = servicioId;
        this.orden = orden;
        this.nombreFase = nombreFase;
        this.duracionMinutos = duracionMinutos;
        this.requiereEmpleado = requiereEmpleado;
    }

    // Domain validation
    public boolean isDuracionValid() {
        return duracionMinutos != null && duracionMinutos > 0;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getServicioId() { return servicioId; }
    public void setServicioId(Long servicioId) { this.servicioId = servicioId; }

    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }

    public String getNombreFase() { return nombreFase; }
    public void setNombreFase(String nombreFase) { this.nombreFase = nombreFase; }

    public Integer getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(Integer duracionMinutos) { this.duracionMinutos = duracionMinutos; }

    public boolean isRequiereEmpleado() { return requiereEmpleado; }
    public void setRequiereEmpleado(boolean requiereEmpleado) { this.requiereEmpleado = requiereEmpleado; }
}
