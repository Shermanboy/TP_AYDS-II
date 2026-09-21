package com.example.demo.dto;

public class EstadisticasDTO {
    private Double totalFacturado;
    private Integer cantidadVentas;
    private Double ticketPromedio;
    private VentaDTO ventaMayor;
    private VentaDTO ventaMenor;
    private String productoMasVendido;

    
    public Double getTotalFacturado() { return totalFacturado; }
    public void setTotalFacturado(Double totalFacturado) { this.totalFacturado = totalFacturado; }
    public Integer getCantidadVentas() { return cantidadVentas; }
    public void setCantidadVentas(Integer cantidadVentas) { this.cantidadVentas = cantidadVentas; }
    public Double getTicketPromedio() { return ticketPromedio; }
    public void setTicketPromedio(Double ticketPromedio) { this.ticketPromedio = ticketPromedio; }
    public VentaDTO getVentaMayor() { return ventaMayor; }
    public void setVentaMayor(VentaDTO ventaMayor) { this.ventaMayor = ventaMayor; }
    public VentaDTO getVentaMenor() { return ventaMenor; }
    public void setVentaMenor(VentaDTO ventaMenor) { this.ventaMenor = ventaMenor; }
    public String getProductoMasVendido() { return productoMasVendido; }
    public void setProductoMasVendido(String productoMasVendido) { this.productoMasVendido = productoMasVendido; }
}