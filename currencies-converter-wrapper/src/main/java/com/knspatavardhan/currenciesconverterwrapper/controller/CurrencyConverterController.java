package com.knspatavardhan.currenciesconverterwrapper.controller;

import com.knspatavardhan.currenciesconverterwrapper.error.RuntimeWebException;
import com.knspatavardhan.currenciesconverterwrapper.model.Currency;
import com.knspatavardhan.currenciesconverterwrapper.representation.CurrencyConversion;
import com.knspatavardhan.currenciesconverterwrapper.service.CurrencyConversionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

@RestController
@RequestMapping("/currency-conversion")
public class CurrencyConverterController {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyConverterController.class);

    private static final String DEFAULT_DATE = "latest";

    private final CurrencyConversionService conversionService;

    @Autowired
    public CurrencyConverterController(final CurrencyConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PostMapping("convert/{fromCurrency}/{toCurrency}/{quantity}")
    public ResponseEntity<CurrencyConversion> convert(
            @PathVariable final Currency fromCurrency,
            @PathVariable final Currency toCurrency,
            @PathVariable final Double quantity,
            @RequestParam(required = false, name = "date", defaultValue = DEFAULT_DATE) final String date) {
        logger.trace("Request to convert {} {} to {} with date {}", quantity, fromCurrency, toCurrency, date);
        return ResponseEntity.ok(new CurrencyConversion(fromCurrency, quantity, toCurrency,
                conversionService.convert(fromCurrency, quantity, toCurrency, validateDate(date)), date));
    }

    private String validateDate(final String dateString) {
        if (DEFAULT_DATE.equals(dateString)) {
            return dateString;
        }
        try {
            LocalDate date = LocalDate.parse(dateString);
            return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw RuntimeWebException.Builder.builder()
                    .message("Invalid request param : date = %s", dateString)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .params(Map.of("propertyName", "date", "value", dateString))
                    .throwable(e)
                    .build();
        }
    }
}
