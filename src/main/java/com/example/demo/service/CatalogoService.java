package com.example.demo.service;

import com.example.demo.model.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CatalogoService {
    private final List<Producto> productos = new ArrayList<>();
    private Long idCounter = 9L;

    public CatalogoService() {
       
        productos.add(new Producto(1L, "Mouse G203", "Perifericos", 25000.0, 10));
        productos.add(new Producto(2L, "Teclado Mecanico", "Perifericos", 45000.0, 5));
        productos.add(new Producto(3L, "Monitor 24", "Monitores", 150000.0, 8));
        productos.add(new Producto(4L, "Monitor 27", "Monitores", 200000.0, 3));
        productos.add(new Producto(5L, "Auriculares 7.1", "Audio", 35000.0, 15));
        productos.add(new Producto(6L, "Microfono USB", "Audio", 40000.0, 7));
        productos.add(new Producto(7L, "Gabinete ATX", "Componentes", 55000.0, 4));
        productos.add(new Producto(8L, "Fuente 600W", "Componentes", 65000.0, 12));
    }

    public List<Producto> obtenerTodos() {
        return productos;
    }

    public List<Producto> buscarConFiltros(String categoria, Double precioMin, Double precioMax) {
        return productos.stream()
                .filter(p -> categoria == null || p.getCategoria().equalsIgnoreCase(categoria))
                .filter(p -> precioMin == null || p.getPrecio() >= precioMin)
                .filter(p -> precioMax == null || p.getPrecio() <= precioMax)
                .collect(Collectors.toList());
    }

    public List<Producto> ordenarProductos(String criterio, String orden) {
        Comparator<Producto> comparador;
        if ("nombre".equalsIgnoreCase(criterio)) {
            comparador = Comparator.comparing(Producto::getNombre);
        } else { 
            comparador = Comparator.comparing(Producto::getPrecio);
        }

        if ("desc".equalsIgnoreCase(orden)) {
            comparador = comparador.reversed();
        }

        return productos.stream()
                .sorted(comparador)
                .collect(Collectors.toList());
    }

    public Producto agregarProducto(Producto p) {
        p.setId(idCounter++);
        productos.add(p);
        return p;
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productos.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public boolean eliminarProducto(Long id) {
        return productos.removeIf(p -> p.getId().equals(id));
    }
}