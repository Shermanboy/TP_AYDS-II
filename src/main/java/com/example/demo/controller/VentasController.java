package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.EstadisticasDTO;
import com.example.demo.dto.ResultadoDescuentoDTO;
import com.example.demo.dto.VentaDTO;
import com.example.demo.service.VentasService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentasController {

    private final VentasService ventasService;

    // Inyección de dependencias
    public VentasController(VentasService ventasService) {
        this.ventasService = ventasService;
    }

    @PostMapping("/estadisticas")
    public ResponseEntity<ApiResponse<EstadisticasDTO>> calcularEstadisticas(@Valid @RequestBody List<VentaDTO> ventas) {
        
        EstadisticasDTO stats = ventasService.procesarEstadisticas(ventas);
        
        ApiResponse<EstadisticasDTO> response = new ApiResponse<>(200, "Operacion realizada con exito", stats);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/aplicar-descuento")
    public ResponseEntity<ApiResponse<Object>> aplicarDescuento(
            @Valid @RequestBody List<VentaDTO> ventas, 
            @RequestParam Double porcentaje) {
        
        if (ventas == null || ventas.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "Bad Request", "La lista no puede estar vacía"));
        }

        if (porcentaje < 0 || porcentaje > 100) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "Bad Request", "El porcentaje debe estar entre 0 y 100"));
        }

        ResultadoDescuentoDTO resultado = ventasService.aplicarDescuento(ventas, porcentaje);
        
        return ResponseEntity.ok(new ApiResponse<>(200, "Operacion realizada con exito", resultado));
    }
}