package com.example.demo.dto;

import java.time.LocalDateTime;

public class HistorialCotizacionDTO {
    private LocalDateTime fecha;
    private Double tasaCambio;

    public HistorialCotizacionDTO() {}

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public Double getTasaCambio() { return tasaCambio; }
    public void setTasaCambio(Double tasaCambio) { this.tasaCambio = tasaCambio; }
}