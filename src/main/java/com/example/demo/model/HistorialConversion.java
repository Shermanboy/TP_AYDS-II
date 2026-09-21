package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_conversiones")
public class HistorialConversion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "moneda_origen")
    private String monedaOrigen;

    @Column(name = "moneda_destino")
    private String monedaDestino;

    private Double monto;

    @Column(name = "monto_convertido")
    private Double montoConvertido;

    private Double tasa;

    @Column(name = "fecha_consulta")
    private LocalDateTime fechaConsulta;

    public HistorialConversion() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMonedaOrigen() { return monedaOrigen; }
    public void setMonedaOrigen(String monedaOrigen) { this.monedaOrigen = monedaOrigen; }
    public String getMonedaDestino() { return monedaDestino; }
    public void setMonedaDestino(String monedaDestino) { this.monedaDestino = monedaDestino; }
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    public Double getMontoConvertido() { return montoConvertido; }
    public void setMontoConvertido(Double montoConvertido) { this.montoConvertido = montoConvertido; }
    public Double getTasa() { return tasa; }
    public void setTasa(Double tasa) { this.tasa = tasa; }
    public LocalDateTime getFechaConsulta() { return fechaConsulta; }
    public void setFechaConsulta(LocalDateTime fechaConsulta) { this.fechaConsulta = fechaConsulta; }
}