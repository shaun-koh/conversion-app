package com.example.conversion.service;

import com.example.conversion.model.ConversionResponse;
import com.example.conversion.model.TemperatureUnit;
import org.springframework.stereotype.Service;

@Service
public class ConversionService {

    public ConversionResponse processTemperatureConversion(TemperatureUnit from, TemperatureUnit to, double value) {
        ConversionResponse conversionResponse = null;
        if(from == to) {
            conversionResponse = new ConversionResponse(value, from.toString());
        }
        if (from == TemperatureUnit.CELCIUS && to == TemperatureUnit.FAHRENHEIT) {
            conversionResponse = new ConversionResponse(convertCelsiusToFahrenheit(value), to.toString());
        }
        if (from == TemperatureUnit.FAHRENHEIT && to == TemperatureUnit.CELCIUS) {
            conversionResponse = new ConversionResponse(convertFahrenheitToCelsius(value), to.toString());
        }
        if (conversionResponse == null) {
            throw new NullPointerException("ConversionResponse is null");
        } else {
            return conversionResponse;
        }
    }

    private double convertCelsiusToFahrenheit(double value) {
        return (9.0/5.0) * value + 32;
    }

    private double convertFahrenheitToCelsius(double value) {
        return (5.0/9.0) * (value - 32);
    }
}
