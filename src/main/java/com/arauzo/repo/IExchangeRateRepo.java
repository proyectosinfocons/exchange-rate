package com.arauzo.repo;

import com.arauzo.model.ExchangeRate;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IExchangeRateRepo extends ReactiveMongoRepository<ExchangeRate, String> {

    @Query("{ 'sourceCurrency' : ?0, 'targetCurrency' : ?1 }")
    Mono<ExchangeRate> findBySourceCurrencyAndTargetCurrency(String sourceCurrency, String targetCurrency);

}
