package com.knspatavardhan.currenciesconverterwrapper.service;

import com.knspatavardhan.currenciesconverterwrapper.model.Currency;
import com.knspatavardhan.currenciesconverterwrapper.provider.ConversionRateProviderFactory;
import com.knspatavardhan.currenciesconverterwrapper.provider.IConversionRateProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CurrencyConversionService {

    private final IConversionRateProvider conversionRateProvider;

    CurrencyConversionService(final ConversionRateProviderFactory conversionRateProviderFactory) {
        this.conversionRateProvider = conversionRateProviderFactory.getInstance();
    }

    public String convert(final Currency from,
                          final Double quantity,
                          final Currency to,
                          final String date) {
        final double conversionRate = conversionRateProvider.getConversionRate(from.getCode(), to.getCode(), date);
        return String.format(to.getDecimalPrecision(), conversionRate * quantity);
    }
}
