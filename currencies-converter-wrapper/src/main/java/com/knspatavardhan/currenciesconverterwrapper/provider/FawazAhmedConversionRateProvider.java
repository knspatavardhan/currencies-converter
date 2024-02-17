package com.knspatavardhan.currenciesconverterwrapper.provider;

import com.knspatavardhan.currenciesconverterwrapper.client.ConversionRatesWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FawazAhmedConversionRateProvider implements IConversionRateProvider {
    private final String apiVersion;
    private final String conversionRatePath;
    private final ConversionRatesWebClient webClient;

    @Autowired
    public FawazAhmedConversionRateProvider(
            @Value("${provider.fawazahmed.base-url}") final String baseUrl,
            @Value("${provider.fawazahmed.api-version}") final String apiVersion,
            @Value("${provider.fawazahmed.path-to-conversion-rate}") final String path
    ) {
        this.webClient = new ConversionRatesWebClient(baseUrl);
        this.apiVersion = apiVersion;
        this.conversionRatePath = path;
    }

    @Override
    public double getConversionRate(String fromCurrencyCode, String toCurrencyCode, final String date) {
        return webClient.getRate(conversionRatePath, apiVersion, date, fromCurrencyCode, toCurrencyCode);
    }
}
