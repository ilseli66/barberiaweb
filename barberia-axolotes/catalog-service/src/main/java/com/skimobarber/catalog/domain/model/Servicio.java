package com.skimobarber.catalog.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class Servicio {
    private Long id;
    private String nombre;
    private String descripcion;
    private boolean activo;
    private List<ServicioFase> fases = new ArrayList<>();

    public Servicio(Long id, String nombre, String descripcion, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
        this.fases = new ArrayList<>();
    }

    public Servicio(Long id, String nombre, String descripcion, boolean activo, List<ServicioFase> fases) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
        this.fases = fases != null ? new ArrayList<>(fases) : new ArrayList<>();
    }

    // Domain validation
    public boolean isNombreValid() {
        return nombre != null && !nombre.isBlank() && nombre.length() <= 100;
    }

    // DDD: Encapsulated collection access
    public List<ServicioFase> getFases() {
        return Collections.unmodifiableList(fases);
    }

    public void addFase(ServicioFase fase) {
        if (fase != null) {
            this.fases.add(fase);
        }
    }

    public void removeFase(ServicioFase fase) {
        this.fases.remove(fase);
    }

    public void clearFases() {
        this.fases.clear();
    }

    public int getDuracionTotalMinutos() {
        return fases.stream()
            .mapToInt(f -> f.getDuracionMinutos() != null ? f.getDuracionMinutos() : 0)
            .sum();
    }
}
