package com.knspatavardhan.currenciesconverterwrapper.provider;

import org.springframework.stereotype.Component;

@Component
public class ConversionRateProviderFactory {

    private final FawazAhmedConversionRateProvider fawazAhmedConversionRateProvider;
    public ConversionRateProviderFactory(final FawazAhmedConversionRateProvider fawazAhmedConversionRateProvider) {
        this.fawazAhmedConversionRateProvider = fawazAhmedConversionRateProvider;
    }

    public IConversionRateProvider getInstance() {
        // factory of conversion rate providers
        return fawazAhmedConversionRateProvider;
    }
}
