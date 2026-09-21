package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.Producto;
import com.example.demo.service.CatalogoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/catalogo")
public class CatalogoController {

    private final CatalogoService catalogoService;

    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Producto>>> listarCatalogo() {
        return ResponseEntity.ok(new ApiResponse<>(200, "Exito", catalogoService.obtenerTodos()));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<Producto>>> buscar(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax) {
        
        List<Producto> filtrados = catalogoService.buscarConFiltros(categoria, precioMin, precioMax);
        return ResponseEntity.ok(new ApiResponse<>(200, "Búsqueda exitosa", filtrados));
    }

    @GetMapping("/ordenar")
    public ResponseEntity<ApiResponse<List<Producto>>> ordenar(
            @RequestParam(defaultValue = "precio") String criterio,
            @RequestParam(defaultValue = "asc") String orden) {
        
        List<Producto> ordenados = catalogoService.ordenarProductos(criterio, orden);
        return ResponseEntity.ok(new ApiResponse<>(200, "Ordenamiento exitoso", ordenados));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Producto>> agregar(@Valid @RequestBody Producto producto) {
        Producto creado = catalogoService.agregarProducto(producto);
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Producto creado", creado));
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<Object>> modificarStock(
            @PathVariable Long id, 
            @RequestParam Integer cantidad) {
        
        Optional<Producto> prodOpt = catalogoService.buscarPorId(id);
        
        if (prodOpt.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Not Found", "El producto no existe"));
        }

        Producto p = prodOpt.get();
        if (p.getStock() + cantidad < 0) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "Bad Request", "El stock no puede quedar negativo"));
        }

        p.setStock(p.getStock() + cantidad);
        return ResponseEntity.ok(new ApiResponse<>(200, "Stock actualizado", p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> eliminar(@PathVariable Long id) {
        boolean eliminado = catalogoService.eliminarProducto(id);
        if (!eliminado) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Not Found", "El producto no existe"));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Producto eliminado", null));
    }
}