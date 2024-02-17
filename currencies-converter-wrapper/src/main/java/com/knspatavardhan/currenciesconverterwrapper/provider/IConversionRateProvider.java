package com.knspatavardhan.currenciesconverterwrapper.provider;

public interface IConversionRateProvider {
    double getConversionRate(final String fromCurrencyCode, final String toCurrencyCode, final String date);
}