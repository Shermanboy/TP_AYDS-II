package com.example.demo.service;

import com.example.demo.dto.Respuestaapi;
import com.example.demo.dto.FrankFuster;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Service
public class apiservice {

    private final RestClient restClient;

    public apiservice() {
        // Inicializamos el cliente apuntando a la URL base de Frankfurter
        this.restClient = RestClient.builder().baseUrl("https://api.frankfurter.app").build();
    }

    public Respuestaapi convertirDivisa(Double monto, String origen, String destino) {
        try {
            // Hacemos la llamada HTTP real a la API externa
            FrankFuster externalResponse = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/latest")
                            .queryParam("amount", monto)
                            .queryParam("from", origen.toUpperCase())
                            .queryParam("to", destino.toUpperCase())
                            .build())
                    .retrieve()
                    .body(FrankFuster.class);

            if (externalResponse == null || !externalResponse.getRates().containsKey(destino.toUpperCase())) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "No se encontraron tasas para la moneda destino");
            }

            // Armamos nuestra propia respuesta
            Respuestaapi response = new Respuestaapi();
            response.setMontoOriginal(monto);
            response.setMonedaOrigen(origen.toUpperCase());
            response.setMonedaDestino(destino.toUpperCase());
            response.setFecha(externalResponse.getDate());
            
            // Frankfurter devuelve el monto ya multiplicado en "rates" si le pasamos "amount"
            double montoConvertido = externalResponse.getRates().get(destino.toUpperCase());
            response.setMontoConvertido(montoConvertido);
            response.setTasaCambio(montoConvertido / monto); // Calculamos la tasa base

            return response;

        } catch (Exception e) {
            // Si Frankfurter explota (timeout, moneda inválida, etc), devolvemos 502 como pide el TP
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error al comunicarse con la API externa: " + e.getMessage());
        }
    }
}