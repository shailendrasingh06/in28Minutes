package com.in28minutes.microservices.currencyconversionmicroservice.controller;

import com.in28minutes.microservices.currencyconversionmicroservice.CurrencyExchangeProxy;
import com.in28minutes.microservices.currencyconversionmicroservice.entity.CurrencyConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    private CurrencyConversion calculateCurrencyConversion(@PathVariable String from,
                                                           @PathVariable String to,
                                                           @PathVariable BigDecimal quantity
    ) {

        HashMap<String, String> uriVariables = new HashMap<>(2);
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class, uriVariables);
        CurrencyConversion convertedCurrency = responseEntity.getBody();

        return new CurrencyConversion(convertedCurrency.getId(),
                from,
                to,
                convertedCurrency.getConversionMultiple(),
                quantity,
                quantity.multiply(convertedCurrency.getConversionMultiple()),
                convertedCurrency.getEnvironment());

    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    private CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from,
                                                           @PathVariable String to,
                                                           @PathVariable BigDecimal quantity
    ) {

        CurrencyConversion convertedCurrency = currencyExchangeProxy.retrieveExchangeRate(from, to);

        return new CurrencyConversion(convertedCurrency.getId(),
                from,
                to,
                convertedCurrency.getConversionMultiple(),
                quantity,
                quantity.multiply(convertedCurrency.getConversionMultiple()),
                convertedCurrency.getEnvironment());

    }
}
