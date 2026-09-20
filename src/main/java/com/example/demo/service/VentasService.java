package com.example.demo.service;

import com.example.demo.dto.EstadisticasDTO;
import com.example.demo.dto.ResultadoDescuentoDTO;
import com.example.demo.dto.VentaDTO;
import com.example.demo.dto.VentaDescuentoDTO;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class VentasService {

    public EstadisticasDTO procesarEstadisticas(List<VentaDTO> ventas) {
        double totalFacturado = 0;
        VentaDTO ventaMayor = ventas.get(0);
        VentaDTO ventaMenor = ventas.get(0);
        Map<String, Integer> cantidadesPorProducto = new HashMap<>();

        for (VentaDTO v : ventas) {
            double monto = v.getCantidad() * v.getPrecioUnitario();
            totalFacturado += monto;

            // Lógica para venta mayor y menor
            if (monto > (ventaMayor.getCantidad() * ventaMayor.getPrecioUnitario())) ventaMayor = v;
            if (monto < (ventaMenor.getCantidad() * ventaMenor.getPrecioUnitario())) ventaMenor = v;

            // Acumulador para producto más vendido
            cantidadesPorProducto.put(v.getProducto(), 
                cantidadesPorProducto.getOrDefault(v.getProducto(), 0) + v.getCantidad());
        }

        String productoMasVendido = Collections.max(cantidadesPorProducto.entrySet(), Map.Entry.comparingByValue()).getKey();

        EstadisticasDTO stats = new EstadisticasDTO();
        stats.setTotalFacturado(totalFacturado);
        stats.setCantidadVentas(ventas.size());
        stats.setTicketPromedio(totalFacturado / ventas.size());
        stats.setVentaMayor(ventaMayor);
        stats.setVentaMenor(ventaMenor);
        stats.setProductoMasVendido(productoMasVendido);

        return stats;
    }

    public ResultadoDescuentoDTO aplicarDescuento(List<VentaDTO> ventas, Double porcentaje) {
        double totalConDescuento = 0;
        List<VentaDescuentoDTO> ventasProcesadas = new ArrayList<>();

        for (VentaDTO v : ventas) {
            VentaDescuentoDTO dto = new VentaDescuentoDTO();
            dto.setProducto(v.getProducto());
            dto.setCantidad(v.getCantidad());
            dto.setPrecioUnitario(v.getPrecioUnitario());

            // Calcula el monto de esa venta y le resta el porcentaje
            double montoOriginal = v.getCantidad() * v.getPrecioUnitario();
            double montoDescuento = montoOriginal - (montoOriginal * (porcentaje / 100));
            dto.setMontoConDescuento(montoDescuento);
            
            ventasProcesadas.add(dto);
            totalConDescuento += montoDescuento;
        }

        ResultadoDescuentoDTO resultado = new ResultadoDescuentoDTO();
        resultado.setVentas(ventasProcesadas);
        resultado.setTotalConDescuento(totalConDescuento);

        return resultado;
    }
}