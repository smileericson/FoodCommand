package com.example.foodcommand.infra.external;

import com.example.foodcommand.application.DTOs.ConsultaCnpjResponse;
import com.example.foodcommand.domain.interfaces.ICnpjExternalRepository;
import com.example.foodcommand.infra.dto.BrasilApiCnpjResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class BrasilApiCnpjExternalRepository implements ICnpjExternalRepository {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10)).build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ConsultaCnpjResponse consultarCnpj(String cnpj) {
        var url = "https://brasilapi.com.br/api/cnpj/v1/" + cnpj.replaceAll("[^0-9]", "");
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                    .timeout(Duration.ofSeconds(15)).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                BrasilApiCnpjResponse dados = objectMapper.readValue(response.body(), BrasilApiCnpjResponse.class);
                return new ConsultaCnpjResponse(dados.getRazaoSocial(), dados.getNomeFantasia(), dados.getCep(),
                        dados.getLogradouro(), dados.getBairro(), dados.getCidade(), dados.getUf());
            }
            if (response.statusCode() == 400 || response.statusCode() == 404) {
                return null;
            }
            throw new RuntimeException("Serviço de consulta de CNPJ indisponível (HTTP " + response.statusCode() + ")");

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Serviço de consulta de CNPJ indisponível", e);
        }
    }
}
