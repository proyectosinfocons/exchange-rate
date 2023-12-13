package com.arauzo.service;

import com.arauzo.dto.RequestMontoDTO;
import com.arauzo.dto.ResponseMontoDTO;
import com.arauzo.model.ExchangeRate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IExchangeRateService{

    Mono<ExchangeRate> save(ExchangeRate exchangeRate);

    Mono<ResponseMontoDTO> changeCurrency(RequestMontoDTO requestMontoDTO);

    Flux<ExchangeRate> findAll();

    Mono<ExchangeRate> update(ExchangeRate exchangeRate);

    Mono<ExchangeRate> findById(String id);

}
