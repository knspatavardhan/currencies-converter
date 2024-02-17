package com.knspatavardhan.currenciesconverterwrapper.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

public enum Currency {
    EUR("eur", "Euro", "%.2f"),
    INR("inr", "Indian Rupee", "%.2f"),
    JPY("jpy", "Japanese Yen", "%.0f"),
    USD("usd", "US Dollar", "%.2f");

    final String code;

    final String description;

    final String decimalPrecision;

    Currency(final String code,
             final String description,
             final String decimalPrecision) {
        this.code = code;
        this.description = description;
        this.decimalPrecision = decimalPrecision;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getDecimalPrecision() {
        return decimalPrecision;
    }

    public static Currency fromCode(final String code) {
        return Arrays.stream(Currency.values())
                .filter(c -> code.equals(c.code))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Unsupported currency: %s", code)));
    }

    @Override
    public String toString() {
        return "Currency{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", decimalPrecision='" + decimalPrecision + '\'' +
                '}';
    }
}
