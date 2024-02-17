package com.knspatavardhan.currenciesconverterwrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knspatavardhan.currenciesconverterwrapper.error.RuntimeWebException;
import com.knspatavardhan.currenciesconverterwrapper.provider.FawazAhmedConversionRateProvider;
import com.knspatavardhan.currenciesconverterwrapper.representation.CurrencyConversion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CurrenciesConverterWrapperApplicationTests {

    private static final double CONVERSION_RATE = 0.671;

    private static final UriBuilder URI_BUILDER =
            UriComponentsBuilder.fromPath("/currency-conversion/convert/{fromCurrency}/{toCurrency}/{quantity}");

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @MockBean
    FawazAhmedConversionRateProvider mockConversionProvider;

    @Autowired
    private MockMvc mvc;

    @Test
    void convertReturnsOkResponse() throws Exception {
        double inputQuantity = 100;
        when(mockConversionProvider.getConversionRate("usd", "inr", "latest"))
                .thenReturn(CONVERSION_RATE);
        mvc.perform(post(getPath("USD", "INR", String.valueOf(inputQuantity))))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    final CurrencyConversion response = readEntity(result, CurrencyConversion.class);
                    final String expectedOutput = String.format(response.getToCurrency().getDecimalPrecision(),
                            CONVERSION_RATE * inputQuantity);
                    Assertions.assertEquals(expectedOutput, response.getOutputQuantity());
                });
    }

    @Test
    void convertReturnsErrorOnInvalidFromCurrency() throws Exception {
        mvc.perform(post(getPath("XYZ", "INR", "200")))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    final Map<String, Object> errorResponse = readEntity(result, Map.class);
                    Assertions.assertEquals(400, errorResponse.get("status"));
                    Assertions.assertTrue(errorResponse.get("parameters") instanceof Map<?,?>);
                    Map<String, Object> paramsMap = (Map<String, Object>) errorResponse.get("parameters");
                    Assertions.assertEquals("fromCurrency", paramsMap.get("propertyName"));
                    Assertions.assertEquals("XYZ", paramsMap.get("value"));
                });
    }

    @Test
    void convertReturnsErrorOnInvalidToCurrency() throws Exception {
        mvc.perform(post(getPath("USD", "ABC", "200")))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    final Map<String, Object> errorResponse = readEntity(result, Map.class);
                    Assertions.assertEquals(400, errorResponse.get("status"));
                    Assertions.assertTrue(errorResponse.get("parameters") instanceof Map<?,?>);
                    Map<String, Object> paramsMap = (Map<String, Object>) errorResponse.get("parameters");
                    Assertions.assertEquals("toCurrency", paramsMap.get("propertyName"));
                    Assertions.assertEquals("ABC", paramsMap.get("value"));
                });
    }

    @Test
    void convertReturnsErrorOnInvalidQuantity() throws Exception {
        mvc.perform(post(getPath("USD", "INR", "invalid")))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    final Map<String, Object> errorResponse = readEntity(result, Map.class);
                    Assertions.assertEquals(400, errorResponse.get("status"));
                    Assertions.assertTrue(errorResponse.get("parameters") instanceof Map<?,?>);
                    Map<String, Object> paramsMap = (Map<String, Object>) errorResponse.get("parameters");
                    Assertions.assertEquals("quantity", paramsMap.get("propertyName"));
                    Assertions.assertEquals("invalid", paramsMap.get("value"));
                });
    }

    @Test
    void convertReturnsNotFoundErrorWhenConversionRateIsNotFound() throws Exception {
        final String errorMessage = "Failed to fetch conversion rate";
        when(mockConversionProvider.getConversionRate("usd", "inr", "latest"))
                .thenThrow(new RuntimeWebException.Builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(errorMessage)
                        .params(Collections.emptyMap())
                        .build());
        mvc.perform(post(getPath("USD", "INR", "200")))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    final Map<String, Object> errorResponse = readEntity(result, Map.class);
                    Assertions.assertEquals(400, errorResponse.get("status"));
                    Assertions.assertEquals(errorMessage, errorResponse.get("message"));
                });
    }

    private String getPath(final String from, final String to, final String quantity) {
        return URI_BUILDER.build(from, to, quantity).getPath();
    }

    private <T> T readEntity(final MvcResult result, final Class<T> type)
            throws UnsupportedEncodingException, JsonProcessingException {
        final String responseString = result.getResponse().getContentAsString();
        Assertions.assertNotNull(responseString);
        return readEntity(responseString, type);
    }

    private <T> T readEntity(final String content, final Class<T> type) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(content, type);
    }

}
