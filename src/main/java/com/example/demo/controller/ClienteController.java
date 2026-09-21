package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.model.Cliente;
import com.example.demo.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

   
    @PostMapping
    public ResponseEntity<ApiResponse<Cliente>> altaSimple(@RequestBody ClienteDTO clienteDTO) {
        Cliente creado = clienteService.guardarCliente(clienteDTO);
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Cliente creado", creado));
    }

    
    @PostMapping("/validado")
    public ResponseEntity<ApiResponse<Object>> altaValidada(@Valid @RequestBody ClienteDTO clienteDTO) {
        
        if (clienteService.existeEmail(clienteDTO.getEmail())) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "El email ya está registrado", null));
        }
        
        Cliente creado = clienteService.guardarCliente(clienteDTO);
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Cliente creado", creado));
    }
}