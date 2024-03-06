package com.in28minutes.microservices.currencyexchangemicroservice.repo;

import com.in28minutes.microservices.currencyexchangemicroservice.entity.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {

    CurrencyExchange findByFromAndTo(String from, String to);
}
