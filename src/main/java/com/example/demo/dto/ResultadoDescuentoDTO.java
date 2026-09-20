package com.example.demo.dto;
import java.util.List;

public class ResultadoDescuentoDTO {
    private List<VentaDescuentoDTO> ventas;
    private Double totalConDescuento;

    public List<VentaDescuentoDTO> getVentas() { return ventas; }
    public void setVentas(List<VentaDescuentoDTO> ventas) { this.ventas = ventas; }
    public Double getTotalConDescuento() { return totalConDescuento; }
    public void setTotalConDescuento(Double totalConDescuento) { this.totalConDescuento = totalConDescuento; }
}