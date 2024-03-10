package com.in28minutes.microservices.currencyexchangemicroservice.controller;

import com.in28minutes.microservices.currencyexchangemicroservice.entity.CurrencyExchange;
import com.in28minutes.microservices.currencyexchangemicroservice.repo.CurrencyExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("currency-exchange")
public class CurrencyExchangeController {

    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    @Autowired
    private Environment environment;

    @Autowired
    CurrencyExchangeRepository exchangeRepository;

    @GetMapping("/from/{from}/to/{to}")
    private CurrencyExchange retrieveExchangeRate(@PathVariable String from, @PathVariable String to) {

//        9fbc38ab99406c3e2457f38f5855a9cd-42989e7d1e8713c7

        logger.info("Calling exchange api with params: from- {}, to - {}", from, to);

        CurrencyExchange exchange =  exchangeRepository.findByFromAndTo(from, to);

        if (exchange ==null)
            throw new RuntimeException("Unable to find any data");

        exchange.setEnvironment(environment.getProperty("local.server.port"));

        return exchange;
    }
}
