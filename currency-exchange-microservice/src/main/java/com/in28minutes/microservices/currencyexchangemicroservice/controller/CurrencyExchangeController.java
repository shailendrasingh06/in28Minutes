package com.in28minutes.microservices.currencyexchangemicroservice.controller;

import com.in28minutes.microservices.currencyexchangemicroservice.entity.CurrencyExchange;
import com.in28minutes.microservices.currencyexchangemicroservice.repo.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("currency-exchange")
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    @Autowired
    CurrencyExchangeRepository exchangeRepository;

    @GetMapping("/from/{from}/to/{to}")
    private CurrencyExchange retrieveExchangeRate(@PathVariable String from, @PathVariable String to) {

        CurrencyExchange exchange =  exchangeRepository.findByFromAndTo(from, to);

        if (exchange ==null)
            throw new RuntimeException("Unable to find any data");

        exchange.setEnvironment(environment.getProperty("local.server.port"));

        return exchange;
    }
}
