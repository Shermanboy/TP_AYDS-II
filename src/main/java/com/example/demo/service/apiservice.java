package com.example.demo.service;

import com.example.demo.dto.Respuestaapi;
import com.example.demo.dto.FrankFuster;
import com.example.demo.dto.HistorialCotizacionDTO;
import com.example.demo.model.HistorialConversion;
import com.example.demo.repository.HistorialConversionRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class apiservice {

    private final RestClient restClient;
    private final HistorialConversionRepo historialRepository;

    public apiservice(HistorialConversionRepo historialRepository) {
        this.historialRepository = historialRepository; // Corregido: antes decía this.historialRepo
        this.restClient = RestClient.builder().baseUrl("https://api.frankfurter.app").build();
    }

    public Respuestaapi consultarYGuardar(Double monto, String origen, String destino) {
        try {
            FrankFuster externalResponse = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/latest")
                            .queryParam("amount", monto)
                            .queryParam("from", origen.toUpperCase())
                            .queryParam("to", destino.toUpperCase())
                            .build())
                    .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                    .header("User-Agent", "Mozilla/5.0")
                    .retrieve()
                    .body(FrankFuster.class);

            if (externalResponse == null || !externalResponse.getRates().containsKey(destino.toUpperCase())) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "No se encontraron tasas para la moneda destino");
            }

            double montoConvertido = externalResponse.getRates().get(destino.toUpperCase());
            double tasaBase = montoConvertido / monto;

            // Guardamos en la base de datos usando la Entidad, no el Repo
            HistorialConversion historial = new HistorialConversion(); 
            historial.setMonedaOrigen(origen.toUpperCase());
            historial.setMonedaDestino(destino.toUpperCase());
            historial.setMonto(monto);
            historial.setMontoConvertido(montoConvertido);
            historial.setTasa(tasaBase);
            historial.setFechaConsulta(LocalDateTime.now());
            
            // Usamos el Repo para guardar la Entidad
            historialRepository.save(historial); 

            // Armamos la respuesta para el cliente
            Respuestaapi response = new Respuestaapi();
            response.setMontoOriginal(monto);
            response.setMonedaOrigen(origen.toUpperCase());
            response.setMonedaDestino(destino.toUpperCase());
            response.setFecha(externalResponse.getDate());
            response.setMontoConvertido(montoConvertido);
            response.setTasaCambio(tasaBase);

            return response;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error al comunicarse con la API externa: " + e.getMessage());
        }
    }

    public List<HistorialCotizacionDTO> obtenerHistorial(String origen, String destino) {
        // Buscamos una lista de Entidades
        List<HistorialConversion> registros = historialRepository.buscarHistorial(origen.toUpperCase(), destino.toUpperCase());
        List<HistorialCotizacionDTO> respuesta = new ArrayList<>();
        
        for (HistorialConversion h : registros) {
            HistorialCotizacionDTO dto = new HistorialCotizacionDTO();
            dto.setFecha(h.getFechaConsulta());
            dto.setTasaCambio(h.getTasa());
            respuesta.add(dto);
        }
        return respuesta;
    }
}