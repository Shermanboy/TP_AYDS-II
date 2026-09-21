package com.example.demo.service;

import com.example.demo.dto.PedidoResponseDTO;
import com.example.demo.dto.ProductoPedidoDTO;
import com.example.demo.model.DetallePedido;
import com.example.demo.model.Pedido;
import com.example.demo.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<PedidoResponseDTO> buscarPedidos(Long clienteId, String categoria, LocalDate fechaDesde, LocalDate fechaHasta, String estado) {
        List<Pedido> pedidos = pedidoRepository.buscarConFiltros(clienteId, categoria, fechaDesde, fechaHasta, estado);
        List<PedidoResponseDTO> respuesta = new ArrayList<>();

        for (Pedido p : pedidos) {
            PedidoResponseDTO dto = new PedidoResponseDTO();
            dto.setPedidoId(p.getId());
            dto.setCliente(p.getCliente().getNombre() + " " + p.getCliente().getApellido());
            dto.setFecha(p.getFechaPedido());
            dto.setEstado(p.getEstado());

            double total = 0.0;
            List<ProductoPedidoDTO> productosDto = new ArrayList<>();
            
            if (p.getDetalles() != null) {
                for (DetallePedido dp : p.getDetalles()) {
                    ProductoPedidoDTO pdto = new ProductoPedidoDTO();
                    pdto.setNombre(dp.getProducto().getNombre());
                    
                    
                    if (dp.getProducto().getCategoria() != null) {
                        pdto.setCategoria(dp.getProducto().getCategoria().getNombre());
                    } else {
                        pdto.setCategoria("Sin Categoria");
                    }
                    
                    pdto.setCantidad(dp.getCantidad());
                    
                    double subtotal = dp.getCantidad() * dp.getPrecioUnitario();
                    pdto.setSubtotal(subtotal);
                    total += subtotal;
                    
                    productosDto.add(pdto);
                }
            }

            dto.setProductos(productosDto);
            dto.setTotalPedido(total);
            respuesta.add(dto);
        }

        return respuesta;
    }
}