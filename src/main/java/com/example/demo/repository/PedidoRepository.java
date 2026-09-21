package com.example.demo.repository;

import com.example.demo.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT DISTINCT p FROM Pedido p " +
           "WHERE (:clienteId IS NULL OR p.cliente.id = :clienteId) " +
           "AND (:estado IS NULL OR p.estado = :estado) " +
           "AND (cast(:fechaDesde as date) IS NULL OR p.fechaPedido >= :fechaDesde) " +
           "AND (cast(:fechaHasta as date) IS NULL OR p.fechaPedido <= :fechaHasta) " +
           "AND (:categoria IS NULL OR EXISTS (SELECT 1 FROM DetallePedido dp WHERE dp.pedido = p AND dp.producto.categoria.nombre = :categoria))")
    List<Pedido> buscarConFiltros(
            @Param("clienteId") Long clienteId,
            @Param("categoria") String categoria,
            @Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta,
            @Param("estado") String estado);
}