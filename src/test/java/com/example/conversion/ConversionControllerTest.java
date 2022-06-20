package com.example.conversion;

import com.example.conversion.controller.ConversionController;
import com.example.conversion.model.ConversionResponse;
import com.example.conversion.model.TemperatureUnit;
import com.example.conversion.service.ConversionService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpServerErrorException;

@WebMvcTest(ConversionController.class)
public class ConversionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConversionService conversionService;

    @Test
    public void testTemperatureConversion_Ok() throws Exception {
        TemperatureUnit from = TemperatureUnit.CELCIUS;
        TemperatureUnit to = TemperatureUnit.FAHRENHEIT;
        double value = 0.0;
        double expectedValue = 32.0;
        String expectedUnit = TemperatureUnit.FAHRENHEIT.name();
        ConversionResponse conversionResponse = new ConversionResponse(expectedValue, expectedUnit);

        Mockito.when(conversionService.processTemperatureConversion(from, to, value)).thenReturn(conversionResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/temperature")
                        .queryParam("from", from.name())
                        .queryParam("to", to.name())
                        .queryParam("value", Double.toString(value)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.aMapWithSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value", Matchers.is(expectedValue)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.unit", Matchers.is(expectedUnit)));
    }

    @Test
    public void testTemperatureConversion_BadRequest_IllegalArgument() throws Exception {
        TemperatureUnit from = TemperatureUnit.CELCIUS;
        TemperatureUnit to = TemperatureUnit.FAHRENHEIT;
        String value = "not a double";

        int expectedStatusCode = HttpStatus.BAD_REQUEST.value();

        mockMvc.perform(MockMvcRequestBuilders.get("/temperature")
                        .queryParam("from", from.name())
                        .queryParam("to", to.name())
                        .queryParam("value", value))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.aMapWithSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", Matchers.is(expectedStatusCode)));
    }

    @Test
    public void testTemperatureConversion_BadRequest_MissingArgument() throws Exception {
        TemperatureUnit from = TemperatureUnit.CELCIUS;
        TemperatureUnit to = TemperatureUnit.FAHRENHEIT;

        int expectedStatusCode = HttpStatus.BAD_REQUEST.value();
        String expectedMessage = "Required request parameter 'value' for method parameter type double is not present";

        mockMvc.perform(MockMvcRequestBuilders.get("/temperature")
                        .queryParam("from", from.name())
                        .queryParam("to", to.name()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.aMapWithSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", Matchers.is(expectedStatusCode)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(expectedMessage)));
    }

    @Test
    public void testTemperatureConversion_InternalServerError() throws Exception {
        TemperatureUnit from = TemperatureUnit.CELCIUS;
        TemperatureUnit to = TemperatureUnit.FAHRENHEIT;
        double value = 0.0;

        Mockito.when(conversionService.processTemperatureConversion(from, to, value)).thenThrow(HttpServerErrorException.InternalServerError.class);

        int expectedStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

        mockMvc.perform(MockMvcRequestBuilders.get("/temperature")
                        .queryParam("from", from.name())
                        .queryParam("to", to.name())
                        .queryParam("value", Double.toString(value)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.aMapWithSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", Matchers.is(expectedStatusCode)));
    }
}