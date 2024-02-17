package com.knspatavardhan.currenciesconverterwrapper.client;

import com.knspatavardhan.currenciesconverterwrapper.error.RuntimeWebException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ConversionRatesWebClient {

    private final WebClient webClient;

    public ConversionRatesWebClient(final String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Double getRate(final String pathToConversionRate,
                          final String apiVersion,
                          final String date,
                          final String from,
                          final String to) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(pathToConversionRate)
                        .build(apiVersion, date, from, to))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse -> {
                  throw new RuntimeWebException.Builder()
                          .message(String.format("Conversion rates not found for date %s from %s to %s", date, from, to))
                          .httpStatus(HttpStatus.valueOf(errorResponse.statusCode().value()))
                          .build();
                })
                .bodyToMono(String.class)
                .flatMap(responseBody -> Mono.just(JsonObjectMapper.readPropertyDouble(responseBody, to)))
                .block();
    }
}
