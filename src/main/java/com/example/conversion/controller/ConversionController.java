package com.example.conversion.controller;

import com.example.conversion.model.ConversionResponse;
import com.example.conversion.model.TemperatureUnit;
import com.example.conversion.service.ConversionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conversion")
@Tag(name = "Conversion", description = "Endpoints for converting between units of various conversion types")
@Validated
@ResponseBody
public class ConversionController {

    private final ConversionService conversionService;

    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping(value = "/temperature", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConversionResponse> temperatureConversion(@RequestParam TemperatureUnit from, @RequestParam TemperatureUnit to, @RequestParam double value) {
        ConversionResponse conversionResponse = conversionService.processTemperatureConversion(from, to, value);
        return new ResponseEntity<>(conversionResponse, HttpStatus.OK);
    }
}


