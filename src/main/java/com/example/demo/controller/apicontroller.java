package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.Respuestaapi;
import com.example.demo.service.apiservice;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/divisas")
@Validated // Obligatorio para que funcionen las validaciones en los @RequestParam
public class apicontroller {
    private final apiservice apiservice;

    public apicontroller(apiservice apiservice) {
        this.apiservice = apiservice;
    }

    @GetMapping("/convertir")
    public ResponseEntity<ApiResponse<Object>> convertir(
            @RequestParam @Positive(message = "El monto debe ser mayor que 0") Double monto,
            @RequestParam @Size(min = 3, max = 3, message = "El origen debe tener 3 letras") String origen,
            @RequestParam @Size(min = 3, max = 3, message = "El destino debe tener 3 letras") String destino) {
        
        try {
            Respuestaapi resultado = apiservice.convertirDivisa(monto, origen, destino);
            return ResponseEntity.ok(new ApiResponse<>(200, "Conversión exitosa", resultado));
            
        } catch (ResponseStatusException ex) {
            // Atrapamos el error 502 del servicio y lo envolvemos en nuestro ApiResponse
            return ResponseEntity.status(ex.getStatusCode().value())
                    .body(new ApiResponse<>(ex.getStatusCode().value(), ex.getReason(), null));
        }
    }
}