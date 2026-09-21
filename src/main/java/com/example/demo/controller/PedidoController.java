package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.PedidoResponseDTO;
import com.example.demo.service.PedidoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<PedidoResponseDTO>>> buscarPedidos(
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(required = false) String estado) {

        List<PedidoResponseDTO> resultados = pedidoService.buscarPedidos(clienteId, categoria, fechaDesde, fechaHasta, estado);
        
        return ResponseEntity.ok(new ApiResponse<>(200, "Consulta realizada correctamente", resultados));
    }
}