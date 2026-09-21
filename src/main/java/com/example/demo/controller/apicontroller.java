package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.Respuestaapi;
import com.example.demo.dto.HistorialCotizacionDTO;
import com.example.demo.service.apiservice;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/divisas")
@Validated
public class apicontroller {

    private final apiservice apiservice;

    public apicontroller(apiservice apiservice) {
        this.apiservice = apiservice;
    }

    @PostMapping("/consultar")
    public ResponseEntity<ApiResponse<Object>> consultar(
            @RequestParam @Positive(message = "El monto debe ser mayor que 0") Double monto,
            @RequestParam @Size(min = 3, max = 3, message = "El origen debe tener 3 letras") String origen,
            @RequestParam @Size(min = 3, max = 3, message = "El destino debe tener 3 letras") String destino) {
        try {
            Respuestaapi resultado = apiservice.consultarYGuardar(monto, origen, destino);
            return ResponseEntity.ok(new ApiResponse<>(200, "Consulta guardada exitosamente", resultado));
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode().value())
                    .body(new ApiResponse<>(ex.getStatusCode().value(), ex.getReason(), null));
        }
    }

    @GetMapping("/historial")
    public ResponseEntity<ApiResponse<List<HistorialCotizacionDTO>>> historial(
            @RequestParam @Size(min = 3, max = 3, message = "El origen debe tener 3 letras") String origen,
            @RequestParam @Size(min = 3, max = 3, message = "El destino debe tener 3 letras") String destino) {
        
        List<HistorialCotizacionDTO> resultados = apiservice.obtenerHistorial(origen, destino);
        return ResponseEntity.ok(new ApiResponse<>(200, "Historial recuperado exitosamente", resultados));
    }
}