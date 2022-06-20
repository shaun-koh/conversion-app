package com.example.conversion;

import com.example.conversion.model.ConversionResponse;
import com.example.conversion.model.TemperatureUnit;
import com.example.conversion.service.ConversionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConversionServiceTest {
    @InjectMocks
    ConversionService conversionService;

    @Test
    public void testProcessTemperatureConversion_CelciusToFahrenheit() {
        TemperatureUnit from = TemperatureUnit.CELCIUS;
        TemperatureUnit to = TemperatureUnit.FAHRENHEIT;
        double value = 0.0;

        ConversionResponse conversionResponse = conversionService.processTemperatureConversion(from, to, value);

        double expectedValue = 32.0;
        String expectedUnit = TemperatureUnit.FAHRENHEIT.name();

        Assertions.assertThat(conversionResponse.getValue()).isEqualTo(expectedValue);
        Assertions.assertThat(conversionResponse.getUnit()).isEqualTo(expectedUnit);
    }

    @Test
    public void testProcessTemperatureConversion_FahrenheitToCelcius() {
        TemperatureUnit from = TemperatureUnit.FAHRENHEIT;
        TemperatureUnit to = TemperatureUnit.CELCIUS;
        double value = 32.0;

        ConversionResponse conversionResponse = conversionService.processTemperatureConversion(from, to, value);

        double expectedValue = 0.0;
        String expectedUnit = TemperatureUnit.CELCIUS.name();

        Assertions.assertThat(conversionResponse.getValue()).isEqualTo(expectedValue);
        Assertions.assertThat(conversionResponse.getUnit()).isEqualTo(expectedUnit);
    }
}
