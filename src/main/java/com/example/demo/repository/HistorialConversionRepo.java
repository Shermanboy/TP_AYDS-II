package com.example.demo.repository;

import com.example.demo.model.HistorialConversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialConversionRepo extends JpaRepository<HistorialConversion, Long> {

    @Query("SELECT h FROM HistorialConversion h WHERE h.monedaOrigen = :origen AND h.monedaDestino = :destino ORDER BY h.fechaConsulta DESC")
    List<HistorialConversion> buscarHistorial(
            @Param("origen") String origen, 
            @Param("destino") String destino
    );
}