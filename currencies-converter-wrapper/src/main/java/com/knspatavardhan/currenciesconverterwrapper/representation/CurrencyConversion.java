package com.knspatavardhan.currenciesconverterwrapper.representation;

import com.knspatavardhan.currenciesconverterwrapper.model.Currency;

public class CurrencyConversion {

    public CurrencyConversion() {
    }

    public CurrencyConversion(final Currency fromCurrency,
                              final Double inputQuantity,
                              final Currency toCurrency,
                              final String outputQuantity,
                              final String date) {
        this.fromCurrency = fromCurrency;
        this.inputQuantity = inputQuantity;
        this.toCurrency = toCurrency;
        this.outputQuantity = outputQuantity;
        this.date = date;
    }

    private Currency fromCurrency;

    private Double inputQuantity;

    private Currency toCurrency;

    private String outputQuantity;

    private String date;

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public Double getInputQuantity() {
        return inputQuantity;
    }

    public void setInputQuantity(Double inputQuantity) {
        this.inputQuantity = inputQuantity;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(Currency toCurrency) {
        this.toCurrency = toCurrency;
    }

    public String getOutputQuantity() {
        return outputQuantity;
    }

    public void setOutputQuantity(String outputQuantity) {
        this.outputQuantity = outputQuantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CurrencyConversion{" +
                "fromCurrency=" + fromCurrency +
                ", inputQuantity=" + inputQuantity +
                ", toCurrency=" + toCurrency +
                ", outputQuantity=" + outputQuantity +
                ", date=" + date +
                '}';
    }
}
