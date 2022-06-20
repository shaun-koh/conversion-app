package com.example.conversion;

import com.example.conversion.controller.ConversionController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConversionApplicationTest {

    @Autowired
    private ConversionController conversionController;

    @Test
    public void contextLoads() {
        Assertions.assertThat(conversionController).isNotNull();
    }
}
